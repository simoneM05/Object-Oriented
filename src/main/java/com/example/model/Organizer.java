package com.example.model;

public class Organizer extends User {

    private String hackatonTitle; // hackaton a cui fa parte
    private boolean registrationStatus; // attributo per capire se le registrazione sono aperte o meno
    public Organizer(String firstName, String lastName, String email, Hackathon hackathon, String password,
                     String username, boolean registrationStatus) /* throws CredenzialiNonValide */ {
        super(firstName, lastName, email, password, username);
        this.hackatonTitle = hackathon.getTitle();
        this.registrationStatus = registrationStatus;
    }

    public void sendMailInvite(User user) {
        // todo: puo essere fatto solo a chi fa parte di quel hackaton
    }
///
//  public void chooseJugde(//la mia intenzione è quella di creare un arraylist di persone, su cui fare un foreach usando instanceof)
//{
//
//  }

    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public boolean isRegistrationAvaible(boolean registrationStatus) {
        if (registrationStatus == true) {
            System.out.println("Registration is available.");
            return true;
        }
        else {
            System.out.println("Registration is not available.");
            return false;
        }
    }

    //TODO = L'organizzatore deve aprire le registrazioni, in questa funzione richiamerà setRegistrationStatus

}