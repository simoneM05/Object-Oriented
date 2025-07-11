package com.example.daoimp;

import com.example.dao.OrganizerDAO;
import com.example.database.DBConnection;
import com.example.model.Organizer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizerDaoImpl implements OrganizerDAO {

    @Override
    public Optional<Organizer> findByEmail(String email) {
        String sql = "SELECT * FROM organizer WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToOrganizer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Organizer> findAll() {
        List<Organizer> list = new ArrayList<>();
        String sql = "SELECT * FROM organizer";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToOrganizer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Organizer> findByHackathonId(String hackathonId) {
        List<Organizer> list = new ArrayList<>();
        String sql = "SELECT * FROM organizer WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hackathonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrganizer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Organizer o) {
        String sql = "INSERT INTO organizer (email, username, password, first_name, last_name, hackathon_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getEmail());
            ps.setString(2, o.getUsername());
            ps.setString(3, o.getPassword());
            ps.setString(4, o.getFirst_name());
            ps.setString(5, o.getLast_name());
            ps.setString(6, o.getHackathonID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Organizer o) {
        String sql = "UPDATE organizer SET username=?, password=?, first_name=?, last_name=?, hackathon_id=? WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getUsername());
            ps.setString(2, o.getPassword());
            ps.setString(3, o.getFirst_name());
            ps.setString(4, o.getLast_name());
            ps.setString(5, o.getHackathonID());
            ps.setString(6, o.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Organizer mapRowToOrganizer(ResultSet rs) throws SQLException {
        return new Organizer(
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("hackathon_id"));
    }

    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM organizer WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
