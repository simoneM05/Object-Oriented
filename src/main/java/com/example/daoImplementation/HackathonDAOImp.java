package com.example.daoImplementation;
import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.HackathonDAO;
import com.example.model.Hackathon;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HackathonDAOImp implements HackathonDAO {

    @Override
    public void addHackathon (Hackathon hackathon) throws SQLException{
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if(con == null){
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = con.prepareStatement(sql);

            //ELENCO VALORI

            rs = ps.executeQuery();
            if(rs.next()){

                //AGGIUNTA QUERY
            }
        }
        finally{
            if(rs != null){
                try{
                    rs.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(ps != null){
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
    public boolean  updateHackathon (Hackathon hackathon)  throws SQLException{
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null){
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

    public boolean deleteHackathon(Hackathon hackathon) throws SQLException{
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null){
            throw new SQLException("Connessione DB non riuscita");
        }
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
    public List<Hackathon> getAllHackathons() throws SQLException{
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null){
            throw new SQLException("Connessione DB non riuscita");
        }
        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Hackathon>  hackathons = new ArrayList<>();

        try{
            ps = con.prepareStatement(sql);
            while (rs.next())
            {
                Hackathon hackathon = new Hackathon();

                //serie di set per popolare il costruttore

                hackathons.add(hackathon);
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


        return hackathons;
    }
}
