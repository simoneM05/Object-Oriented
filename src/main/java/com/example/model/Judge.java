package com.example.model;

public class Judge extends User {

    private String hackatonTitle; // used for found hackaton but in database saved with HackatonId

    public Judge(String firstName, String lastName, String email, String password, Hackaton hackaton) {
        super(firstName, lastName, email, password, lastName);
        this.hackatonTitle = hackaton.getTitle();
    }

    public void giveProblem(String problem) {
        // todo: fare in modo che vada inserito il problema solo al hackaton a cui fa
        // riferimeento il giudice
    }

    public void giveVote(int vote) {
    }
}
