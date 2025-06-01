package com.example.dao;

import com.example.model.User;
import com.sun.jdi.request.DuplicateRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ArrayList;

public interface UserDAO {

    void addUser(User user) throws DuplicateRequestException;
    void deleteUser(User user) throws NoSuchElementException;
    void updateUser(User user);

    //Optional è un container che previene l'uso continuo di NullPointerExcpetion
    Optional<User> getUserByID(int id) throws NoSuchElementException;
    Optional<User> getUserByEmail(String email) throws NoSuchElementException;
    List<User> getAllUsers() throws NoSuchElementException;

    //il numero di metodi dipende molto dal tipo di query che vogliamo usare e quante ne vogliamo

    
}
