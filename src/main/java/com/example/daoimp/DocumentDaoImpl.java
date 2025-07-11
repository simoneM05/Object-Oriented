package com.example.daoimp;

import com.example.dao.DocumentDAO;
import com.example.database.DBConnection;
import com.example.model.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentDaoImpl implements DocumentDAO {

    @Override
    public Optional<Document> findById(int id) {
        String sql = "SELECT * FROM document WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Document> findAll() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM document";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public List<Document> findByHackathonId(int hackathonId) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM document WHERE hackathon_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hackathonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public List<Document> findByTeamId(int teamId) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM document WHERE team_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public void save(Document document) {
        String sql = "INSERT INTO document (hackathon_id, team_id, file_path) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, document.getHackathonId());
            ps.setInt(2, document.getTeamId());
            ps.setString(3, document.getFileName()); // Presumo che file_path corrisponda a fileName nel modello
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                document.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Document document) {
        String sql = "UPDATE document SET hackathon_id = ?, team_id = ?, file_path = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, document.getHackathonId());
            ps.setInt(2, document.getTeamId());
            ps.setString(3, document.getFileName());
            ps.setInt(4, document.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM document WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Document mapRowToDocument(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int hackathonId = rs.getInt("hackathon_id");
        int teamId = rs.getInt("team_id");
        String filePath = rs.getString("file_path");
        return new Document(id, hackathonId, teamId, filePath);
    }
}
