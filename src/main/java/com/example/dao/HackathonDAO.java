package com.example.dao;

import com.example.model.Hackathon;

import java.util.List;
import java.util.Optional;

public interface HackathonDAO {
    Optional<Hackathon> findById(int id);

    List<Hackathon> findAll();

    Optional<Hackathon> findByTitle(String title);

    void save(Hackathon hackathon);

    void update(Hackathon hackathon);

    boolean updateProblemDescription(int hackathonId, String problemDescription);

    void delete(int id);
}
