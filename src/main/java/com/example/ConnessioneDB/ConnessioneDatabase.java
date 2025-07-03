package com.example.ConnessioneDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    private Connection connection;


    private final String nomeUtente = "postgres";
    private final String passwordDB = "Diomede05";
    private final String url = "jdbc:postgresql://localhost:5432/Hackathon"; // Esempio per PostgreSQL
    private final String driver = "org.postgresql.Driver";


    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, nomeUtente, passwordDB);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver JDBC (" + driver + ") non trovato. Assicurati che il JAR sia nel classpath.", ex);
        } catch (SQLException ex) {
            throw new SQLException("Connessione al database fallita. URL: " + url + ", Utente: " + nomeUtente, ex);
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else {

            try {
                if (instance.connection == null || instance.connection.isClosed() || !instance.connection.isValid(1)) {

                    instance.connection = DriverManager.getConnection(instance.url, instance.nomeUtente,
                            instance.passwordDB);

                }
            } catch (SQLException e) {

                throw new SQLException("Impossibile ristabilire la connessione al database.", e);
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void close() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                // System.out.println("Connessione al database chiusa.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Considera un logger
        }
    }
}