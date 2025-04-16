package com.example.model;

import java.time.LocalDate;

public class Document {
    private LocalDate date;
    private String document;

    public Document(LocalDate date, String document) {
        this.date = date;
        this.document = document;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDocument() {
        return document;
    }

}