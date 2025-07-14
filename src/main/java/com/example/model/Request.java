package com.example.model;

public class Request {
    int id;
    String message;
    String status;
    int teamId;
    String sender_participant_email;
    String receiver_organizer_email;

    public Request(int id, String message, String status, int teamId, String sender_participant_email,
            String receiver_organizer_email) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.teamId = teamId;
        this.sender_participant_email = sender_participant_email;
        this.receiver_organizer_email = receiver_organizer_email;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getSender_participant_email() {
        return sender_participant_email;
    }

    public void setSender_participant_email(String sender_participant_email) {
        this.sender_participant_email = sender_participant_email;
    }

    public String getReceiver_organizer_email() {
        return receiver_organizer_email;
    }

    public void setReceiver_organizer_email(String receiver_organizer_email) {
        this.receiver_organizer_email = receiver_organizer_email;
    }
}