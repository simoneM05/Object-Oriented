package com.example.model;

public class Partecipant extends User {

    private int HackathonID;
    private Integer TeamID;

    public Partecipant(String email, String username, String password, String first_name, String last_name,
            int HackathonID, Integer TeamID) {
        super(email, username, password, first_name, last_name);
        this.HackathonID = HackathonID;
        this.TeamID = TeamID;
    }

    public Partecipant(User user,
            int HackathonID, Integer TeamID) {
        super(user.email, user.username, user.password, user.first_name, user.last_name);
        this.HackathonID = HackathonID;
        this.TeamID = TeamID;
    }

    public Partecipant(String email, int hackathonID, Integer teamID) {
        super();
        this.email = email;
        this.HackathonID = hackathonID;
        this.TeamID = teamID;
    }

    public int getHackathonID() {
        return HackathonID;
    }

    public void setHackathonID(int hackathonID) {
        HackathonID = hackathonID;
    }

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
    }

}