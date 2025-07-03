package com.example.controller;

import com.example.dao.*;
import com.example.daoImplementation.*;
import com.example.gui.CustomMessageDialog;
import com.example.gui.Home;
import com.example.model.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Controller {
    private Home home;

    // DAO instances
    private UserDAO userDAO;
    private HackathonDAO hackathonDAO;
    private TeamDAO teamDAO;
    private RequestDAO requestDAO;
    private ParticipantDAO participantDAO;

    // Current logged user
    private User currentUser;

    public Controller(Home home) {
        this.home = home;
        initializeDAOs();
    }

    private void initializeDAOs() {
        this.userDAO = new UserDAOImp();
        this.hackathonDAO = new HackathonDAOImp();
        this.teamDAO = new TeamDAOimp();
        this.requestDAO = new RequestDAOImp();
        this.participantDAO = new ParticipantDAOImp();
    }

    // ========== AUTHENTICATION METHODS ==========

    public void handleLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Email e password sono obbligatori.", "Errore di Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = userDAO.getUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                this.currentUser = user;
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Login avvenuto con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                home.showPanel(Home.HOME_PANEL);
            } else {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Email o password non validi.", "Errore di Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Connection refused") ||
                    e.getMessage().contains("Communications link failure") ||
                    e.getMessage().contains("No suitable driver") ||
                    e.getMessage().toLowerCase().contains("database")) {

                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Errore di connessione al database. Verificare che il database sia attivo.",
                        "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
                System.err.println("Database non disponibile: " + e.getMessage());
            } else {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Errore durante il login: " + e.getMessage(),
                        "Errore di Login", JOptionPane.ERROR_MESSAGE);
                System.err.println("Errore durante il login: " + e.getMessage());
            }
        } catch (Exception e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore imprevisto. Verificare la connessione al database.",
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore imprevisto durante il login: " + e.getMessage());
        }
    }

    public void handleRegistration(String firstName, String lastName, String email, String username, String password,
                                   String confirmPassword) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Tutti i campi sono obbligatori.", "Errore di Registrazione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Le password non corrispondono.", "Errore di Registrazione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Formato email non valido.", "Errore di Registrazione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User existingUser = userDAO.getUserByEmail(email);
            if (existingUser != null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Un utente con questa email esiste già.", "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(firstName, lastName, email, username, password);
            userDAO.addUser(newUser);

            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Registrazione avvenuta con successo! Ora puoi accedere.",
                    "Successo", JOptionPane.INFORMATION_MESSAGE);
            showLoginPanel();

        } catch (SQLException e) {
            if (e.getMessage().contains("Connection refused") ||
                    e.getMessage().contains("Communications link failure") ||
                    e.getMessage().contains("No suitable driver") ||
                    e.getMessage().toLowerCase().contains("database")) {

                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Errore di connessione al database. Verificare che il database sia attivo.",
                        "Errore di Connessione", JOptionPane.ERROR_MESSAGE);
                System.err.println("Database non disponibile: " + e.getMessage());
            } else {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Errore durante la registrazione: " + e.getMessage(),
                        "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
                System.err.println("Errore durante la registrazione: " + e.getMessage());
            }
        } catch (Exception e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore imprevisto. Verificare la connessione al database.",
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore imprevisto durante la registrazione: " + e.getMessage());
        }
    }

    public void handleLogout() {
        this.currentUser = null;
        CustomMessageDialog.showMessage(home.getMainPanel(),
                "Logout effettuato con successo!", "Logout", JOptionPane.INFORMATION_MESSAGE);
        home.showPanel(Home.LOGIN_PANEL);
    }

    // ========== NAVIGATION METHODS ==========

    public void showLoginPanel() {
        home.showPanel(Home.LOGIN_PANEL);
    }

    public void showRegistrationPanel() {
        home.showPanel(Home.REGISTRATION_PANEL);
    }

    public void showHomePanel() {
        home.showPanel(Home.HOME_PANEL);
    }

    // ========== GETTER METHODS ==========

    public User getCurrentUser() {
        return currentUser;
    }

    // ========== HACKATHON METHODS ==========

    public void handleCreateHackathon(String title, String location, LocalDateTime startDate, LocalDateTime endDate,
                                      int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
                                      LocalDateTime registrationEnd, String problemDescription) {
        if (currentUser == null) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Devi essere loggato per creare un hackathon.", "Errore Creazione Hackathon",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (title.trim().isEmpty() || location.trim().isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Titolo e location sono obbligatori.", "Errore Creazione Hackathon",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (startDate.isAfter(endDate)) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "La data di inizio deve essere precedente alla data di fine.", "Errore Creazione Hackathon",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (registrationEnd.isAfter(startDate.minusDays(2))) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Le registrazioni devono chiudersi almeno 2 giorni prima dell'evento.", "Errore Creazione Hackathon",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Hackathon newHackathon = new Hackathon(title, location, startDate, endDate, maxParticipants,
                    maxTeamSize, registrationStart, registrationEnd,
                    problemDescription, currentUser.getEmail());

            hackathonDAO.addHackathon(newHackathon);

            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Hackathon '" + title + "' creato con successo!", "Hackathon Creato",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore durante la creazione dell'hackathon: " + e.getMessage(), "Errore Creazione Hackathon",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore durante la creazione dell'hackathon: " + e.getMessage());
        }
    }

    public void handleRegisterToHackathon(int hackathonId) {
        if (currentUser == null) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Devi essere loggato per registrarti a un hackathon.", "Errore Registrazione",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Verifica se l'utente è già registrato a qualsiasi hackathon
            if (isUserRegisteredToAnyHackathon()) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Sei già registrato a un hackathon. Non puoi partecipare a più hackathon contemporaneamente.",
                        "Registrazione Non Consentita", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Trova l'hackathon
            Hackathon hackathon = hackathonDAO.getHackathonById(hackathonId);
            if (hackathon == null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Hackathon non trovato.", "Errore Registrazione",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se le registrazioni sono aperte
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(hackathon.getRegistrationStart()) || now.isAfter(hackathon.getRegistrationEnd())) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Le registrazioni per questo hackathon non sono attualmente aperte.",
                        "Registrazione Non Disponibile", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica se ci sono posti disponibili
            List<Participant> currentParticipants = participantDAO.getParticipantsByHackathonId(hackathonId);
            if (currentParticipants.size() >= hackathon.getMaxParticipants()) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Questo hackathon ha raggiunto il numero massimo di partecipanti.",
                        "Hackathon Completo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crea il partecipante - CORRETTO: usa il costruttore del modello Participant
            Participant newParticipant = new Participant(currentUser.getFirstName(), currentUser.getLastName(),
                    currentUser.getEmail(), currentUser.getUsername(), currentUser.getPassword(),
                    hackathonId, null);
            participantDAO.addParticipant(newParticipant);

            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Ti sei registrato con successo all'hackathon '" + hackathon.getTitle() + "'!",
                    "Registrazione Completata", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore durante la registrazione: " + e.getMessage(),
                    "Errore Registrazione", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore durante la registrazione all'hackathon: " + e.getMessage());
        }
    }

    public List<Hackathon> getAllHackathons() {
        try {
            return hackathonDAO.getAllHackathons();
        } catch (SQLException e) {
            System.err.println("Errore nel recupero degli hackathon: " + e.getMessage());
            return null;
        }
    }

    public boolean isUserRegisteredToHackathon(int hackathonId) {
        if (currentUser == null) return false;

        try {
            List<Participant> participants = participantDAO.getParticipantsByHackathonId(hackathonId);
            for (Participant p : participants) {
                if (p.getEmail().equals(currentUser.getEmail())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nella verifica di registrazione: " + e.getMessage());
        }

        return false;
    }

    public boolean isUserRegisteredToAnyHackathon() {
        if (currentUser == null) return false;

        try {
            List<Participant> participants = participantDAO.getAllParticipants();
            for (Participant p : participants) {
                if (p.getEmail().equals(currentUser.getEmail())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nella verifica di registrazione: " + e.getMessage());
        }

        return false;
    }

    public Hackathon getUserRegisteredHackathon() {
        if (currentUser == null) return null;

        try {
            List<Participant> participants = participantDAO.getAllParticipants();
            for (Participant p : participants) {
                if (p.getEmail().equals(currentUser.getEmail())) {
                    return hackathonDAO.getHackathonById(p.getHackathonId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dell'hackathon dell'utente: " + e.getMessage());
        }

        return null;
    }

    // ========== TEAM METHODS ==========

    public void handleCreateTeam(String teamName, int hackathonId, int maxMembers) {
        if (currentUser == null) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Devi essere loggato per creare un team.", "Errore Creazione Team",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (teamName.trim().isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Il nome del team è obbligatorio.", "Errore Creazione Team",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Verifica se l'utente è registrato a questo hackathon
            if (!isUserRegisteredToHackathon(hackathonId)) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Devi essere registrato a questo hackathon per creare un team.",
                        "Errore Creazione Team", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se l'utente è già in un team per questo hackathon
            if (getCurrentUserTeamForHackathon(hackathonId) != null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Sei già membro di un team per questo hackathon.",
                        "Errore Creazione Team", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica che il numero massimo di membri sia valido
            Hackathon hackathon = hackathonDAO.getHackathonById(hackathonId);
            if (maxMembers > hackathon.getMaxTeamSize()) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Il numero massimo di membri non può superare " + hackathon.getMaxTeamSize() + ".",
                        "Errore Creazione Team", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crea il team - CORRETTO: usa il costruttore del modello Team
            Team newTeam = new Team(teamName, hackathonId, maxMembers, currentUser.getEmail());
            teamDAO.addTeam(newTeam);

            // Recupera il team appena creato per ottenere l'ID
            List<Team> teams = teamDAO.getTeamsByHackathonId(hackathonId);
            Team createdTeam = null;
            for (Team team : teams) {
                if (team.getTeamName().equals(teamName)) {
                    createdTeam = team;
                    break;
                }
            }

            if (createdTeam != null) {
                // Aggiorna il partecipante con l'ID del team
                participantDAO.updateParticipantTeam(currentUser.getEmail(), hackathonId, createdTeam.getId());

                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Team '" + teamName + "' creato con successo!",
                        "Team Creato", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Errore nel recupero del team creato.",
                        "Errore Creazione Team", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore durante la creazione del team: " + e.getMessage(),
                    "Errore Creazione Team", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore durante la creazione del team: " + e.getMessage());
        }
    }

    public List<Team> getTeamsByHackathon(int hackathonId) {
        try {
            return teamDAO.getTeamsByHackathonId(hackathonId);
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dei team: " + e.getMessage());
            return null;
        }
    }

    public Team getCurrentUserTeamForHackathon(int hackathonId) {
        if (currentUser == null) return null;

        try {
            List<Participant> participants = participantDAO.getParticipantsByHackathonId(hackathonId);
            for (Participant p : participants) {
                if (p.getEmail().equals(currentUser.getEmail()) && p.getTeamId() != null) {
                    return teamDAO.getTeamById(p.getTeamId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero del team dell'utente: " + e.getMessage());
        }

        return null;
    }

    public List<Participant> getTeamMembers(int teamId) {
        try {
            return participantDAO.getParticipantsByTeamId(teamId);
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dei membri del team: " + e.getMessage());
            return null;
        }
    }

    // ========== REQUEST METHODS ==========

    public void handleSendTeamInvitation(String targetEmail, int teamId, String message) {
        if (currentUser == null) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Devi essere loggato per inviare inviti.", "Errore Invito",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (targetEmail.trim().isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "L'email del destinatario è obbligatoria.", "Errore Invito",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Verifica che l'utente target esista
            User targetUser = userDAO.getUserByEmail(targetEmail);
            if (targetUser == null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Utente con email '" + targetEmail + "' non trovato.",
                        "Errore Invito", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che il team esista
            Team team = teamDAO.getTeamById(teamId);
            if (team == null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Team non trovato.", "Errore Invito",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che l'utente target sia registrato allo stesso hackathon
            if (!isUserRegisteredToHackathon(team.getHackathonId())) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "L'utente deve essere registrato allo stesso hackathon.",
                        "Errore Invito", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che il team non sia già pieno
            List<Participant> currentMembers = participantDAO.getParticipantsByTeamId(teamId);
            if (currentMembers.size() >= team.getMaxMembers()) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Il team ha raggiunto il numero massimo di membri.",
                        "Team Completo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica che l'utente target non sia già in un team
            Team targetUserTeam = getCurrentUserTeamForHackathon(team.getHackathonId());
            if (targetUserTeam != null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "L'utente è già membro di un altro team per questo hackathon.",
                        "Errore Invito", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crea la richiesta - CORRETTO: usa il costruttore del modello Request
            String finalMessage = message.trim().isEmpty() ?
                    "Invito a unirsi al team '" + team.getTeamName() + "'" : message;

            Request request = new Request(finalMessage, teamId, currentUser.getEmail(), targetEmail);
            requestDAO.addRequest(request);

            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Invito inviato con successo a " + targetEmail + "!",
                    "Invito Inviato", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore durante l'invio dell'invito: " + e.getMessage(),
                    "Errore Invito", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore durante l'invio dell'invito: " + e.getMessage());
        }
    }

    public void handleRequestResponse(int requestId, boolean accept) {
        if (currentUser == null) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Devi essere loggato per rispondere alle richieste.", "Errore Risposta",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Request request = requestDAO.getRequestById(requestId);
            if (request == null) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Richiesta non trovata.", "Errore Risposta",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che la richiesta sia destinata all'utente corrente
            if (!request.getReceiverUserEmail().equals(currentUser.getEmail())) {
                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Non sei autorizzato a rispondere a questa richiesta.",
                        "Errore Risposta", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accept) {
                // Accetta la richiesta
                Team team = teamDAO.getTeamById(request.getTeamId());
                if (team == null) {
                    CustomMessageDialog.showMessage(home.getMainPanel(),
                            "Team non trovato.", "Errore Risposta",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verifica che il team non sia pieno
                List<Participant> currentMembers = participantDAO.getParticipantsByTeamId(team.getId());
                if (currentMembers.size() >= team.getMaxMembers()) {
                    CustomMessageDialog.showMessage(home.getMainPanel(),
                            "Il team è già completo.", "Team Completo",
                            JOptionPane.WARNING_MESSAGE);
                    requestDAO.updateRequestStatus(requestId, "REJECTED");
                    return;
                }

                // Aggiorna il partecipante con l'ID del team
                participantDAO.updateParticipantTeam(currentUser.getEmail(), team.getHackathonId(), team.getId());
                requestDAO.updateRequestStatus(requestId, "ACCEPTED");

                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Ti sei unito con successo al team '" + team.getTeamName() + "'!",
                        "Richiesta Accettata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Rifiuta la richiesta
                requestDAO.updateRequestStatus(requestId, "REJECTED");

                CustomMessageDialog.showMessage(home.getMainPanel(),
                        "Richiesta rifiutata.", "Richiesta Rifiutata",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore durante la risposta alla richiesta: " + e.getMessage(),
                    "Errore Risposta", JOptionPane.ERROR_MESSAGE);
            System.err.println("Errore durante la risposta alla richiesta: " + e.getMessage());
        }
    }

    public List<Request> getCurrentUserRequests() {
        if (currentUser == null) return null;

        try {
            return requestDAO.getRequestsByReceiverEmail(currentUser.getEmail());
        } catch (SQLException e) {
            System.err.println("Errore nel recupero delle richieste: " + e.getMessage());
            return null;
        }
    }

    // ========== UTILITY METHODS ==========

    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            System.err.println("Errore nel recupero degli utenti: " + e.getMessage());
            return null;
        }
    }

    public List<User> getUsersByHackathon(int hackathonId) {
        try {
            List<Participant> participants = participantDAO.getParticipantsByHackathonId(hackathonId);
            List<User> users = new java.util.ArrayList<>();

            for (Participant p : participants) {
                User user = userDAO.getUserByEmail(p.getEmail());
                if (user != null) {
                    users.add(user);
                }
            }

            return users;
        } catch (SQLException e) {
            System.err.println("Errore nel recupero degli utenti per hackathon: " + e.getMessage());
            return null;
        }
    }
}