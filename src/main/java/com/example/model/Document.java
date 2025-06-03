package com.example.model;

import java.time.LocalDate;

public class Document {
    private LocalDate date;

    //l'ho chiamato cosi' giusto per differenziarlo rispetto al nome della classe
    private String documentFile;
    private int teamId;
    private int documentId;

    public Document(LocalDate date, String document, int teamId, int documentId) {
        this.date = date;
        this.documentFile = document;
        this.teamId = teamId;
        this.documentId = documentId;
    }

    public Document() {

    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDocument(String document) {
        this.documentFile = document;
    }


    public LocalDate getDate() {
        return date;
    }

    public String getDocumentFile() {
        return documentFile;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getDocumentId() {
        return documentId;

    }

}
