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
    public void addRequest(Request request) throws SQLException { // forse potrebbe lanciare un eccezione per la duplicazione, ma la aggiungerò dopo
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if(con == null|| con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;


        try{
            ps = con.prepareStatement(sql);

            //ELENCO DI VALORI DA AGGIUNGERE

            rs = ps.executeQuery();
            if(rs.next()) {


                //AGGIUNTA QUERY

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
        }



    @Override
    public boolean updateRequest(Request request)  throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();


        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }
        String sql = "";
        PreparedStatement ps = null;
        int righeModificate = 0;

        try {
            ps = con.prepareStatement(sql);


            //ELENCO DI VALORI DA AGGIORNARE

            righeModificate = ps.executeUpdate();
        }
        finally {

            if (ps != null) {
                try{
                    ps.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

        //VERO SE ALMENO UNA RIGA è STATA MODIFICATA
        return righeModificate > 0;
    }

    @Override
    public boolean deleteRequest(Request request) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        //QUERY
        String sql = "";
        PreparedStatement ps = null;
        int righeModificate = 0;
        try{
            ps = con.prepareStatement(sql);

            //ELIMINAZIONE

            righeModificate = ps.executeUpdate();
        }
        finally {
            if (ps != null) {
                try{
                    ps.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return righeModificate > 0;

    }



    @Override
    public List<Request> getAllRequest() throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "SELECT * FROM request";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Request> requests = new ArrayList<Request>();

        try{
            ps = con.prepareStatement(sql);
            while (rs.next()) {
                //creazione di un oggetto Request per ogni tupla (DA CAPIRE SE USARE UN COSTRUTTORE VUOTO O MENO)
                Request request = new Request();

                //serie di set per popolare il costruttore

                requests.add(request);
            }
            rs = ps.executeQuery();
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
        return requests;
    }


}
