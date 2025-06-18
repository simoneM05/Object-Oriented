package com.example.dao;

import com.example.model.Hackathon;
import com.sun.jdi.request.DuplicateRequestException;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public interface HackathonDAO {

    //se l'hackathon esiste, lancio un eccezione che specifica la duplicazione
    void addHackathon (Hackathon hackathon) throws SQLException;
    boolean updateHackathon (Hackathon hackathon)throws SQLException;

    // se voglio cancellare un Hackathon che non esiste, lancio un eccezione adatta a questo caso
    boolean deleteHackathon (Hackathon hackathon) throws SQLException;
    List<Hackathon> getAllHackathons() throws SQLException;




}
