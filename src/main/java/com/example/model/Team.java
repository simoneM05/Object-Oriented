package com.example.model;

public class Team {

    private int id;
    private String name;

    //un team ha un documento, quindi devo necessariamente avere un'istanza di Document in Team
    private Document document;

    //da come abbiamo realizzato l'UML, un team fa parte di un Hackathon, quindi aggiungo il riferimento di Hackathon
    private Hackathon hackathon;

    //potrebbe aver senso avere in Team un istanza di voto, per recuperare facilmente l'informazione
    private Vote vote;

    public Team(int id, String name , Document document, Hackathon hackathon, Vote vote) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.hackathon = hackathon;
        this.vote = vote;
    }
    public Team(){

    }

    public void saveTeam() {
        // save team in database
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addMember(Participant participant) {
        // impostas il team id del partecipante
    }


}