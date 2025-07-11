package com.example.daoimp;

import com.example.dao.HackathonDAO;
import com.example.database.DBConnection;
import com.example.model.Hackathon;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HackathonDaoImpl implements HackathonDAO {

    @Override
    public Optional<Hackathon> findById(int id) {
        String sql = "SELECT * FROM hackathons WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToHackathon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Hackathon> findAll() {
        List<Hackathon> hackathons = new ArrayList<>();
        String sql = "SELECT * FROM hackathons";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                hackathons.add(mapRowToHackathon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hackathons;
    }

    @Override
    public void save(Hackathon hackathon) {
        String sql = "INSERT INTO hackathons (title, location, start_date, end_date, max_participants, max_team_size, registration_start, registration_end, problem_description, organizer_user_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hackathon.getTitle());
            ps.setString(2, hackathon.getLocation());
            ps.setTimestamp(3, Timestamp.valueOf(hackathon.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(hackathon.getEndDate()));
            ps.setInt(5, hackathon.getMaxParticipants());
            ps.setInt(6, hackathon.getMaxTeamSize());

            // FIX per i null
            if (hackathon.getRegistrationStart() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(hackathon.getRegistrationStart()));
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }

            if (hackathon.getRegistrationEnd() != null) {
                ps.setTimestamp(8, Timestamp.valueOf(hackathon.getRegistrationEnd()));
            } else {
                ps.setNull(8, Types.TIMESTAMP);
            }

            ps.setString(9, hackathon.getProblemDescription());
            ps.setString(10, hackathon.getOrganizerUserEmail());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                hackathon.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Hackathon hackathon) {
        String sql = "UPDATE hackathons SET title = ?, location = ?, start_date = ?, end_date = ?, max_participants = ?, max_team_size = ?, registration_start = ?, registration_end = ?, problem_description = ?, organizer_user_email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hackathon.getTitle());
            ps.setString(2, hackathon.getLocation());

            ps.setTimestamp(3, hackathon.getStartDate() != null ? Timestamp.valueOf(hackathon.getStartDate()) : null);
            ps.setTimestamp(4, hackathon.getEndDate() != null ? Timestamp.valueOf(hackathon.getEndDate()) : null);

            ps.setInt(5, hackathon.getMaxParticipants());
            ps.setInt(6, hackathon.getMaxTeamSize());

            ps.setTimestamp(7,
                    hackathon.getRegistrationStart() != null ? Timestamp.valueOf(hackathon.getRegistrationStart())
                            : null);
            ps.setTimestamp(8,
                    hackathon.getRegistrationEnd() != null ? Timestamp.valueOf(hackathon.getRegistrationEnd()) : null);

            ps.setString(9, hackathon.getProblemDescription());
            ps.setString(10, hackathon.getOrganizerUserEmail());

            ps.setInt(11, hackathon.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM hackathons WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Hackathon mapRowToHackathon(ResultSet rs) throws SQLException {
        Timestamp registrationStartTs = rs.getTimestamp("registration_start");
        LocalDateTime registrationStart = registrationStartTs != null ? registrationStartTs.toLocalDateTime() : null;

        Timestamp registrationEndTs = rs.getTimestamp("registration_end");
        LocalDateTime registrationEnd = registrationEndTs != null ? registrationEndTs.toLocalDateTime() : null;

        return new Hackathon(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("location"),
                rs.getTimestamp("start_date") != null ? rs.getTimestamp("start_date").toLocalDateTime() : null,
                rs.getTimestamp("end_date") != null ? rs.getTimestamp("end_date").toLocalDateTime() : null,
                rs.getInt("max_participants"),
                rs.getInt("max_team_size"),
                registrationStart,
                rs.getString("problem_description"),
                rs.getString("organizer_user_email"));
    }

    @Override
    public boolean updateProblemDescription(int hackathonId, String problemDescription) {
        String sql = "UPDATE hackathons SET problem_description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, problemDescription);
            ps.setInt(2, hackathonId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Hackathon> findByTitle(String title) {
        String sql = "SELECT * FROM hackathons WHERE title = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToHackathon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
