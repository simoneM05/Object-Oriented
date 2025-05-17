package com.example.gui;

import com.example.controller.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private Controller controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().getMainPanel());
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.setSize(800, 600);
        frameHome.setLocationRelativeTo(null);
        frameHome.setVisible(true);
    }

    public Home() {
        controller = new Controller(frameHome); // handle events
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0x121212)); // grigio scuro meno nero
        mainPanel.add(controller.createHeaderPanel(), BorderLayout.NORTH);

        // margin
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Unico pannello blu arrotondato che contiene entrambi i bottoni
        JPanel buttonContainer = new RoundedPanel(20, new Color(0, 102, 153)); // blu hackaton
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setMaximumSize(new Dimension(350, 180)); // più grande per contenere bottoni + spazio

        // Bottone Login in RoundedPanel
        RoundedPanel loginRounded = new RoundedPanel(20, new Color(0x00B2FF));
        loginRounded.setMaximumSize(new Dimension(280, 50));
        loginRounded.setLayout(new GridBagLayout()); // per centrare bottone

        JButton loginButton = controller.buttonChangeFrame("Login", new Login().getPanel());
        loginButton.setBackground(new Color(0x00B2FF));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(260, 40));

        loginRounded.add(loginButton);

        // Bottone Sign Up in RoundedPanel
        RoundedPanel signUpRounded = new RoundedPanel(20, new Color(0x00B2FF));
        signUpRounded.setMaximumSize(new Dimension(280, 50));
        signUpRounded.setLayout(new GridBagLayout());

        JButton signUpButton = controller.buttonChangeFrame("Sign Up", new Singup().getPanel());
        signUpButton.setBackground(new Color(0x00B2FF));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setPreferredSize(new Dimension(260, 40));

        signUpRounded.add(signUpButton);

        // Aggiungi bottoni e spazio verticale tra loro
        buttonContainer.add(Box.createVerticalStrut(20));
        buttonContainer.add(loginRounded);
        buttonContainer.add(Box.createVerticalStrut(25)); // più spazio
        buttonContainer.add(signUpRounded);
        buttonContainer.add(Box.createVerticalStrut(20));

        // Aggiungi al pannello principale
        mainPanel.add(buttonContainer);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
