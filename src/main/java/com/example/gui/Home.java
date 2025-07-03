package com.example.gui;

import com.example.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private Controller controller;

    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String REGISTRATION_PANEL = "RegistrationPanel";
    public static final String HOME_PANEL = "HomePage";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frameHome = new JFrame("Applicazione Utenti");
            Home homeInstance = new Home();
            frameHome.setContentPane(homeInstance.getMainPanel());
            frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameHome.setSize(900, 800);
            frameHome.setLocationRelativeTo(null);
            frameHome.setVisible(true);


            ((CardLayout) homeInstance.mainPanel.getLayout()).show(homeInstance.mainPanel, LOGIN_PANEL);
        });
    }

    public Home() {
        controller = new Controller(this);
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new CardLayout());
        // Crea istanze dei tuoi pannelli
        LoginPanel loginPanel = new LoginPanel(controller);
        RegistrationPanel registrationPanel = new RegistrationPanel(controller);
        HomePage homePage = new HomePage(controller);


        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registrationPanel, REGISTRATION_PANEL);
        mainPanel.add(homePage, HOME_PANEL);
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
