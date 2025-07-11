package com.example.dao;

import com.example.model.Document;
import java.util.List;
import java.util.Optional;

public interface DocumentDAO {
    Optional<Document> findById(int id);

    List<Document> findAll();

    List<Document> findByHackathonId(int hackathonId);

    List<Document> findByTeamId(int teamId);

    void save(Document document);

    void update(Document document);

    void delete(int id);
}
