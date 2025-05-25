package com.example.model;

import java.time.LocalDate;

public class Organizer extends User {


    private String hackathonTitle; // hackaton a cui fa parte
    private LocalDate registrationStatus; // attributo per capire se le registrazione sono aperte o meno impostando una data di inizio  
    private final String role = Role.Organizer.toString();

    public Organizer(String firstName, String lastName, String email, Hackathon hackathon, String password,
            String username) /* throws CredenzialiNonValide */ {
        super(firstName, lastName, email, password, username);
        this.hackathonTitle = hackathon.getTitle();
    }

    public void sendMailInvite(User user) {
        // todo: puo essere fatto solo a chi fa parte di quel hackaton (per crearea un
        // giudice)
    }

    public void setRegistrationStart(LocalDate registrationStatus) { 
        // ricerca del hackaton per il titolo e set del giorno di
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
