package com.example.daoimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.dao.JudgeDAO;
import com.example.database.DBConnection;
import com.example.model.Judge;

public class JudgeDaoImpl implements JudgeDAO {

    @Override
    public boolean save(Judge j) {
        String sql = "INSERT INTO judges (user_email, hackathon_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getEmail());
            ps.setInt(2, j.getHackathonId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Judge j) {
        String sql = "UPDATE judges SET hackathon_id=? WHERE user_email=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, j.getHackathonId());
            ps.setString(2, j.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Judge mapRowToJudge(ResultSet rs) throws SQLException {
        return new Judge(
                rs.getString("user_email"),
                rs.getInt("hackathon_id"));
    }

    @Override
    public Optional<Judge> findByEmail(String email) {
        String sql = "SELECT * FROM judges WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToJudge(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Judge> findAll() {
        List<Judge> list = new ArrayList<>();
        String sql = "SELECT * FROM judges";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToJudge(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM judges WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metodo aggiuntivo per trovare giudici per hackathon
    public List<Judge> findByHackathonId(int hackathonId) {
        List<Judge> list = new ArrayList<>();
        String sql = "SELECT * FROM judges WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRowToJudge(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}