package com.example.dao;

import com.example.model.Judge;
import java.util.List;
import java.util.Optional;

public interface JudgeDAO {

    Optional<Judge> findByEmail(String email);

    List<Judge> findAll();

    boolean save(Judge judge);

    boolean update(Judge judge);

    boolean deleteByEmail(String email);
}
