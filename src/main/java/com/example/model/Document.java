package com.example.model;

import java.time.LocalDateTime;

public class Document {
    private int id;
    private int teamId;
    private LocalDateTime uploadDate;
    private String fileName;

    public Document(int teamId, String fileName) {
        this.teamId = teamId;
        this.fileName = fileName;
        this.uploadDate = LocalDateTime.now();
    }

    public Document(int id, String fileName, LocalDateTime uploadDate, int teamId) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.teamId = teamId;
    }

    // Getters and Setters
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", teamId=" + teamId +
                ", fileName='" + fileName + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}