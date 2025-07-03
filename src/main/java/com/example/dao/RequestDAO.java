package com.example.dao;

import com.example.model.Request;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface RequestDAO {
    void addRequest (Request request) throws SQLException;
    boolean updateRequest (Request request) throws SQLException;
    boolean deleteRequest(int requestId) throws  SQLException;
    List<Request> getAllRequest() throws NoSuchElementException, SQLException;
    Request getRequestById(int requestId) throws SQLException; // ✅ AGGIUNTO
    List<Request> getRequestsByReceiverEmail(String email) throws SQLException; // ✅ AGGIUNTO
    boolean updateRequestStatus(int requestId, String status) throws SQLException; // ✅ AGGIUNTO
}