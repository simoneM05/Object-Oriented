package com.example.dao;

import com.example.model.Team;
import com.sun.jdi.request.DuplicateRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface TeamDAO {

    void addTeam (Team team) throws DuplicateRequestException;
    void updateTeam (Team team);
    void deleteTeam (Team team) throws NoSuchElementException;
    Optional<Team> getTeamByID(int id);
    List<Team> getAllTeams() throws NoSuchElementException;



}
