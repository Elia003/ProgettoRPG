package it.unicam.cs.mpgc.rpg123022.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class SchemaInitializer {
    private SchemaInitializer() {
    }

    public static void initialize(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS personaggi (
                        id INTEGER PRIMARY KEY,
                        nome TEXT NOT NULL,
                        classe TEXT NOT NULL,
                        genere TEXT NOT NULL,
                        livello INTEGER NOT NULL,
                        attacco INTEGER NOT NULL,
                        difesa INTEGER NOT NULL,
                        hp INTEGER NOT NULL,
                        tipo_risorsa TEXT NOT NULL,
                        valore_risorsa INTEGER NOT NULL
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS oggetti (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL,
                        tipo TEXT NOT NULL,
                        peso TEXT NOT NULL,
                        consumo INTEGER NOT NULL,
                        rarita TEXT NOT NULL
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS oggetto_classi_compatibili (
                        oggetto_id INTEGER NOT NULL,
                        classe TEXT NOT NULL,
                        PRIMARY KEY (oggetto_id, classe),
                        FOREIGN KEY (oggetto_id) REFERENCES oggetti(id) ON DELETE CASCADE
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS oggetto_effetti (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        oggetto_id INTEGER NOT NULL,
                        tipo_effetto TEXT NOT NULL,
                        valore INTEGER NOT NULL,
                        tipo_risorsa TEXT,
                        FOREIGN KEY (oggetto_id) REFERENCES oggetti(id) ON DELETE CASCADE
                    )
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS inventario_personaggi (
                        personaggio_id INTEGER NOT NULL,
                        oggetto_id INTEGER NOT NULL,
                        PRIMARY KEY (personaggio_id, oggetto_id),
                        FOREIGN KEY (personaggio_id) REFERENCES personaggi(id) ON DELETE CASCADE,
                        FOREIGN KEY (oggetto_id) REFERENCES oggetti(id) ON DELETE CASCADE
                    )
                    """);
        }
    }
}
