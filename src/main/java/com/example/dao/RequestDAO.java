package com.example.dao;

import com.example.model.Request;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.List;

public interface RequestDAO {

    void addRequest (Request request) throws DuplicateRequestException, SQLException;
    void updateRequest (Request request) throws SQLException;

    void deleteRequest (Request request) throws NoSuchElementException, SQLException;
    Request getRequestByRequestID(int requestId) throws SQLException;

    List<Request> getAllRequest() throws NoSuchElementException, SQLException;


}
