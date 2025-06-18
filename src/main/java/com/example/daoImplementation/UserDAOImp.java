package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.UserDAO;

import com.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public void addUser(User user) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);

            // ELENCO DI VALORI DA AGGIUNGERE

            rs = ps.executeQuery();
            if (rs.next()) {

                // AGGIUNTA QUERY

            }

        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }
        String sql = "";
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            ps = con.prepareStatement(sql);

            // ELENCO DI VALORI DA AGGIORNARE

            righeModificate = ps.executeUpdate();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // VERO SE ALMENO UNA RIGA è STATA MODIFICATA
        return righeModificate > 0;
    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        // QUERY
        String sql = "";
        PreparedStatement ps = null;
        int righeModificate = 0;
        try {
            ps = con.prepareStatement(sql);

            // ELIMINAZIONE

            righeModificate = ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return righeModificate > 0;

    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "SELECT * FROM request";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = new ArrayList<User>();

        try {
            ps = con.prepareStatement(sql);
            while (rs.next()) {
                // creazione di un oggetto Request per ogni tupla (DA CAPIRE SE USARE UN
                // COSTRUTTORE VUOTO O MENO)
                User user = new User();

                // serie di set per popolare il costruttore

                users.add(user);
            }
            rs = ps.executeQuery();
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        Connection con = null; // Initialize connection to null
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection(); // Get the connection inside the try block

            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            // CORRECTED: Use 'email' column for WHERE clause, not 'user_id'
            String sql = "SELECT user_id, first_name, last_name, email, username, password FROM users WHERE email = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, email); // CORRECTED: Set String parameter, not int
            rs = ps.executeQuery();

            if (rs.next()) {
                // Creazione e popolamento dell'oggetto User
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } finally {
            // Ensure resources are closed in reverse order of creation
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (con != null) { // Close the connection if it was opened
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
        return user;
    }
}
