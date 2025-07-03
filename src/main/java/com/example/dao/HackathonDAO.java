package com.example.dao;

import com.example.model.Hackathon;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface HackathonDAO {
    void addHackathon (Hackathon hackathon) throws SQLException;
    boolean updateHackathon (Hackathon hackathon) throws SQLException;
    boolean deleteHackathon (int hackathonId) throws SQLException;
    List<Hackathon> getAllHackathons() throws SQLException;
    Hackathon getHackathonById(int hackathonId) throws SQLException; // ✅ AGGIUNTO
}