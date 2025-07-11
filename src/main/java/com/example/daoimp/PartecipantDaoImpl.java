package com.example.daoimp;

import com.example.dao.PartecipantDAO;
import com.example.database.DBConnection;
import com.example.model.Partecipant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartecipantDaoImpl implements PartecipantDAO {

    @Override
    public Optional<Partecipant> findByEmail(String email) {
        String sql = "SELECT * FROM partecipant WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToPartecipant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Partecipant> findAll() {
        List<Partecipant> list = new ArrayList<>();
        String sql = "SELECT * FROM partecipant";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToPartecipant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Partecipant> findByHackathonId(int hackathonId) {
        List<Partecipant> list = new ArrayList<>();
        String sql = "SELECT * FROM partecipant WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPartecipant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Partecipant> findByTeamId(int teamId) {
        List<Partecipant> list = new ArrayList<>();
        String sql = "SELECT * FROM partecipant WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPartecipant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Partecipant p) {
        String sql = "INSERT INTO participants (user_email, hackathon_id, team_id) VALUES (?, ?, NULL)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getEmail());
            ps.setInt(2, p.getHackathonID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean assignToTeam(String email, int teamId) {
        String sql = "UPDATE participants SET team_id = ? WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Partecipant p) {
        String sql = "UPDATE partecipant SET username=?, password=?, first_name=?, last_name=?, hackathon_id=?, team_id=? WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());
            ps.setString(3, p.getFirst_name());
            ps.setString(4, p.getLast_name());
            ps.setInt(5, p.getHackathonID());
            ps.setInt(6, p.getTeamID());
            ps.setString(7, p.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM partecipant WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Partecipant mapRowToPartecipant(ResultSet rs) throws SQLException {
        return new Partecipant(
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("hackathon_id"),
                rs.getInt("team_id"));
    }
}
