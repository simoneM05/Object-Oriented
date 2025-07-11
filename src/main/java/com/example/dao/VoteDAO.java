package com.example.dao;

import com.example.model.Vote;
import java.util.List;
import java.util.Optional;

public interface VoteDAO {
    Optional<Vote> findById(int id);

    List<Vote> findAll();

    List<Vote> findByHackathonId(int hackathonId);

    List<Vote> findByJudgeId(int judgeId);

    List<Vote> findByTeamId(int teamId);

    void save(Vote vote);

    void update(Vote vote);

    void delete(int id);
}
