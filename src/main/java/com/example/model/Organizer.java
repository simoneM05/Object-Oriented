package com.example.model;

public class Organizer extends User {

    private String hackatonTitle; // hackaton a cui fa parte

    public Organizer(String firstName, String lastName, String email, Hackaton hackaton, String password,
            String username) {
        super(firstName, lastName, email, password, username);
        this.hackatonTitle = hackaton.getTitle();
    }

    public void sendMailInvite(User user) {
        // todo: puo essere fatto solo a chi fa parte di quel hackaton
    }

}