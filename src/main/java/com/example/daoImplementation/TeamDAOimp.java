package com.example.daoImplementation;

import com.example.dao.TeamDAO;
import com.example.model.Request;
import com.example.model.Team;
import com.sun.jdi.request.DuplicateRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeamDAOimp implements TeamDAO {

    @Override
    public void addTeam(Team team) throws DuplicateRequestException {

    }

    @Override
    public void updateTeam(Team team)  {

    }


    @Override
    public void deleteTeam(Team team) throws NoSuchElementException {

    }

    @Override
    public Optional<Team> getTeamByID(int id) {
        return null;
    }

    @Override
    public List<Team> getAllTeams() throws NoSuchElementException {
        return null;
    }
}
