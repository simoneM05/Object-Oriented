package com.example.dao;

import com.example.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamDAO {
    Optional<Team> findById(int id);

    List<Team> findAll();

    List<Team> findByHackathonId(int hackathonId);

    void save(Team team);

    void update(Team team);

    void delete(int id);
}
