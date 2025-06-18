package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.UserDAO;
import com.example.model.User; // Assicurati che questa classe User corrisponda ai campi del DB

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    /**
     * Aggiunge un nuovo utente al database.
     * La password dovrebbe essere hashata prima di essere passata a questo metodo
     * in un'applicazione reale.
     *
     * @param user L'oggetto User da aggiungere.
     * @throws SQLException Se si verifica un errore SQL.
     */
    @Override
    public void addUser(User user) throws SQLException {
        Connection con = null; // Inizializza la connessione a null
        PreparedStatement ps = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // SQL per inserire un nuovo utente.
            // Le colonne sono: email (PK), username (UNIQUE), password, first_name,
            // last_name
            String sql = "INSERT INTO users (email, username, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            // Imposta i valori per i placeholder della query
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword()); // In un'applicazione reale, la password dovrebbe essere hashata qui o
                                                 // prima!
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());

            // Esegui l'aggiornamento (INSERT)
            ps.executeUpdate();
            System.out.println("Utente " + user.getUsername() + " aggiunto con successo.");

        } finally {
            // Assicurati di chiudere le risorse (PreparedStatement, Connection) anche in
            // caso di eccezioni
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err
                            .println("Errore durante la chiusura del PreparedStatement in addUser: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in addUser: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Aggiorna i dati di un utente esistente nel database.
     * L'utente viene identificato tramite la sua email (chiave primaria).
     *
     * @param user L'oggetto User con i dati aggiornati.
     * @return true se almeno una riga è stata modificata, false altrimenti.
     * @throws SQLException Se si verifica un errore SQL.
     */
    @Override
    public boolean updateUser(User user) throws SQLException {
        Connection con = null; // Inizializza la connessione a null
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // SQL per aggiornare un utente basandosi sulla sua email (chiave primaria)
            String sql = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ? WHERE email = ?";
            ps = con.prepareStatement(sql);

            // Imposta i nuovi valori per i campi dell'utente e la condizione WHERE
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // Gestire l'aggiornamento della password con attenzione (es. hashing)
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail()); // La condizione WHERE si basa sull'email dell'utente

            righeModificate = ps.executeUpdate(); // Esegui l'aggiornamento (UPDATE)

        } finally {
            // Assicurati di chiudere le risorse
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Errore durante la chiusura del PreparedStatement in updateUser: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in updateUser: " + e.getMessage());
                }
            }
        }

        // Restituisce true se almeno una riga è stata modificata
        return righeModificate > 0;
    }

    /**
     * Elimina un utente dal database.
     * L'utente viene identificato tramite la sua email (chiave primaria).
     *
     * @param user L'oggetto User da eliminare (basta che contenga l'email).
     * @return true se almeno una riga è stata eliminata, false altrimenti.
     * @throws SQLException Se si verifica un errore SQL.
     */
    @Override
    public boolean deleteUser(User user) throws SQLException {
        Connection con = null; // Inizializza la connessione a null
        PreparedStatement ps = null;
        int righeModificate = 0;
        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // SQL per eliminare un utente basandosi sulla sua email (chiave primaria)
            String sql = "DELETE FROM users WHERE email = ?";
            ps = con.prepareStatement(sql);

            // Imposta il parametro per la clausola WHERE
            ps.setString(1, user.getEmail());

            righeModificate = ps.executeUpdate(); // Esegui l'eliminazione (DELETE)
        } finally {
            // Assicurati di chiudere le risorse
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Errore durante la chiusura del PreparedStatement in deleteUser: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in deleteUser: " + e.getMessage());
                }
            }
        }
        return righeModificate > 0;
    }

    /**
     * Recupera tutti gli utenti dal database.
     *
     * @return Una lista di oggetti User, vuota se non ci sono utenti.
     * @throws SQLException Se si verifica un errore SQL.
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        Connection con = null; // Inizializza la connessione a null
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>(); // Inizializza una nuova lista di utenti

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // SQL per selezionare tutti gli utenti dalla tabella 'users'
            String sql = "SELECT email, username, password, first_name, last_name FROM users";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); // Esegui la query per ottenere il ResultSet

            // Itera sul ResultSet per costruire gli oggetti User
            while (rs.next()) {
                // Creazione di un oggetto User per ogni riga (tupla)
                // Assumendo che il tuo modello User abbia un costruttore che accetta questi
                // parametri
                User user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));

                // NOTA IMPORTANTE: Il tuo schema della tabella 'users' NON contiene una colonna
                // 'user_id'.
                // Se il tuo oggetto User ha un campo 'userId', non sarà popolato da questa
                // query
                // a meno che tu non aggiunga una colonna 'user_id' al DDL della tabella
                // 'users'.
                // user.setUserId(rs.getInt("user_id")); // Questa riga è stata rimossa per
                // evitare errori

                users.add(user); // Aggiungi l'utente alla lista
            }
        } finally {
            // Assicurati di chiudere le risorse (ResultSet, PreparedStatement, Connection)
            // in ordine inverso di creazione
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getAllUsers: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Errore durante la chiusura del PreparedStatement in getAllUsers: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in getAllUsers: " + e.getMessage());
                }
            }
        }
        return users; // Restituisci la lista di utenti
    }

    /**
     * Recupera un utente specifico dal database tramite la sua email.
     *
     * @param email L'email dell'utente da cercare.
     * @return L'oggetto User se trovato, null altrimenti.
     * @throws SQLException Se si verifica un errore SQL.
     */
    @Override
    public User getUserByEmail(String email) throws SQLException {
        Connection con = null; // Inizializza la connessione a null
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null; // Inizializza l'oggetto User a null

        try {
            con = ConnessioneDatabase.getInstance().getConnection(); // Ottieni la connessione
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // Query per selezionare un utente tramite email.
            // Anche qui, escludiamo 'user_id' perché non è nel tuo schema 'users'.
            String sql = "SELECT email, username, password, first_name, last_name FROM users WHERE email = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, email); // Imposta il parametro email nella query
            rs = ps.executeQuery(); // Esegui la query

            if (rs.next()) {
                // Se viene trovata una riga, crea e popola l'oggetto User
                user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));
                // NOTA IMPORTANTE: Come sopra, 'user_id' non è nello schema della tabella
                // 'users'.
                // user.setUserId(rs.getInt("user_id")); // Questa riga è stata rimossa per
                // evitare errori
            }
        } finally {
            // Assicurati di chiudere le risorse in ordine inverso di creazione
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getUserByEmail: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Errore durante la chiusura del PreparedStatement in getUserByEmail: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Errore durante la chiusura della Connection in getUserByEmail: " + e.getMessage());
                }
            }
        }
        return user; // Restituisce l'utente trovato o null
    }
}
