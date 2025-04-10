package com.example.model;

public class Vote {

    private int valore;
    private String teamdId;
    private String judgeId;

    public Vote(String judgeId, String teamdId, int valore) {
        this.judgeId = judgeId;
        this.teamdId = teamdId;
        this.valore = valore;
    }

}