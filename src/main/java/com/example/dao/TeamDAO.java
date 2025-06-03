package com.example.dao;

import com.example.model.Team;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface TeamDAO {

    void addTeam (Team team) throws SQLException;
    boolean updateTeam (Team team) throws SQLException;
    boolean deleteTeam (Team team) throws SQLException;

    List<Team> getAllTeams() throws SQLException;



}
