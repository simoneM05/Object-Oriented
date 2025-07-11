package com.example.model;

public class Organizer extends User {

    private String HackathonID;

    public Organizer(String email, String username, String password, String first_name, String last_name,
            String HackathonID) {
        super(email, username, password, first_name, last_name);
        this.HackathonID = HackathonID;
    }

    public String getHackathonID() {
        return HackathonID;
    }

    public void setHackathonID(String hackathonID) {
        HackathonID = hackathonID;
    }

}