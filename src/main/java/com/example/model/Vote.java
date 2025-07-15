package com.example.model;

public class Vote {
    private int id;
    private int hackathonId;
    private String judgeEmail; // potresti usare email o un altro identificativo, qui uso int per esempio
    private int teamId;
    private int score;
    private String comment;

    public Vote(int id, int hackathonId, String judgeEmail, int teamId, int score, String comment) {
        this.id = id;
        this.hackathonId = hackathonId;
        this.judgeEmail = judgeEmail;
        this.teamId = teamId;
        this.score = score;
        this.comment = comment;
    }

    public Vote(int hackathonId, String judgeEmail, int teamId, int score, String comment) {
        this(0, hackathonId, judgeEmail, teamId, score, comment);
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

    public String getJudgeEmail() {
        return judgeEmail;
    }

    public void setJudgeEmail(String judgeEmail) {
        this.judgeEmail = judgeEmail;
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
                ", judgeEmail=" + judgeEmail +
                ", teamId=" + teamId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
