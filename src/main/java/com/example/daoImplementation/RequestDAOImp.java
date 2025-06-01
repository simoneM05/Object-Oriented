package com.example.daoImplementation;

import com.example.dao.RequestDAO;
import com.example.model.Request;
import com.sun.jdi.request.DuplicateRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RequestDAOImp implements RequestDAO {

    @Override
    public void addRequest(Request request) throws DuplicateRequestException {

    }

    @Override
    public void updateRequest(Request request)  {

    }

    @Override
    public void deleteRequest(Request request) throws NoSuchElementException {

    }

    @Override
    public Optional<Request> getRequestByTeamID(String teamId) {
        return null;
    }

    @Override
    public List<Request> getAllRequest() throws NoSuchElementException {
        return null;
    }







}
