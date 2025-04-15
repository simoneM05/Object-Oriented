package com.example.model;

public class Participant extends User {

    String teamId;

    public Participant(String firstName, String lastName, String email, String password, String username,
            String teamId) {
        super(firstName, lastName, email, password, username);
        this.teamId = teamId;
    }

    public void inviteUser(User user) {
    }

    public void acceptInvite() {
    }

    public void rejectInvite() {
    }

}