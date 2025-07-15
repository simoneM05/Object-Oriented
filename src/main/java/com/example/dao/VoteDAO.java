package com.example.dao;

import com.example.model.Vote;
import java.util.List;
import java.util.Optional;

public interface VoteDAO {
    Optional<Vote> findById(int id);

    List<Vote> findAll();

    List<Vote> findByHackathonId(int hackathonId, String judgeEmail);

    public List<Vote> findByHackathonIdExcludingJudgeVotes(int hackathonId, String judgeEmail);

    public boolean hasJudgeVotedForTeam(int hackathonId, String judgeEmail, int teamId);

    List<Vote> findByJudgeEmail(String judgeEmail);

    List<Vote> findByTeamId(int teamId);

    public void save(Vote vote, int documentID);

    void update(Vote vote);

    void delete(int id);
}
