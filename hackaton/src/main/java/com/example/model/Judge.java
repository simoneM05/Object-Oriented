package com.example.model;

public class Judge extends User {

    public Judge(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, lastName);
    }

    public void giveProblem(String problem) {

    }

    public void giveVote(int vote) {
    }
}
