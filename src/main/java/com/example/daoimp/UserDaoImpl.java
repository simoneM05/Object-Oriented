package com.example.daoimp;

import com.example.dao.UserDAO;
import com.example.database.DBConnection;
import com.example.model.Role;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDAO {

    @Override
    public Optional<User> findByEmail(String email) {

        String sql = """
                SELECT
                    u.*,
                    CASE
                        WHEN EXISTS (SELECT 1 FROM organizers o WHERE o.user_email = u.email) THEN 'organizer'
                        WHEN EXISTS (SELECT 1 FROM judges j WHERE j.user_email = u.email) THEN 'judge'
                        WHEN EXISTS (SELECT 1 FROM participants p WHERE p.user_email = u.email) THEN 'participant'
                        ELSE 'user'
                    END AS role
                FROM users u
                WHERE u.email = ?;
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = mapRowToUser(rs);
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                System.out.println(user.getRole());
                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT u.* FROM users u WHERE u.username = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Per il ruolo devi fare la stessa logica, quindi fai una query o modifica qui
                // Facciamo una query ausiliaria per il ruolo:
                String email = rs.getString("email");
                User user = mapRowToUser(rs);

                // Ora recuperiamo il ruolo con query singola, o reimplementiamo findByEmail
                Optional<User> userWithRole = findByEmail(email);
                return userWithRole;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM users u";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Come sopra, per il ruolo conviene fare una query extra oppure implementare un
                // join
                // Qui prendo solo i dati base e poi cerco il ruolo
                String email = rs.getString("email");
                Optional<User> userWithRole = findByEmail(email);
                userWithRole.ifPresent(users::add);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean save(User user) {
        String sql = "INSERT INTO users (email, username, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirst_name());
            ps.setString(5, user.getLast_name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ? WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirst_name());
            ps.setString(4, user.getLast_name());
            ps.setString(5, user.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        String email = rs.getString("email");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");

        Role role = Role.USER; // default
        try {
            String roleStr = rs.getString("role");
            if (roleStr != null) {
                role = Role.valueOf(roleStr.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            // Se il ruolo non è valido, usa USER di default
        }

        return new User(email, username, password, firstName, lastName, role);
    }
}
