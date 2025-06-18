package com.example.model;

import java.time.LocalDateTime;

public class Hackathon {
    private int id;                                                  // Corrisponde a id SERIAL PRIMARY KEY nello schema SQL
    private String title;                                            // Corrisponde a title VARCHAR(255) NOT NULL UNIQUE
    private String location;                                         // Corrisponde a location VARCHAR(255) NOT NULL
    private LocalDateTime startDate;                                 // Corrisponde a start_date TIMESTAMP NOT NULL
    private LocalDateTime endDate;                                   // Corrisponde a end_date TIMESTAMP NOT NULL
    private int maxParticipants;                                     // Corrisponde a max_participants INT NOT NULL
    private int maxTeamSize;                                         // Corrisponde a max_team_size INT NOT NULL
    private LocalDateTime registrationStart;                         // Corrisponde a registration_start TIMESTAMP NOT NULL
    private LocalDateTime registrationEnd;                           // Corrisponde a registration_end TIMESTAMP NOT NULL
    private String problemDescription;                               // Corrisponde a problem_description TEXT
    private String organizerUserEmail;                               // Corrisponde a organizer_user_email VARCHAR(255) NOT NULL (FK)

    // Costruttore completo per inizializzare tutti i campi
    public Hackathon(int id, String title, String location, LocalDateTime startDate, LocalDateTime endDate,
                     int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
                     LocalDateTime registrationEnd, String problemDescription, String organizerUserEmail) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.maxTeamSize = maxTeamSize;
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
        this.problemDescription = problemDescription;
        this.organizerUserEmail = organizerUserEmail;
    }

    // Costruttore senza ID per nuovi Hackathon
    public Hackathon(String title, String location, LocalDateTime startDate, LocalDateTime endDate,
                     int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
                     LocalDateTime registrationEnd, String problemDescription, String organizerUserEmail) {
        this(0, title, location, startDate, endDate, maxParticipants, maxTeamSize,
                registrationStart, registrationEnd, problemDescription, organizerUserEmail);
    }


    public Hackathon() {
    }

    // Getter e Setter per tutti i campi

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

    // Metodo per validare il titolo (se necessario, in base alle regole di business)
    // Questo metodo gestiva l'eccezione TitoloNonValido (dal tuo codice originale)
    public void validateTitle(String title) throws TitoloNonValidoException {
        if (title == null || title.trim().isEmpty()) {
            throw new TitoloNonValidoException("Il titolo dell'Hackathon non può essere vuoto.");
        }
        // Possibili regole da aggiungere per il titolo
    }

    // È importante che la logica di business come l'apertura/chiusura delle registrazioni
    // e l'assegnazione del problema venga gestita dal Controller/Service layer,
    // interagendo con il DAO e gli oggetti Hackathon.
}