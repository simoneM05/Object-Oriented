package com.example.model;
import java.util.ArrayList;
import java.time.LocalDate;

public class Hackathon {

    private String sede;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private int partecipantNumber; // numero di partecipanti totali
    private int numMaxForTeam; // numero massimo per ogni team
    private LocalDate startSub; // inizio iscrizioni
    private final LocalDate endSub = startDate.minusDays(2); // fine iscrizioni 2 giorni prima del inzio
    private String problem; // descrizione problema

    //da come abbiamo fatto l'UML, l'Hackathon è formato da un insieme di Team che si sfidano
    //Al momento creun un arrayList di Team
    private ArrayList<Team> teamPartecipanti;


    //sempre per restare coerenti con l'UML e le cardinalità che abbiamo scritto, avere al momento un ArrayList di utente è comodo
    private ArrayList<User> user;

    //TODO = LEGGENDO MEGLIO LA TRACCIA, HACKATHON DEVE AVERE UN ISTANZA DI GIUDICE


    public Hackathon(String sede, LocalDate startDate, LocalDate endDate, String title, int partecipantNumber,
                     int numMaxForTeam, LocalDate startSub, LocalDate endSub, String problem) {


        this.sede = sede;
        this.startDate = startDate;
        this.endDate = endDate;
        this.partecipantNumber = partecipantNumber;
        this.numMaxForTeam = numMaxForTeam;
        this.startSub = startSub;
        this.problem = problem;
        teamPartecipanti = new ArrayList<>();
        user = new ArrayList<>();
        // if(/* INSERIRE CONDIZIONE PER IL TITOLO*/){
        // throw new TitoloNonValido("Questo Hackathon ha un titolo noon valido");
        // }
        // else{
        // this.title = title;
        // }
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPartecipantNumber() {
        return partecipantNumber;
    }

    public void setPartecipantNumber(int partecipantNumber) {
        this.partecipantNumber = partecipantNumber;
    }

    public int getNumMaxForTeam() {
        return numMaxForTeam;
    }

    public void setNumMaxForTeam(int numMaxForTeam) {
        this.numMaxForTeam = numMaxForTeam;
    }

    public LocalDate getStartSub() {
        return startSub;
    }

    public void setStartSub(LocalDate startSub) {
        this.startSub = startSub;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}