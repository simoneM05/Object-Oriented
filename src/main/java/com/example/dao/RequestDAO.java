package com.example.dao;

import com.example.model.Request;
import com.sun.jdi.request.DuplicateRequestException;

import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.List;

public interface RequestDAO {

    void addRequest (Request request) throws DuplicateRequestException;
    void updateRequest (Request request);

    void deleteRequest (Request request) throws NoSuchElementException;
    Optional<Request> getRequestByTeamID(String teamId);

    List<Request> getAllRequest() throws NoSuchElementException;

}
