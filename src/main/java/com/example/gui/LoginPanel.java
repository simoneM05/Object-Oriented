package com.example.gui;

import com.example.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for EmptyBorder
import javax.swing.border.LineBorder; // Import for LineBorder
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Controller controller;

    public LoginPanel(Controller controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout()); // Use GridBagLayout for flexible positioning
        setBackground(new Color(230, 230, 230)); // Light grey background for a modern feel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increased padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Accedi al tuo account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Modern font, larger size
        titleLabel.setForeground(new Color(50, 50, 50)); // Dark grey
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Username Label
        JLabel usernameLabel = new JLabel("Nome Utente:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Modern font, good size
        usernameLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);

        // Username Field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1), // Subtle line border
                new EmptyBorder(10, 15, 10, 15) // Generous internal padding
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Accedi");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Bold and larger font
        loginButton.setBackground(new Color(60, 140, 220)); // A vibrant blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // No focus border
        loginButton.setBorder(new EmptyBorder(12, 30, 12, 30)); // Padding for button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                controller.handleLogin(username, password);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 70; // Internal padding
        gbc.ipady = 15;
        add(loginButton, gbc);

        // Register Button (as a link)
        registerButton = new JButton("Non hai un account? Registrati!");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        registerButton.setForeground(new Color(20, 100, 180)); // A slightly darker blue for link
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showRegistrationPanel();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 15, 15); // More top padding
        add(registerButton, gbc);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }
}
