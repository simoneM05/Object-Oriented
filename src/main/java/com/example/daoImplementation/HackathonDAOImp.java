package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.HackathonDAO;
import com.example.model.Hackathon;
// ✅ RIMOSSO: import com.sun.jdi.request.DuplicateRequestException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HackathonDAOImp implements HackathonDAO {


    @Override
    public void addHackathon(Hackathon hackathon) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            String sql = "INSERT INTO hackathons (title, location, start_date, end_date, max_participants, max_team_size, registration_start, registration_end, problem_description, organizer_user_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, hackathon.getTitle());
            ps.setString(2, hackathon.getLocation());
            ps.setTimestamp(3, Timestamp.valueOf(hackathon.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(hackathon.getEndDate()));
            ps.setInt(5, hackathon.getMaxParticipants());
            ps.setInt(6, hackathon.getMaxTeamSize());
            ps.setTimestamp(7, Timestamp.valueOf(hackathon.getRegistrationStart()));
            ps.setTimestamp(8, Timestamp.valueOf(hackathon.getRegistrationEnd()));
            ps.setString(9, hackathon.getProblemDescription());
            ps.setString(10, hackathon.getOrganizerUserEmail());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    @Override
    public boolean updateHackathon(Hackathon hackathon) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int righeModificate = 0;
        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            String sql = "UPDATE hackathons SET title = ?, location = ?, start_date = ?, end_date = ?, max_participants = ?, max_team_size = ?, registration_start = ?, registration_end = ?, problem_description = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, hackathon.getTitle());
            ps.setString(2, hackathon.getLocation());
            ps.setTimestamp(3, Timestamp.valueOf(hackathon.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(hackathon.getEndDate()));
            ps.setInt(5, hackathon.getMaxParticipants());
            ps.setInt(6, hackathon.getMaxTeamSize());
            ps.setTimestamp(7, Timestamp.valueOf(hackathon.getRegistrationStart()));
            ps.setTimestamp(8, Timestamp.valueOf(hackathon.getRegistrationEnd()));
            ps.setString(9, hackathon.getProblemDescription());
            ps.setInt(10, hackathon.getId());
            righeModificate = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return righeModificate > 0;
    }

    // ...
    @Override
    public boolean deleteHackathon(int hackathonId) throws SQLException {
        // ✅ CORRETTO: Nome tabella da "hackathon" a "hackathons"
        String queryCheckExist = "SELECT COUNT(*) FROM hackathons WHERE id = ?";
        String queryDelete = "DELETE FROM hackathons WHERE id = ?";

        try (
                Connection connection = ConnessioneDatabase.getInstance().getConnection();
                PreparedStatement checkExistStmt = connection.prepareStatement(queryCheckExist);
                PreparedStatement deleteStmt = connection.prepareStatement(queryDelete)
        ) {
            checkExistStmt.setInt(1, hackathonId);
            ResultSet rs = checkExistStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new NoSuchElementException("Nessun Hackathon trovato con l'ID: " + hackathonId);
            }

            deleteStmt.setInt(1, hackathonId);
            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Errore durante l'eliminazione dell'Hackathon con ID: " + hackathonId, e);
        }
    }
// ...


    @Override
    public List<Hackathon> getAllHackathons() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Hackathon> hackathons = new ArrayList<>();
        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            String sql = "SELECT * FROM hackathons";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Hackathon hackathon = new Hackathon();
                hackathon.setId(rs.getInt("id"));
                hackathon.setTitle(rs.getString("title"));
                hackathon.setLocation(rs.getString("location"));
                hackathon.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
                hackathon.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                hackathon.setMaxParticipants(rs.getInt("max_participants"));
                hackathon.setMaxTeamSize(rs.getInt("max_team_size"));
                hackathon.setRegistrationStart(rs.getTimestamp("registration_start").toLocalDateTime());
                hackathon.setRegistrationEnd(rs.getTimestamp("registration_end").toLocalDateTime());
                hackathon.setProblemDescription(rs.getString("problem_description"));
                hackathon.setOrganizerUserEmail(rs.getString("organizer_user_email"));
                hackathons.add(hackathon);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return hackathons;
    }

// Aggiungi questo metodo alla classe HackathonDAOImp esistente

    @Override
    public Hackathon getHackathonById(int hackathonId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "SELECT * FROM hackathons WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, hackathonId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Hackathon hackathon = new Hackathon();
                hackathon.setId(rs.getInt("id"));
                hackathon.setTitle(rs.getString("title"));
                hackathon.setLocation(rs.getString("location"));
                hackathon.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
                hackathon.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                hackathon.setMaxParticipants(rs.getInt("max_participants"));
                hackathon.setMaxTeamSize(rs.getInt("max_team_size"));
                hackathon.setRegistrationStart(rs.getTimestamp("registration_start").toLocalDateTime());
                hackathon.setRegistrationEnd(rs.getTimestamp("registration_end").toLocalDateTime());
                hackathon.setProblemDescription(rs.getString("problem_description"));
                hackathon.setOrganizerUserEmail(rs.getString("organizer_user_email"));
                return hackathon;
            }
            return null;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getHackathonById: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in getHackathonById: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in getHackathonById: " + e.getMessage());
                }
            }
        }
    }

}