package com.example.gui;

import javax.swing.*;
import java.awt.*;
import com.example.controller.Controller;

public class Registration {
    private JPanel registrationPanel;

    public Registration() {
        Controller controller = new Controller();

        // Main panel with BorderLayout
        registrationPanel = new JPanel(new BorderLayout());
        registrationPanel.setOpaque(false); // trasparente

        // Add header panel at the top
        registrationPanel.add(controller.createHeaderPanel(), BorderLayout.NORTH);

        // Center Panel with GridBagLayout for centering content
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Pannello blu con angoli arrotondati per il form
        RoundedPanel contentPanel = new RoundedPanel(30, new Color(0, 102, 153));
        contentPanel.setPreferredSize(new Dimension(400, 500));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Register label
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(registerLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Add input fields
        contentPanel.add(controller.createTextField("Name"));
        contentPanel.add(Box.createVerticalStrut(15));

        contentPanel.add(controller.createPasswordField("Password"));
        contentPanel.add(Box.createVerticalStrut(15));

        contentPanel.add(controller.createTextField("Username"));
        contentPanel.add(Box.createVerticalStrut(15));

        contentPanel.add(controller.createTextField("Email"));
        contentPanel.add(Box.createVerticalStrut(15));

        contentPanel.add(controller.createPasswordField("Password"));
        contentPanel.add(Box.createVerticalStrut(20));

        // Submit button
        JButton submitButton = controller.createButton("Submit", e -> {
            System.out.println("Registration submitted");
        });

        // Rendi il bottone trasparente (niente sfondo, così si vede solo il pannello
        // arrotondato)
        submitButton.setContentAreaFilled(false);
        submitButton.setOpaque(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Crea un RoundedPanel come contenitore del bottone
        RoundedPanel roundedButtonPanel = new RoundedPanel(25, new Color(0, 178, 255)); // Colore azzurro per il bottone
        roundedButtonPanel.setLayout(new GridBagLayout()); // per centrare il bottone dentro il pannello
        roundedButtonPanel.setMaximumSize(new Dimension(200, 40)); // dimensione pannello
        roundedButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi il bottone al pannello arrotondato
        roundedButtonPanel.add(submitButton);
        contentPanel.add(roundedButtonPanel);

        // Add the form panel to the center panel
        mainPanel.add(contentPanel);

        // Add the center panel to the main panel
        registrationPanel.add(mainPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return registrationPanel;
    }
}