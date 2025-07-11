package com.example.model;

public class Vote {
    private int id;
    private int hackathonId;
    private int judgeId; // potresti usare email o un altro identificativo, qui uso int per esempio
    private int teamId;
    private int score;
    private String comment;

    public Vote(int id, int hackathonId, int judgeId, int teamId, int score, String comment) {
        this.id = id;
        this.hackathonId = hackathonId;
        this.judgeId = judgeId;
        this.teamId = teamId;
        this.score = score;
        this.comment = comment;
    }

    public Vote(int hackathonId, int judgeId, int teamId, int score, String comment) {
        this(0, hackathonId, judgeId, teamId, score, comment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public int getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(int judgeId) {
        this.judgeId = judgeId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", hackathonId=" + hackathonId +
                ", judgeId=" + judgeId +
                ", teamId=" + teamId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
