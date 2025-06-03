package com.example.daoImplementation;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.UserDAO;

import com.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public void addUser(User user) throws SQLException {
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
    public boolean updateUser(User user)  throws SQLException {
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
    public boolean deleteUser(User user) throws SQLException {
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
    public List<User> getAllUsers() throws SQLException {
        Connection con = ConnessioneDatabase.getInstance().getConnection();

        if (con == null ||con.isClosed()) {
            throw new SQLException("Connessione DB non riuscita");
        }

        String sql = "SELECT * FROM request";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = new ArrayList<User>();

        try{
            ps = con.prepareStatement(sql);
            while (rs.next()) {
                //creazione di un oggetto Request per ogni tupla (DA CAPIRE SE USARE UN COSTRUTTORE VUOTO O MENO)
                User user = new User();

                //serie di set per popolare il costruttore

                users.add(user);
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
        return users;
    }


}
