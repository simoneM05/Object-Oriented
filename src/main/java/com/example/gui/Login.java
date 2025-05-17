package com.example.gui;

import javax.swing.*;
import java.awt.*;
import com.example.controller.Controller;

public class Login {

    private JPanel loginPanel;

    public Login() {
        Controller controller = new Controller();
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setOpaque(false); // trasparente

        loginPanel.add(controller.createHeaderPanel(), BorderLayout.NORTH);

        // Center Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Pannello blu con angoli arrotondati per il form
        RoundedPanel contentPanel = new RoundedPanel(30, new Color(0, 102, 153));
        contentPanel.setPreferredSize(new Dimension(300, 300));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(loginLabel);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(controller.createTextField("Email"));
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(controller.createPasswordField("Password"));
        contentPanel.add(Box.createVerticalStrut(20));

        JButton submitButton = controller.createButton("Submit", e -> {
            System.out.println("Submit clicked");
        });

        // Rendi il bottone trasparente (niente sfondo, così si vede solo il pannello
        // arrotondato)
        submitButton.setContentAreaFilled(false);
        submitButton.setOpaque(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Crea un RoundedPanel come contenitore del bottone
        RoundedPanel roundedButtonPanel = new RoundedPanel(25, new Color(0x00B2FF)); // radius e colore a piacere
        roundedButtonPanel.setLayout(new GridBagLayout()); // per centrare il bottone dentro il pannello
        roundedButtonPanel.setMaximumSize(new Dimension(200, 40)); // dimensione pannello

        // Aggiungi il bottone al pannello arrotondato
        roundedButtonPanel.add(submitButton);

        contentPanel.add(roundedButtonPanel);

        mainPanel.add(contentPanel);
        loginPanel.add(mainPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}
