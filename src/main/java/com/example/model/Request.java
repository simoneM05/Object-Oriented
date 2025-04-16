package com.example.model;

public class Request {
    // ? private Boolean status; // true=accepted✅, false=rejected❌
    private String message;

    public Request(String message) {
        this.message = message;
    }

    // public void SelectStatus() {
    // // potrebbe impostare nel database lo stato della richiesta
    // }

}