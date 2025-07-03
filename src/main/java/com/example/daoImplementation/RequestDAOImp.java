
package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.RequestDAO;
import com.example.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RequestDAOImp implements RequestDAO {

    @Override
    public void addRequest(Request request) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "INSERT INTO requests (message, status, team_id, sender_participant_email, receiver_participant_email) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, request.getMessage());
            ps.setString(2, request.getStatus() != null ? request.getStatus() : "PENDING");
            ps.setInt(3, request.getTeamId());
            ps.setString(4, request.getSenderUserEmail());
            ps.setString(5, request.getReceiverUserEmail());

            ps.executeUpdate();
            System.out.println("Request aggiunta con successo.");

        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in addRequest: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in addRequest: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean updateRequest(Request request) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "UPDATE requests SET message = ?, status = ?, team_id = ?, sender_participant_email = ?, receiver_participant_email = ? WHERE id = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, request.getMessage());
            ps.setString(2, request.getStatus());
            ps.setInt(3, request.getTeamId());
            ps.setString(4, request.getSenderUserEmail());
            ps.setString(5, request.getReceiverUserEmail());
            ps.setInt(6, request.getId());

            righeModificate = ps.executeUpdate();

        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in updateRequest: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in updateRequest: " + e.getMessage());
                }
            }
        }

        return righeModificate > 0;
    }

    @Override
    public boolean deleteRequest(int requestId) throws SQLException {
        Connection con = null;
        PreparedStatement checkExistStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String queryCheckExist = "SELECT COUNT(*) FROM requests WHERE id = ?";
            checkExistStmt = con.prepareStatement(queryCheckExist);
            checkExistStmt.setInt(1, requestId);
            rs = checkExistStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                throw new NoSuchElementException("Nessuna richiesta trovata con ID: " + requestId);
            }

            String queryDelete = "DELETE FROM requests WHERE id = ?";
            deleteStmt = con.prepareStatement(queryDelete);
            deleteStmt.setInt(1, requestId);

            return deleteStmt.executeUpdate() > 0;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in deleteRequest: " + e.getMessage());
                }
            }
            if (checkExistStmt != null) {
                try {
                    checkExistStmt.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in deleteRequest: " + e.getMessage());
                }
            }
            if (deleteStmt != null) {
                try {
                    deleteStmt.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in deleteRequest: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in deleteRequest: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<Request> getAllRequest() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Request> requests = new ArrayList<>();

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "SELECT * FROM requests";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setTeamId(rs.getInt("team_id"));
                request.setSenderUserEmail(rs.getString("sender_participant_email"));
                request.setReceiverUserEmail(rs.getString("receiver_participant_email"));

                requests.add(request);
            }

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getAllRequest: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in getAllRequest: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in getAllRequest: " + e.getMessage());
                }
            }
        }

        return requests;
    }

    @Override
    public Request getRequestById(int requestId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "SELECT * FROM requests WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setTeamId(rs.getInt("team_id"));
                request.setSenderUserEmail(rs.getString("sender_participant_email"));
                request.setReceiverUserEmail(rs.getString("receiver_participant_email"));
                return request;
            }
            return null;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getRequestById: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in getRequestById: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in getRequestById: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<Request> getRequestsByReceiverEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Request> requests = new ArrayList<>();

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "SELECT * FROM requests WHERE receiver_participant_email = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setMessage(rs.getString("message"));
                request.setStatus(rs.getString("status"));
                request.setTeamId(rs.getInt("team_id"));
                request.setSenderUserEmail(rs.getString("sender_participant_email"));
                request.setReceiverUserEmail(rs.getString("receiver_participant_email"));

                requests.add(request);
            }

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del ResultSet in getRequestsByReceiverEmail: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in getRequestsByReceiverEmail: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in getRequestsByReceiverEmail: " + e.getMessage());
                }
            }
        }

        return requests;
    }

    @Override
    public boolean updateRequestStatus(int requestId, String status) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            con = ConnessioneDatabase.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                throw new SQLException("Connessione DB non riuscita o chiusa.");
            }

            String sql = "UPDATE requests SET status = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, requestId);

            righeModificate = ps.executeUpdate();

        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura del PreparedStatement in updateRequestStatus: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("Errore durante la chiusura della Connection in updateRequestStatus: " + e.getMessage());
                }
            }
        }

        return righeModificate > 0;
    }
}