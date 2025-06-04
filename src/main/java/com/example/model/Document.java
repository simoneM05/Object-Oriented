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

    // il costruttore vuoto è utile per l'implementazione della DAO (qualora dovessimo fare la DAO del documento)
    public Document() {

    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDocumentFile(String documentFile) {
        this.documentFile = documentFile;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
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
