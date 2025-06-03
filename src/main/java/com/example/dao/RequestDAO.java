package com.example.dao;

import com.example.model.Request;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.List;

public interface RequestDAO {

    void addRequest (Request request) throws SQLException;
    boolean updateRequest (Request request) throws SQLException;

    boolean deleteRequest(Request request) throws  SQLException;

    List<Request> getAllRequest() throws NoSuchElementException, SQLException;


}
