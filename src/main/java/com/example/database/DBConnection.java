package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Hackathon";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1";

    static {
        try {
            // Caricamento esplicito del driver (facoltativo per JDBC 4.0+ ma utile in certi
            // casi)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC PostgreSQL non trovato!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
