package com.example.model;

import java.time.LocalDate;

public class Organizer extends User {

    private String hackathonTitle; // hackaton a cui fa parte

    public Organizer(String firstName, String lastName, String email, Hackathon hackathon, String password,
            String username) /* throws CredenzialiNonValide */ {
        super(firstName, lastName, email, password, username);
        this.setRole(Role.Organizer.toString());
        this.hackathonTitle = hackathon.getTitle();

    }

    public void sendMailInvite(User user) {
        for (int i = 0; i < 3; i++) {
            // query
        }
    }

    public void setRegistrationStart(LocalDate registrationStatus) {
        // QUERY che trova l'hachaton a di riferimento e imposta in start sub una data
    }

    public boolean isRegistrationAvaible(boolean registrationStatus) {
        if (registrationStatus == true) {
            System.out.println("Registration is available.");
            return true;
        } else {
            System.out.println("Registration is not available.");
            return false;
        }
    }

    // TODO = L'organizzatore deve aprire le registrazioni, in questa funzione
    // richiamerà setRegistrationStatus

}