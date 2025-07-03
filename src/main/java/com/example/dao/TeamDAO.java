package com.example.dao;

import com.example.model.Team;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface TeamDAO {
    void addTeam (Team team) throws SQLException;
    boolean updateTeam (Team team) throws SQLException;
    boolean deleteTeam (Team team) throws SQLException;
    List<Team> getAllTeams() throws SQLException;
    List<Team> getTeamsByHackathonId(int hackathonId) throws SQLException; // ✅ AGGIUNTO
    Team getTeamById(int teamId) throws SQLException; // ✅ AGGIUNTO
}