package com.example.daoimp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.dao.TeamDAO;
import com.example.database.DBConnection;
import com.example.model.Team;

public class TeamDaoImpl implements TeamDAO {

    @Override
    public Optional<Team> findById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int teamId = rs.getInt("id");
                String name = rs.getString("team_name");
                int hackathonId = rs.getInt("hackathon_id");
                Team team = new Team(teamId, name, hackathonId);
                return Optional.of(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int teamId = rs.getInt("id");
                String name = rs.getString("name");
                int hackathonId = rs.getInt("hackathon_id");
                Team team = new Team(teamId, name, hackathonId);
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public List<Team> findByHackathonId(int hackathonId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int teamId = rs.getInt("id");
                String name = rs.getString("name");
                Team team = new Team(teamId, name, hackathonId);
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public void save(Team team) {
        String sql = "INSERT INTO teams (name, hackathon_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, team.getName());
            ps.setInt(2, team.getHackathonId());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                team.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Team team) {
        String sql = "UPDATE teams SET name = ?, hackathon_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, team.getName());
            ps.setInt(2, team.getHackathonId());
            ps.setInt(3, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM teams WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
