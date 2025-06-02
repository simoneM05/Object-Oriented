package com.example.model;

public class Request {
    private boolean status = false; // true=accepted✅, false=rejected❌
    private String message;
    private int teamId; // utente che manda la richiesta
    private String emailRecive;// email del utente che riceve la richiesta
    private int requestId;

    public Request(String message, int teamId, String emailRecive, int requestId, boolean status) { // teamId = utente che manda la richiesta
        this.message = message; // message del utente
        this.teamId = teamId; // teamId del partecipante che manda la richiesta
        this.emailRecive = emailRecive;
        // email di chi riceve la richiesta
        this.requestId = requestId;
        this.status = false;
    }



    public boolean isStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public int getTeamId() {
        return teamId;
    }
    public String getEmailRecive() {
        return emailRecive;
    }
    public void setStatus(boolean status) {

        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setEmailRecive(String emailRecive) {
        this.emailRecive = emailRecive;
    }

}