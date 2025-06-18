package com.example.model;

public class Vote {
    private int id; // Corrisponde a id SERIAL PRIMARY KEY nel DB
    private int score; // Corrisponde a score INT NOT NULL CHECK (score >= 0 AND score <= 10)
    private int teamId; // Corrisponde a team_id INT NOT NULL
    private String judgeUserEmail; // Corrisponde a judge_user_email VARCHAR(255) NOT NULL
    private int hackathonId; // Corrisponde a hackathon_id INT NOT NULL


    public Vote(int id, int score, int teamId, String judgeUserEmail, int hackathonId) {
        this.id = id;
        this.score = score;
        this.teamId = teamId;
        this.judgeUserEmail = judgeUserEmail;
        this.hackathonId = hackathonId;
    }

    // Costruttore per nuovo voto (senza ID)
    public Vote(int score, int teamId, String judgeUserEmail, int hackathonId) {
        this.score = score;
        this.teamId = teamId;
        this.judgeUserEmail = judgeUserEmail;
        this.hackathonId = hackathonId;
    }

    // Costruttore vuoto
    public Vote() {
    }

    // --- Getter e Setter ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getJudgeUserEmail() {
        return judgeUserEmail;
    }

    public void setJudgeUserEmail(String judgeUserEmail) {
        this.judgeUserEmail = judgeUserEmail;
    }

    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", score=" + score +
                ", teamId=" + teamId +
                ", judgeUserEmail='" + judgeUserEmail + '\'' +
                ", hackathonId=" + hackathonId +
                '}';
    }
}