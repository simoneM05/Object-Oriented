package com.example.implementazioniDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.ConnessioneDB.ConnessioneDatabase;
import com.example.dao.UserDAO;
import com.example.model.User;

public class UserDAOimp implements UserDAO {

    @Override
    public void addUser() {
        try {
            ConnessioneDatabase con = ConnessioneDatabase.getInstance();
            PreparedStatement addUserPS = con.connection
                    .prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public User getUser() {
    }

    @Override
    public void updateUser() {
    }

    @Override
    public void removeUser() {
    }

}
