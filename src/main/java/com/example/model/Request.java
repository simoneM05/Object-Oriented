package com.example.model;

public class Request {
    private boolean status = false; // true=accepted✅, false=rejected❌
    private String message;
    private String teamId; // utente che manda la richiesta
    private String emailRecive; // email del utente che riceve la richiesta

    public Request(String message, String teamId, String emailRecive) { // teamId = utente che manda la richiesta
        this.message = message; // message del utente
        this.teamId = teamId; // teamId del partecipante che manda la richiesta
        this.emailRecive = emailRecive; // email di chi riceve la richiesta
    }
    public boolean isStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getTeamId() {
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
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setEmailRecive(String emailRecive) {
        this.emailRecive = emailRecive;
    }

}