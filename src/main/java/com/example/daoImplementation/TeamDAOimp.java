package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.TeamDAO;
import com.example.model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDAOimp implements TeamDAO {

    @Override
    public void addTeam(Team team) throws SQLException {
        // ✅ MODIFICATO: Query per includere leader_email
        String sql = "INSERT INTO teams (team_name, hackathon_id, max_members, leader_email) VALUES (?, ?, ?, ?)";
        // ✅ MODIFICATO: Uso di try-with-resources per la gestione sicura delle risorse
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, team.getTeamName());
            ps.setInt(2, team.getHackathonId());
            ps.setInt(3, team.getMaxMembers());
            ps.setString(4, team.getLeaderEmail()); // ✅ AGGIUNTO

            ps.executeUpdate();
        }
    }
    @Override
    public boolean updateTeam(Team team) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "UPDATE teams SET team_name = ?, hackathon_id = ?, max_members = ? WHERE id = ?";
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, team.getTeamName());
            ps.setInt(2, team.getHackathonId());
            ps.setInt(3, team.getMaxMembers());
            ps.setInt(4, team.getId());

            righeModificate = ps.executeUpdate();
        } finally {

            if (ps != null)
                ps.close();


        }

        return righeModificate > 0;
    }

    @Override
    public boolean deleteTeam(Team team) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "DELETE FROM teams WHERE id = ?";
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, team.getId());
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
    public List<Team> getAllTeams() throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null || con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "SELECT * FROM teams";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Team> teams = new ArrayList<Team>();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setTeamName(rs.getString("team_name"));
                team.setHackathonId(rs.getInt("hackathon_id"));
                team.setMaxMembers(rs.getInt("max_members"));

                teams.add(team);
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
        return teams;
    }
    // Aggiungi questi metodi alla classe TeamDAOimp esistente

    @Override
    public List<Team> getTeamsByHackathonId(int hackathonId) throws SQLException {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams WHERE hackathon_id = ?";
        // ✅ MODIFICATO: Uso di try-with-resources
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getInt("id"));
                    team.setTeamName(rs.getString("team_name"));
                    team.setHackathonId(rs.getInt("hackathon_id"));
                    team.setMaxMembers(rs.getInt("max_members"));
                    team.setLeaderEmail(rs.getString("leader_email")); // ✅ AGGIUNTO
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    @Override
    public Team getTeamById(int teamId) throws SQLException {
        Team team = null;
        String sql = "SELECT * FROM teams WHERE id = ?";
        // ✅ MODIFICATO: Uso di try-with-resources
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    team = new Team();
                    team.setId(rs.getInt("id"));
                    team.setTeamName(rs.getString("team_name"));
                    team.setHackathonId(rs.getInt("hackathon_id"));
                    team.setMaxMembers(rs.getInt("max_members"));
                    team.setLeaderEmail(rs.getString("leader_email")); // ✅ AGGIUNTO
                }
            }
        }
        return team;
    }
}