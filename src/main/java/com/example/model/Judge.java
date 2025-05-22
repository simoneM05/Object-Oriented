package com.example.model;

public class Judge extends User {

    private String hackatonTitle; // hackaton a cui fa parte

    public Judge(String firstName, String lastName, String email, String password, Hackathon hackathon){
        super(firstName, lastName, email, password, lastName);
        this.hackatonTitle = hackathon.getTitle();
    }

    public void giveProblem(String problem) {
        // todo: fare in modo che vada inserito il problema solo al hackaton a cui fa
        // riferimeento il giudice
    }

    public void giveVote(int vote) {
    }
}
