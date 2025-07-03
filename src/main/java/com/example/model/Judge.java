package com.example.model;

public class Judge extends User {

    private String hackathonTitle; // hackathon a cui fa parte

    public Judge(String firstName, String lastName, String email, String username, String password, int userId, String hackathonTitle) {
        super(userId, firstName, lastName, email, username, password);
        this.hackathonTitle = hackathonTitle;
    }

    public Judge(String firstName, String lastName, String email, String username, String password, String hackathonTitle) {
        super(firstName, lastName, email, username, password);
        this.hackathonTitle = hackathonTitle;
    }

    public Judge() {
        super();
        this.hackathonTitle = null;
    }

    public String getHackathonTitle() {
        return hackathonTitle;
    }

    public void setHackathonTitle(String hackathonTitle) {
        this.hackathonTitle = hackathonTitle;
    }

    public void giveProblem(String problem) {
        // TODO: implementare logica per inserire il problema solo all'hackathon di riferimento
        if (this.hackathonTitle == null) {
            throw new IllegalStateException("Il giudice deve essere associato a un hackathon prima di poter assegnare problemi.");
        }
        // Logica per assegnare il problema
    }

    public void giveVote(int vote) {
        // TODO: implementare logica per assegnare voto
        if (vote < 0 || vote > 10) {
            throw new IllegalArgumentException("Il voto deve essere compreso tra 0 e 10.");
        }
        // Logica per assegnare il voto
    }
}