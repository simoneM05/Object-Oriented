package com.example.model;

public class Judge extends User {

    private String hackathonTitle; // hackaton a cui fa parte

    public Judge(String firstName, String lastName, String email,String username, String password, int userId) {
        super(userId, firstName, lastName, email, username, password);
        this.hackathonTitle = hackathonTitle;
    }

    public Judge()
    {

    }

    public String getHackathonTitle() {
        return hackathonTitle;
    }

    public void setHackathonTitle(String hackathonTitle) {
        this.hackathonTitle = hackathonTitle;
    }

    public void giveProblem(String problem) {
        // todo: fare in modo che vada inserito il problema solo al hackaton a cui fa
        // riferimeento il giudice
    }

    public void giveVote(int vote) {
    }
}
