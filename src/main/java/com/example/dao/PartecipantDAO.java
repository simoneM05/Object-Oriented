package com.example.dao;

import com.example.model.Partecipant;
import java.util.List;
import java.util.Optional;

public interface PartecipantDAO {

    Optional<Partecipant> findByEmail(String email);

    List<Partecipant> findAll();

    List<Partecipant> findByHackathonId(int hackathonId);

    List<Partecipant> findByTeamId(int teamId);

    boolean save(Partecipant partecipant);

    boolean update(Partecipant partecipant);

    boolean deleteByEmail(String email);

    public boolean assignToTeam(String email, int teamId);
}
