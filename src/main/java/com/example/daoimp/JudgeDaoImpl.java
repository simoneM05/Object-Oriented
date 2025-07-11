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
        String sql = "INSERT INTO judge (email, username, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getEmail());
            ps.setString(2, j.getUsername());
            ps.setString(3, j.getPassword());
            ps.setString(4, j.getFirst_name()); // <-- qui
            ps.setString(5, j.getLast_name()); // <-- qui

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Judge j) {
        String sql = "UPDATE judge SET username=?, password=?, first_name=?, last_name=? WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getUsername());
            ps.setString(2, j.getPassword());
            ps.setString(3, j.getFirst_name()); // <-- qui
            ps.setString(4, j.getLast_name()); // <-- qui
            ps.setString(5, j.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Judge mapRowToJudge(ResultSet rs) throws SQLException {
        return new Judge(
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"));
    }

    @Override
    public Optional<Judge> findByEmail(String email) {
        String sql = "SELECT * FROM judge WHERE email = ?";
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
        String sql = "SELECT * FROM judge";
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
        String sql = "DELETE FROM judge WHERE email = ?";
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
