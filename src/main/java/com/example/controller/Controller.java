package com.example.controller;

import com.example.gui.Home; // Importa Home per accedere ai suoi metodi
import com.example.model.*;
import com.example.dao.UserDAO;
import com.example.daoImplementation.UserDAOImp;
import com.example.gui.CustomMessageDialog; // Importa la tua nuova classe di dialogo personalizzata

import java.sql.SQLException;

import javax.swing.JOptionPane; // Mantenuto solo per i tipi di messaggio (es. INFORMATION_MESSAGE)

public class Controller {
    private Home home; // Riferimento al frame Home

    public Controller(Home home) {
        this.home = home;
    }

    /**
     * Gestisce il tentativo di login.
     * In un'applicazione reale, interagirebbe con un servizio backend.
     * * @param username Il nome utente inserito.
     * 
     * @param password La password inserita.
     */
    public void handleLogin(String email, String password) {
        // --- Placeholder per la logica di login effettiva ---
        System.out.println("Tentativo di Login:");
        System.out.println("   Nome Utente: " + email);
        System.out.println("   Password: " + password);

        UserDAOImp userDAO = new UserDAOImp();
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                CustomMessageDialog.showMessage(home.getMainPanel(), "Login avvenuto con successo!", "Successo",
                        JOptionPane.INFORMATION_MESSAGE);
                home.showPanel(Home.HOME_PANEL); // Mostra la homepage dopo il login
            } else {
                CustomMessageDialog.showMessage(home.getMainPanel(), "Nome utente o password errati.",
                        "Errore di Login",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(), "Errore durante il login: ",
                    "Errore di Login", JOptionPane.ERROR_MESSAGE);
            System.out.println("Errore durante il login: " + e.getMessage());
        } catch (Exception e) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Errore sconosciuto durante il login: ",
                    "Errore di Login", JOptionPane.ERROR_MESSAGE);
            System.out.println("Errore durante il login: " + e.getMessage());

        }

    }

    /**
     * Gestisce il tentativo di registrazione.
     * In un'applicazione reale, interagirebbe con un servizio backend.
     * * @param firstName Il nome inserito.
     * 
     * @param lastName        Il cognome inserito.
     * @param email           L'email inserita.
     * @param username        Il nome utente inserito.
     * @param password        La password inserita.
     * @param confirmPassword La password confermata inserita.
     */
    public void handleRegistration(String firstName, String lastName, String email, String username, String password,
            String confirmPassword) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(), "Tutti i campi sono obbligatori.",
                    "Errore di Registrazione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            CustomMessageDialog.showMessage(home.getMainPanel(), "Le password non corrispondono.",
                    "Errore di Registrazione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // In una vera applicazione, qui faresti:
        // 1. Validazione più robusta dell'email, della lunghezza della password, ecc.
        // 2. Controllo se l'username o l'email esistono già nel database.
        // 3. Hashing della password prima di salvarla.
        // 4. Salvare il nuovo utente nel database.

        // Simula la creazione dell'oggetto User
        User newUser = new User(firstName, lastName, email, username, password);
        UserDAOImp userDAO = new UserDAOImp();
        try {
            userDAO.addUser(newUser);
        } catch (SQLException e) {
            CustomMessageDialog.showMessage(home.getMainPanel(), "Errore durante la registrazione: " + e.getMessage(),
                    "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
            return;
        }
        CustomMessageDialog.showMessage(home.getMainPanel(), "Registrazione avvenuta con successo! Ora puoi accedere.",
                "Successo", JOptionPane.INFORMATION_MESSAGE);
        showLoginPanel(); // Torna al login dopo la registrazione avvenuta con successo
    }

    /**
     * Passa la vista al pannello di Login.
     */
    public void showLoginPanel() {
        home.showPanel(Home.LOGIN_PANEL);
    }

    /**
     * Passa la vista al pannello di Registrazione.
     */
    public void showRegistrationPanel() {
        home.showPanel(Home.REGISTRATION_PANEL);
    }

    /**
     * Gestisce l'azione di logout, tornando al pannello di login.
     */
    public void handleLogout() {
        CustomMessageDialog.showMessage(home.getMainPanel(), "Disconnessione avvenuta con successo.", "Logout",
                JOptionPane.INFORMATION_MESSAGE);
        showLoginPanel(); // Torna al pannello di login
    }

    /**
     * Gestisce l'invio di un invito a un utente fittizio.
     * In un'applicazione reale, questo coinvolgerebbe la logica backend.
     * * @param recipientUsername Il nome utente a cui inviare l'invito.
     */
    public void handleSendInvitation(String recipientUsername) {
        if (recipientUsername.trim().isEmpty()) {
            CustomMessageDialog.showMessage(home.getMainPanel(),
                    "Il nome utente del destinatario non può essere vuoto.", "Errore Invito",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Simula l'invio dell'invito
        System.out.println("Invito inviato a: " + recipientUsername);
        CustomMessageDialog.showMessage(home.getMainPanel(), "Invito inviato con successo a " + recipientUsername + "!",
                "Invito Inviato", JOptionPane.INFORMATION_MESSAGE);
        Request request = new Request();

    }

    /**
     * Gestisce l'iscrizione a un hackathon.
     * In un'applicazione reale, questo coinvolgerebbe la logica backend e
     * l'aggiornamento dei dati dell'utente.
     * * @param hackathonName Il nome dell'hackathon a cui iscriversi.
     */
    public void handleSubscribeHackathon(String hackathonName) {
        System.out.println("Tentativo di iscrizione a: " + hackathonName);
        CustomMessageDialog.showMessage(home.getMainPanel(),
                "Iscrizione a '" + hackathonName + "' avvenuta con successo!", "Iscrizione Hackathon",
                JOptionPane.INFORMATION_MESSAGE);
        // In un'app reale, qui aggiorneresti un database o una lista di hackathon
        // iscritti per l'utente corrente.
        // Potresti anche ricaricare la sezione "Hackathon Iscritto" per mostrare il
        // nuovo hackathon.
    }
}