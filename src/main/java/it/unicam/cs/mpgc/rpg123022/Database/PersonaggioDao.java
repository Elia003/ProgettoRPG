package it.unicam.cs.mpgc.rpg123022.Database;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Barbaro;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Druido;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Guerriero;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Ladro;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Mago;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg123022.Risorse.Mana;
import it.unicam.cs.mpgc.rpg123022.Risorse.Risorsa;
import it.unicam.cs.mpgc.rpg123022.Risorse.Stamina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaggioDao {
    private final OggettoDao oggettoDao = new OggettoDao();

    public void save(Personaggio personaggio) {
        final String sql = """
                INSERT OR REPLACE INTO personaggi
                (id, nome, classe, genere, livello, attacco, difesa, hp, tipo_risorsa, valore_risorsa)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            statement.setInt(1, personaggio.getId());
            statement.setString(2, personaggio.getNome());
            statement.setString(3, personaggio.getClasse().name());
            statement.setString(4, personaggio.getGenere().name());
            statement.setInt(5, personaggio.getLivello());
            statement.setInt(6, personaggio.getAttacco());
            statement.setInt(7, personaggio.getDifesa());
            statement.setInt(8, personaggio.getHp());
            statement.setString(9, personaggio.getRisorsa().getTipo().name());
            statement.setInt(10, personaggio.getRisorsa().getValore());
            statement.executeUpdate();

            saveInventario(connection, personaggio);
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel salvataggio del personaggio " + personaggio.getNome(), e);
        }
    }

    public List<Personaggio> findAll() {
        List<Personaggio> personaggi = new ArrayList<>();
        final String sql = "SELECT id FROM personaggi ORDER BY id";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                personaggi.add(findById(connection, resultSet.getInt("id")));
            }
            return personaggi;
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel caricamento dei personaggi", e);
        }
    }

    public Personaggio findById(int id) {
        try (Connection connection = DatabaseManager.getConnection()) {
            return findById(connection, id);
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel caricamento del personaggio con id " + id, e);
        }
    }

    public void deleteById(int id) {
        final String sql = "DELETE FROM personaggi WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Errore nell'eliminazione del personaggio con id " + id, e);
        }
    }

    private Personaggio findById(Connection connection, int id) throws SQLException {
        final String sql = """
                SELECT nome, classe, genere, livello, attacco, difesa, hp, tipo_risorsa, valore_risorsa
                FROM personaggi
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Personaggio personaggio = buildPersonaggio(
                        id,
                        resultSet.getString("nome"),
                        Classe.valueOf(resultSet.getString("classe")),
                        Genere.valueOf(resultSet.getString("genere"))
                );

                personaggio.setLivello(resultSet.getInt("livello"));
                personaggio.setAttacco(resultSet.getInt("attacco"));
                personaggio.setDifesa(resultSet.getInt("difesa"));
                personaggio.setHp(resultSet.getInt("hp"));
                personaggio.setRisorsa(buildRisorsa(
                        resultSet.getString("tipo_risorsa"),
                        resultSet.getInt("valore_risorsa")
                ));

                List<Oggetto> inventario = oggettoDao.findByPersonaggioId(id);
                personaggio.getInventario().getOggetti().addAll(inventario);
                return personaggio;
            }
        }
    }

    private void saveInventario(Connection connection, Personaggio personaggio) throws SQLException {
        final String deleteSql = "DELETE FROM inventario_personaggi WHERE personaggio_id = ?";
        final String insertSql = """
                INSERT INTO inventario_personaggi(personaggio_id, oggetto_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, personaggio.getId());
            deleteStatement.executeUpdate();
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (Oggetto oggetto : personaggio.getInventario().getOggetti()) {
                long oggettoId = oggettoDao.save(oggetto);
                insertStatement.setInt(1, personaggio.getId());
                insertStatement.setLong(2, oggettoId);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private Personaggio buildPersonaggio(int id, String nome, Classe classe, Genere genere) {
        return switch (classe) {
            case MAGO -> new Mago(id, nome, genere);
            case BARBARO -> new Barbaro(id, nome, genere);
            case LADRO -> new Ladro(id, nome, genere);
            case DRUIDO -> new Druido(id, nome, genere);
            case GUERRIERO -> new Guerriero(id, nome, genere);
        };
    }

    private Risorsa buildRisorsa(String tipoRisorsa, int valore) {
        Risorsa risorsa = switch (tipoRisorsa) {
            case "MANA" -> new Mana();
            case "STAMINA" -> new Stamina();
            default -> throw new IllegalArgumentException("Tipo risorsa non supportato: " + tipoRisorsa);
        };

        risorsa.setValore(valore);
        return risorsa;
    }
}
