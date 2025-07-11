package com.example.model;

public class Document {
    private int id;
    private int hackathonId;
    private int teamId;
    private String fileName;

    public Document(int id, int hackathonId, int teamId, String fileName) {
        this.id = id;
        this.hackathonId = hackathonId;
        this.teamId = teamId;
        this.fileName = fileName;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", hackathonId=" + hackathonId +
                ", teamId=" + teamId +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
