package com.example.dao;

import com.example.model.User;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ArrayList;

public interface UserDAO {

    void addUser(User user) throws SQLException;

    boolean deleteUser(User user) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    User getUserByEmail(String email) throws SQLException, NoSuchElementException;

    // il numero di metodi dipende molto dal tipo di query che vogliamo usare e
    // quante ne vogliamo

}
