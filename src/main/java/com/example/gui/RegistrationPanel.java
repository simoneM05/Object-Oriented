package com.example.gui;

import com.example.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPanel extends JPanel {
    private JTextField firstNameField; // New field
    private JTextField lastNameField; // New field
    private JTextField emailField; // New field
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private Controller controller;

    public RegistrationPanel(Controller controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(230, 230, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Crea un nuovo account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // --- First Name ---
        JLabel firstNameLabel = new JLabel("Nome:");
        firstNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        firstNameLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 1; // Updated gridy
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 1; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(firstNameField, gbc);

        // --- Last Name ---
        JLabel lastNameLabel = new JLabel("Cognome:");
        lastNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lastNameLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 2; // Updated gridy
        gbc.anchor = GridBagConstraints.EAST;
        add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 2; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(lastNameField, gbc);

        // --- Email ---
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 3; // Updated gridy
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 3; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // --- Username Label (now at gridy 4) ---
        JLabel usernameLabel = new JLabel("Nome Utente:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usernameLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 4; // Updated gridy
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);

        // --- Username Field (now at gridy 4) ---
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 4; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        // --- Password Label (now at gridy 5) ---
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 5; // Updated gridy
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        // --- Password Field (now at gridy 5) ---
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 5; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // --- Confirm Password Label (now at gridy 6) ---
        JLabel confirmPasswordLabel = new JLabel("Conferma Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        confirmPasswordLabel.setForeground(new Color(70, 70, 70));
        gbc.gridx = 0;
        gbc.gridy = 6; // Updated gridy
        gbc.anchor = GridBagConstraints.EAST;
        add(confirmPasswordLabel, gbc);

        // --- Confirm Password Field (now at gridy 6) ---
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1),
                new EmptyBorder(10, 15, 10, 15)));
        gbc.gridx = 1;
        gbc.gridy = 6; // Updated gridy
        gbc.anchor = GridBagConstraints.WEST;
        add(confirmPasswordField, gbc);

        // --- Register Button (now at gridy 7) ---
        registerButton = new JButton("Registrati");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        registerButton.setBackground(new Color(80, 180, 120)); // A pleasant green
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(new EmptyBorder(12, 30, 12, 30));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText(); // Get new field data
                String lastName = lastNameField.getText(); // Get new field data
                String email = emailField.getText(); // Get new field data
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                // Pass all data to the controller
                controller.handleRegistration(firstName, lastName, email, username, password, confirmPassword);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7; // Updated gridy
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipadx = 70;
        gbc.ipady = 15;
        add(registerButton, gbc);

        // --- Back to Login Button (now at gridy 8) ---
        backToLoginButton = new JButton("Torna al Login");
        backToLoginButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backToLoginButton.setForeground(new Color(20, 100, 180));
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showLoginPanel();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8; // Updated gridy
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 15, 15);
        add(backToLoginButton, gbc);
    }
}