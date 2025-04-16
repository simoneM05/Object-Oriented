package com.example.model;

public class Judge extends User {

    private String hackatonTitle; // hackaton a cui fa parte

    public Judge(String firstName, String lastName, String email, String password, Hackaton hackaton) {
        super(firstName, lastName, email, password, lastName);
        hackatonTitle = hackaton.getTitle();
    }

    public void giveProblem(String problem) {
        // todo: fare in modo che vada inerito il problema solo al hackaton a cui fa
        // riferimeento il giudice
    }

    public void giveVote(int vote) {
    }
}
