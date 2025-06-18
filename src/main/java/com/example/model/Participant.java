// Object-Oriented/src/main/java/com/example/model/Participant.java
package com.example.model;

public class Participant extends User { // Estende User

    private int hackathonId; // ID dell'hackathon a cui partecipa
    private Integer teamId;  // ID del team a cui appartiene (Integer per essere nullable)

    // Costruttore completo, inclusi i campi specifici del partecipante
    public Participant(int userId, String firstName, String lastName, String email, String username, String password,
                       int hackathonId, Integer teamId) {
        super(userId, firstName, lastName, email, username, password);
        this.hackathonId = hackathonId;
        this.teamId = teamId;
    }

    // Costruttore senza userId (per nuovi partecipanti)
    public Participant(String firstName, String lastName, String email, String username, String password,
                       int hackathonId, Integer teamId) {
        super(firstName, lastName, email, username, password);
        this.hackathonId = hackathonId;
        this.teamId = teamId;
    }

    // Costruttore vuoto
    public Participant() {
        super();
    }

    // --- Getter e Setter ---
    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    public Integer getTeamId() { // Usa Integer per permettere valore null
        return teamId;
    }

    public void setTeamId(Integer teamId) { // Usa Integer per permettere valore null
        this.teamId = teamId;
    }

    // Metodi specifici del Partecipante
    public void sendRequest(Request request) {
        // Logica per inviare una richiesta, probabilmente tramite il Controller e un RequestDAO
        // "Ogni partecipante può contattare un altro utente iscritto e chiedere di unirsi al team di cui fa parte, fornendo un messaggio motivazionale"
    }

    public void handleRequest(Request request, boolean accept) {
        // Logica per accettare o rifiutare una richiesta, aggiornando lo stato e potenzialmente il teamId del partecipante.
        // "l'utente può accettare o rifiutare la richiesta."
    }
}