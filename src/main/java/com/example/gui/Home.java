package com.example.gui;

import com.example.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Home {
    private JPanel mainPanel; // Questo pannello conterrà il CardLayout
    private static JFrame frameHome;
    private Controller controller;

    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String REGISTRATION_PANEL = "RegistrationPanel";
    public static final String HOME_PANEL = "HomePage"; // QUESTA È LA COSTANTE CHE IL CONTROLLER CERCA

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frameHome = new JFrame("Applicazione Utenti");
            Home homeInstance = new Home();
            frameHome.setContentPane(homeInstance.getMainPanel());
            frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameHome.setSize(900, 800); // Aumentato leggermente le dimensioni per la homepage
            frameHome.setLocationRelativeTo(null); // Centra la finestra
            frameHome.setVisible(true);

            // Inizialmente mostra il pannello di login
            ((CardLayout) homeInstance.mainPanel.getLayout()).show(homeInstance.mainPanel, LOGIN_PANEL);
        });
    }

    public Home() {
        controller = new Controller(this); // Passa l'istanza di Home al controller
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new CardLayout()); // Usa CardLayout per cambiare pannelli

        // Crea istanze dei tuoi pannelli
        LoginPanel loginPanel = new LoginPanel(controller);
        RegistrationPanel registrationPanel = new RegistrationPanel(controller);
        HomePage homePage = new HomePage(controller); // Nuova istanza della homepage

        // Aggiungi i pannelli al mainPanel con nomi unici per CardLayout
        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registrationPanel, REGISTRATION_PANEL);
        mainPanel.add(homePage, HOME_PANEL); // Aggiungi la homepage
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Metodo per passare tra i diversi pannelli usando CardLayout.
     * 
     * @param panelName Il nome del pannello da mostrare (es. LOGIN_PANEL,
     *                  REGISTRATION_PANEL, HOME_PANEL).
     */
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, panelName);
    }
}
