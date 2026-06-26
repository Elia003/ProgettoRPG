package it.unicam.cs.mpgc.rpg123022.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:progetto-rpg.db";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection()) {
            SchemaInitializer.initialize(connection);
        } catch (SQLException e) {
            throw new DatabaseException("Impossibile inizializzare il database", e);
        }
    }
}
