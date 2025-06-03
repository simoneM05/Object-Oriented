package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.TeamDAO;
import com.example.model.Request;
import com.example.model.Team;
import com.sun.jdi.request.DuplicateRequestException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeamDAOimp implements TeamDAO {

    @Override
    public void addTeam(Team team) throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();
        if (con == null) {
            throw new SQLException("Connessione DB non riuscita");
        }
        String sql ="";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(sql);

            //ELENCO DI VALORI DA AGGIUNGERE
            rs  = ps.executeQuery();
            if(rs.next()){


                //AGGIUNTA QUERY

            }
        }
        finally{
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
    public boolean updateTeam(Team team)  throws SQLException {
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
    public boolean deleteTeam(Team team) throws SQLException {
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
    public List<Team> getAllTeams() throws SQLException {

        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "SELECT * FROM request";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Team> teams = new ArrayList<Team>();

        try{
            ps = con.prepareStatement(sql);
            while (rs.next()) {
                //creazione di un oggetto Request per ogni tupla (DA CAPIRE SE USARE UN COSTRUTTORE VUOTO O MENO)
                Team team = new Team();

                //serie di set per popolare il costruttore

                teams.add(team);
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
        return teams;
    }
}
