package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.RequestDAO;
import com.example.model.Request;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RequestDAOImp implements RequestDAO {

    @Override
    public void addRequest(Request request) throws DuplicateRequestException, SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if(con == null){
            throw new SQLException("Connessione DB non riuscita");
        }


    }

    @Override
    public void updateRequest(Request request)  {

    }

    @Override
    public void deleteRequest(Request request) throws NoSuchElementException {

    }

    @Override
    public Request getRequestByRequestID(int queryRequestId) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }
        Request request = null;

        //HO MESSO QUALCOSA GIUSTO PER
        String sql = "SELECT * FROM request WHERE requestId = ?"; // Usa il placeholder
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, queryRequestId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int dbRequestId = rs.getInt("requestId");
                String dbMessage = rs.getString("message");
                int dbTeamId = rs.getInt("teamId");
                String emailRecivded = rs.getString("emailRecivded");
                boolean dbStatus = rs.getBoolean("status");
                request = new Request(dbMessage, dbTeamId, emailRecivded, dbRequestId, dbStatus);
            }


        }
        finally {
            if (rs != null) {
                try{
                    rs.close();

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try{
                    ps.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return request;
    }

    @Override
    public List<Request> getAllRequest() throws NoSuchElementException {
        return null;
    }







}
