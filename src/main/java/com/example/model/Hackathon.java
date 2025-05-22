package com.example.model;

import java.time.LocalDate;

public class Hackathon {

    private String sede;
    private LocalDate startDate;
    private LocalDate enDate;
    private String title;
    private int partecipantNumber; // numero di partecipanti totali
    private int numMaxForTeam; // numero massimo per ogni team
    private LocalDate startSub; // inizio iscrizioni
    private LocalDate endSub; // fine iscrizioni
    private String problem; // descrizione problema

    //TODO = LEGGENDO MEGLIO LA TRACCIA, HACKATHON DEVE AVERE UN ISTANZA DI GIUDICE

    public Hackathon(String sede, LocalDate startDate, LocalDate enDate, String title, int partecipantNumber,
                     int numMaxForTeam, LocalDate startSub, LocalDate endSub, String problem) {

        this.sede = sede;
        this.startDate = startDate;
        this.enDate = enDate;
        this.partecipantNumber = partecipantNumber;
        this.numMaxForTeam = numMaxForTeam;
        this.startSub = startSub;
        this.endSub = endSub;
        this.problem = problem;
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

    public LocalDate getEnDate() {
        return enDate;
    }

    public void setEnDate(LocalDate enDate) {
        this.enDate = enDate;
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

    public LocalDate getEndSub() {
        return endSub;
    }

    public void setEndSub(LocalDate endSub) {
        this.endSub = endSub;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}