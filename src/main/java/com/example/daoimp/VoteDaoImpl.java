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
        String sql = "SELECT * FROM vote WHERE id = ?";
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
        String sql = "SELECT * FROM vote";
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
    public List<Vote> findByHackathonId(int hackathonId) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM vote WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
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
    public List<Vote> findByJudgeId(int judgeId) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM vote WHERE judge_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, judgeId);
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
        String sql = "SELECT * FROM vote WHERE team_id = ?";
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
    public void save(Vote vote) {
        String sql = "INSERT INTO vote (hackathon_id, judge_id, team_id, score) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, vote.getHackathonId());
            ps.setInt(2, vote.getJudgeId());
            ps.setInt(3, vote.getTeamId());
            ps.setInt(4, vote.getScore());
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
        String sql = "UPDATE vote SET hackathon_id = ?, judge_id = ?, team_id = ?, score = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vote.getHackathonId());
            ps.setInt(2, vote.getJudgeId());
            ps.setInt(3, vote.getTeamId());
            ps.setInt(4, vote.getScore());
            ps.setInt(5, vote.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM vote WHERE id = ?";
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
        int judgeId = rs.getInt("judge_id");
        int teamId = rs.getInt("team_id");
        int score = rs.getInt("score");
        return new Vote(id, hackathonId, judgeId, teamId, score, null);
    }
}
