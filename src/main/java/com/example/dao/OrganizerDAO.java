package com.example.dao;

import com.example.model.Organizer;
import java.util.List;
import java.util.Optional;

public interface OrganizerDAO {

    Optional<Organizer> findByEmail(String email);

    List<Organizer> findAll();

    List<Organizer> findByHackathonId(String hackathonId);

    boolean save(Organizer organizer);

    boolean update(Organizer organizer);

    boolean deleteByEmail(String email);
}
