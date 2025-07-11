package com.example.model;

import java.time.LocalDateTime;

public class Hackathon {
    private int id;
    private String title;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxParticipants;
    private int maxTeamSize;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private String problemDescription;
    private String organizerUserEmail;

    // Costruttore completo
    public Hackathon(int id, String title, String location, LocalDateTime startDate, LocalDateTime endDate,
            int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
            String problemDescription, String organizerUserEmail) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.maxTeamSize = maxTeamSize;
        this.registrationStart = registrationStart;
        this.problemDescription = problemDescription;
        registrationEnd = registrationStart != null ? registrationStart.plusDays(3) : null; // Default 1 day after start
        this.organizerUserEmail = organizerUserEmail;
    }

    // Costruttore senza id (per oggetti nuovi da salvare)
    public Hackathon(String title, String location, LocalDateTime startDate, LocalDateTime endDate,
            int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
            String problemDescription, String organizerUserEmail) {
        this(0, title, location, startDate, endDate, maxParticipants, maxTeamSize, registrationStart,
                problemDescription, organizerUserEmail);

    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public LocalDateTime getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(LocalDateTime registrationStart) {
        this.registrationStart = registrationStart;
    }

    public LocalDateTime getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(LocalDateTime registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getOrganizerUserEmail() {
        return organizerUserEmail;
    }

    public void setOrganizerUserEmail(String organizerUserEmail) {
        this.organizerUserEmail = organizerUserEmail;
    }

    @Override
    public String toString() {
        return "Hackathon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxParticipants=" + maxParticipants +
                ", maxTeamSize=" + maxTeamSize +
                ", registrationStart=" + registrationStart +
                ", registrationEnd=" + registrationEnd +
                ", problemDescription='" + problemDescription + '\'' +
                ", organizerUserEmail='" + organizerUserEmail + '\'' +
                '}';
    }
}
