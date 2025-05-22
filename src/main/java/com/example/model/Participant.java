package com.example.model;

public class Participant extends User {

    private Team team;

    private String titleHackaton;

    public String getTeamId() {
        return team.getId();
    }

    public Participant(String firstName, String lastName, String email, String password, String username,
            Team team, Hackathon hackathon) {
        super(firstName, lastName, email, password, username);
        this.team = team;
        this.titleHackaton = hackathon.getTitle();
    }

    public Participant(User user, Team team) {
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUsername());
        this.team = team;
    }

    public void sendRequest(Participant participant, String message) {
        // todo: gestione per controllare se l'utente a cui viene mandata la richiesta
        // non fa gia parte di un team pieno e fa parte dello stesso hackaton
        Request request = new Request(message, this.team.getId(), participant.getEmail());
        // Todo: salva la richiesta nel database
    }

    public void handleRequest(Request request) {
        // Todo: deve impostare a true oppurer false e in caso venga impostato a true
        /*
         * richiamare la funzione addMember per il team di cui fa parte il ==>
         * this.team.addMember(//ricerca nel database l'utente con quella mail e
         * modifica il suo teamId con il teamId del partecipante che ha mandato la
         * richiesta);
         * partecipante
         * 
         */
    }
}

// pippo.sendRequest("ciao","pippo@gmail.com"); //* esempio di come mandare una
// richiesta