package com.example.dao;

import com.example.model.User;

public interface UserDAO {

    void addUser();
    User getUser();
    void updateUser();
    void removeUser();
    
}
