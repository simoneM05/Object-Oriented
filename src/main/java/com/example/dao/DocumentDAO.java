package com.example.dao;

import com.example.model.Document;
import java.util.List;
import java.util.Optional;

public interface DocumentDAO {
    Optional<Document> findById(int id);

    List<Document> findAll();

    List<Document> findByTeamId(int teamId);

    public List<Document> findByHackathonId(int hackathonId, String judgeEmail);

    public List<Document> findAllByHackathonId(int hackathonId);

    void save(Document document);

    void update(Document document);

    void delete(int id);
}
