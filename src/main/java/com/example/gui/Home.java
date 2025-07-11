package com.example.gui;

import com.example.controller.ControllerGui;
import com.example.model.Role;
import com.example.model.User;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User loggedUser;

    // Pannelli come campi della classe
    private ParticipantDashboard participantDashboard;
    private OrganizerDashboard organizerDashboard;
    private JudgeDashboard judgeDashboard;
    private UserDashboard userDashboard;

    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String REGISTRATION_PANEL = "RegistrationPanel";
    public static final String PARTICIPANT_DASHBOARD = "ParticipantDashboard";
    public static final String ORGANIZER_DASHBOARD = "OrganizerDashboard";
    public static final String JUDGE_DASHBOARD = "JudgeDashboard";
    public static final String USER_DASHBOARD = "UserDashboard";

    private ControllerGui controller;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Home homeInstance = new Home();
            homeInstance.setVisible(true);
        });
    }

    public Home() {
        setTitle("Hackathon App");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controller = new ControllerGui();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Creo e aggiungo i pannelli
        LoginPage loginPage = new LoginPage(mainPanel, this, controller);
        mainPanel.add(loginPage, LOGIN_PANEL);

        RegistrationPage registrationPage = new RegistrationPage(mainPanel);
        mainPanel.add(registrationPage, REGISTRATION_PANEL);

        userDashboard = new UserDashboard();
        mainPanel.add(userDashboard, USER_DASHBOARD);

        participantDashboard = new ParticipantDashboard();
        mainPanel.add(participantDashboard, PARTICIPANT_DASHBOARD);

        organizerDashboard = new OrganizerDashboard();
        mainPanel.add(organizerDashboard, ORGANIZER_DASHBOARD);

        judgeDashboard = new JudgeDashboard();
        mainPanel.add(judgeDashboard, JUDGE_DASHBOARD);

        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    // Salva l’utente loggato
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    // Cambia dashboard e aggiorna la UI passando i dati dell’utente loggato
    public void showDashboard(Role role) {
        switch (role) {
            case ORGANIZER -> {
                cardLayout.show(mainPanel, ORGANIZER_DASHBOARD);
                organizerDashboard.loadUserData(loggedUser);
            }
            case JUDGE -> {
                cardLayout.show(mainPanel, JUDGE_DASHBOARD);
                judgeDashboard.loadUserData(loggedUser);
            }
            case PARTICIPANT -> {
                cardLayout.show(mainPanel, PARTICIPANT_DASHBOARD);
                participantDashboard.loadUserData(loggedUser);
            }
            case USER -> {
                cardLayout.show(mainPanel, USER_DASHBOARD);
                userDashboard.loadUserData(loggedUser);
            }

            default -> cardLayout.show(mainPanel, LOGIN_PANEL);
        }
    }

}
