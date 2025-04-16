package com.example.model;

public class Team {

    private String id;
    private String name;

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void saveTeam() {
        // save team in database
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addMember(Participant participant) {
        // impostas il team id del partecipante
    }

    public String getId() {
        return id;
    }

}