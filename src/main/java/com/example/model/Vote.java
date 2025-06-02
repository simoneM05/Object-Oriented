package com.example.model;

public class Vote {

    private int valore;
    private int teamdId;
    private int judgeId;
    private int voteId;


    public Vote(int judgeId,int teamdId, int valore, int voteId) {
        this.judgeId = judgeId;
        this.teamdId = teamdId;
        this.valore = valore;
        this.voteId = voteId;

    }
    public int getJudgeId() {
        return judgeId;
    }
    public int getTeamdId() {
        return teamdId;
    }
    public int getValore() {
        return valore;
    }
    public void setVote(int valore){
        this.valore = valore;
    }


}