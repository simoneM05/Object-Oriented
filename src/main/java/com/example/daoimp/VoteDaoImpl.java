package com.example.daoimp;

import com.example.dao.VoteDAO;
import com.example.database.DBConnection;
import com.example.model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoteDaoImpl implements VoteDAO {

    @Override
    public Optional<Vote> findById(int id) {
        String sql = "SELECT * FROM votes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Vote> findAll() {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    @Override
    public List<Vote> findByHackathonId(int hackathonId, String judgeEmail) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE hackathon_id = ? AND judge_user_email != ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ps.setString(2, judgeEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    // Nuovo metodo per filtrare i team che NON hanno ancora ricevuto voto da un
    // giudice specifico
    public List<Vote> findByHackathonIdExcludingJudgeVotes(int hackathonId, String judgeEmail) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE hackathon_id = ? AND NOT EXISTS (" +
                "SELECT 1 FROM votes v2 WHERE v2.hackathon_id = ? AND v2.judge_user_email = ? " +
                "AND v2.team_id = votes.team_id)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ps.setInt(2, hackathonId);
            ps.setString(3, judgeEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    // Metodo per verificare se un giudice ha già votato per un team specifico
    public boolean hasJudgeVotedForTeam(int hackathonId, String judgeEmail, int teamId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE hackathon_id = ? AND judge_user_email = ? AND team_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ps.setString(2, judgeEmail);
            ps.setInt(3, teamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Vote> findByJudgeEmail(String judgeEmail) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE judge_user_email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, judgeEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    @Override
    public List<Vote> findByTeamId(int teamId) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                votes.add(mapRowToVote(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    @Override
    public void save(Vote vote, int documentID) {
        String sql = "INSERT INTO votes (hackathon_id, judge_user_email, team_id, score, comment, document_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, vote.getHackathonId());
            ps.setString(2, vote.getJudgeEmail());
            ps.setInt(3, vote.getTeamId());
            ps.setInt(4, vote.getScore());
            ps.setString(5, vote.getComment());
            ps.setInt(6, documentID); // ora combacia con la query

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                vote.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Vote vote) {
        String sql = "UPDATE votes SET hackathon_id = ?, judge_user_email = ?, team_id = ?, score = ?, comment = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vote.getHackathonId());
            ps.setString(2, vote.getJudgeEmail());
            ps.setInt(3, vote.getTeamId());
            ps.setInt(4, vote.getScore());
            ps.setString(5, vote.getComment());
            ps.setInt(6, vote.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM votes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vote mapRowToVote(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int hackathonId = rs.getInt("hackathon_id");
        String judgeEmail = rs.getString("judge_user_email");
        int teamId = rs.getInt("team_id");
        int score = rs.getInt("score");
        String comment = rs.getString("comment");
        return new Vote(id, hackathonId, judgeEmail, teamId, score, comment);
    }
}