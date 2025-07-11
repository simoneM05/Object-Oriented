package com.example.gui;

import javax.swing.*;

import com.example.controller.ControllerGui;
import com.example.model.User;

import java.awt.*;

public class LoginPage extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private JPanel parentPanel;

    private ControllerGui controller;
    private Home home;

    public LoginPage(JPanel parentPanel, Home home, ControllerGui controller) {
        this.parentPanel = parentPanel;
        this.home = home;
        this.controller = controller;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        messageLabel = new JLabel(" ");

        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.setPreferredSize(new Dimension(120, 35));
        mainPanel.add(loginButton, gbc);

        JLabel registerLabel = new JLabel("Non hai un account? Registrati");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerLabel.setForeground(new Color(70, 130, 180));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentPanel != null) {
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, Home.REGISTRATION_PANEL);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(messageLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        loginButton.addActionListener(e -> {
            String email = getEmail();
            String password = getPassword();

            User user = controller.login(email, password);

            if (user != null) {
                home.setLoggedUser(user); // Salvo l’utente loggato nel frame principale
                home.showDashboard(user.getRole()); // Mostro la dashboard giusta
            } else {
                showMessage("Email o password errati", Color.RED);
            }
        });

        emailField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> loginButton.doClick());
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);

        Timer timer = new Timer(4000, e -> messageLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }

    // Getter e metodi di utilità
    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        messageLabel.setText(" ");
    }

    public void setTestCredentials(String email, String password) {
        emailField.setText(email);
        passwordField.setText(password);
    }

    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
}
