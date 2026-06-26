package it.unicam.cs.mpgc.rpg123022.Database;

import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaAttacco;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaDifesa;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaRisorsa;
import it.unicam.cs.mpgc.rpg123022.Aumenti.Effetto;
import it.unicam.cs.mpgc.rpg123022.Builder.OggettoBuilder;
import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.PesoOggetto;
import it.unicam.cs.mpgc.rpg123022.Enum.Rarita;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoRisorsa;
import it.unicam.cs.mpgc.rpg123022.Oggetti.Oggetto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OggettoDao {

    public long save(Oggetto oggetto) {
        final String insertOggetto = """
                INSERT INTO oggetti(nome, tipo, peso, consumo, rarita)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertOggetto, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            statement.setString(1, oggetto.getNome());
            statement.setString(2, oggetto.getTipo().name());
            statement.setString(3, oggetto.getPesoOggetto().name());
            statement.setInt(4, oggetto.getConsumo());
            statement.setString(5, oggetto.getRarita().name());
            statement.executeUpdate();

            long oggettoId;
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DatabaseException("Impossibile ottenere l'id dell'oggetto salvato", null);
                }
                oggettoId = generatedKeys.getLong(1);
            }

            saveClassiCompatibili(connection, oggettoId, oggetto.getClasseCompatibili());
            saveEffetti(connection, oggettoId, oggetto.getEffetto());
            connection.commit();
            return oggettoId;
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel salvataggio dell'oggetto " + oggetto.getNome(), e);
        }
    }

    public List<Oggetto> findAll() {
        List<Oggetto> oggetti = new ArrayList<>();
        final String sql = "SELECT id FROM oggetti ORDER BY id";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                oggetti.add(findById(connection, resultSet.getLong("id")));
            }
            return oggetti;
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel caricamento degli oggetti", e);
        }
    }

    public Oggetto findById(long id) {
        try (Connection connection = DatabaseManager.getConnection()) {
            return findById(connection, id);
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel caricamento dell'oggetto con id " + id, e);
        }
    }

    public List<Oggetto> findByPersonaggioId(int personaggioId) {
        List<Oggetto> oggetti = new ArrayList<>();
        final String sql = """
                SELECT oggetto_id
                FROM inventario_personaggi
                WHERE personaggio_id = ?
                ORDER BY oggetto_id
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, personaggioId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    oggetti.add(findById(connection, resultSet.getLong("oggetto_id")));
                }
            }
            return oggetti;
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel caricamento dell'inventario del personaggio " + personaggioId, e);
        }
    }

    public void deleteById(long id) {
        final String sql = "DELETE FROM oggetti WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Errore nell'eliminazione dell'oggetto con id " + id, e);
        }
    }

    private Oggetto findById(Connection connection, long id) throws SQLException {
        final String sql = """
                SELECT nome, tipo, peso, consumo, rarita
                FROM oggetti
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                return new OggettoBuilder()
                        .setNome(resultSet.getString("nome"))
                        .setTipo(TipoOggetto.valueOf(resultSet.getString("tipo")))
                        .setPeso(PesoOggetto.valueOf(resultSet.getString("peso")))
                        .addClassiCompatibili(loadClassiCompatibili(connection, id).toArray(new Classe[0]))
                        .addEffetto(loadEffetti(connection, id).toArray(new Effetto[0]))
                        .setConsumo(resultSet.getInt("consumo"))
                        .setRarita(Rarita.valueOf(resultSet.getString("rarita")))
                        .build();
            }
        }
    }

    private void saveClassiCompatibili(Connection connection, long oggettoId, Set<Classe> classiCompatibili) throws SQLException {
        final String sql = """
                INSERT INTO oggetto_classi_compatibili(oggetto_id, classe)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Classe classe : classiCompatibili) {
                statement.setLong(1, oggettoId);
                statement.setString(2, classe.name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private Set<Classe> loadClassiCompatibili(Connection connection, long oggettoId) throws SQLException {
        Set<Classe> classiCompatibili = new HashSet<>();
        final String sql = """
                SELECT classe
                FROM oggetto_classi_compatibili
                WHERE oggetto_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, oggettoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    classiCompatibili.add(Classe.valueOf(resultSet.getString("classe")));
                }
            }
        }
        return classiCompatibili;
    }

    private void saveEffetti(Connection connection, long oggettoId, List<Effetto> effetti) throws SQLException {
        final String sql = """
                INSERT INTO oggetto_effetti(oggetto_id, tipo_effetto, valore, tipo_risorsa)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Effetto effetto : effetti) {
                statement.setLong(1, oggettoId);

                if (effetto instanceof AumentaAttacco aumentaAttacco) {
                    statement.setString(2, "AUMENTA_ATTACCO");
                    statement.setInt(3, aumentaAttacco.getValore());
                    statement.setString(4, null);
                } else if (effetto instanceof AumentaDifesa aumentaDifesa) {
                    statement.setString(2, "AUMENTA_DIFESA");
                    statement.setInt(3, aumentaDifesa.getValore());
                    statement.setString(4, null);
                } else if (effetto instanceof AumentaRisorsa aumentaRisorsa) {
                    statement.setString(2, "AUMENTA_RISORSA");
                    statement.setInt(3, aumentaRisorsa.getValore());
                    statement.setString(4, aumentaRisorsa.getTipo().name());
                } else {
                    continue;
                }

                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private List<Effetto> loadEffetti(Connection connection, long oggettoId) throws SQLException {
        List<Effetto> effetti = new ArrayList<>();
        final String sql = """
                SELECT tipo_effetto, valore, tipo_risorsa
                FROM oggetto_effetti
                WHERE oggetto_id = ?
                ORDER BY id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, oggettoId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tipoEffetto = resultSet.getString("tipo_effetto");
                    int valore = resultSet.getInt("valore");

                    switch (tipoEffetto) {
                        case "AUMENTA_ATTACCO" -> effetti.add(new AumentaAttacco(valore));
                        case "AUMENTA_DIFESA" -> effetti.add(new AumentaDifesa(valore));
                        case "AUMENTA_RISORSA" -> effetti.add(new AumentaRisorsa(
                                valore,
                                TipoRisorsa.valueOf(resultSet.getString("tipo_risorsa"))
                        ));
                        default -> {
                        }
                    }
                }
            }
        }
        return effetti;
    }
}
