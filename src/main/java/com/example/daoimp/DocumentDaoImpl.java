package com.example.daoimp;

import com.example.dao.DocumentDAO;
import com.example.database.DBConnection;
import com.example.model.Document;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentDaoImpl implements DocumentDAO {

    @Override
    public Optional<Document> findById(int id) {
        String sql = "SELECT * FROM documents WHERE id = ?";
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
        String sql = "SELECT * FROM documents";
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
    public List<Document> findByTeamId(int teamId) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM documents WHERE team_id = ?";
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
        String sql = "INSERT INTO documents (filename, upload_date, team_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, document.getFileName());
            ps.setTimestamp(2, Timestamp.valueOf(document.getUploadDate()));
            ps.setInt(3, document.getTeamId());

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
        String sql = "UPDATE documents SET filename = ?, upload_date = ?, team_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, document.getFileName());
            ps.setTimestamp(2, Timestamp.valueOf(document.getUploadDate()));
            ps.setInt(3, document.getTeamId());
            ps.setInt(4, document.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM documents WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the exception
        }
    }

    private Document mapRowToDocument(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String documentPath = rs.getString("filename");
        LocalDateTime uploadDate = rs.getTimestamp("upload_date").toLocalDateTime();
        int teamId = rs.getInt("team_id");

        return new Document(id, documentPath, uploadDate, teamId);
    }

    @Override
    public List<Document> findByHackathonId(int hackathonId) {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT d.* FROM documents d JOIN teams t ON d.team_id = t.id WHERE t.hackathon_id = ?";

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
}