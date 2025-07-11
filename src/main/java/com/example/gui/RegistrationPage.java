package com.example.gui;

import javax.swing.*;

import com.example.controller.ControllerGui;
import com.example.model.User;

import java.awt.*;

public class RegistrationPage extends JPanel {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel messageLabel;
    private JPanel parentPanel;
    private String email;
    private ControllerGui controller = new ControllerGui();

    public RegistrationPage(JPanel parentPanel) {
        this.parentPanel = parentPanel;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        nomeField = new JTextField(20);
        cognomeField = new JTextField(20);
        usernameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Registrati");
        messageLabel = new JLabel(" ");

        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        nomeField.setFont(fieldFont);
        cognomeField.setFont(fieldFont);
        usernameField.setFont(fieldFont);
        emailField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        confirmPasswordField.setFont(fieldFont);

        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        JLabel titleLabel = new JLabel("Registrazione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        addFormField(mainPanel, gbc, "Nome:", nomeField, 1);
        addFormField(mainPanel, gbc, "Cognome:", cognomeField, 2);
        addFormField(mainPanel, gbc, "Nome Utente:", usernameField, 3);
        addFormField(mainPanel, gbc, "Email:", emailField, 4);
        addFormField(mainPanel, gbc, "Password:", passwordField, 5);
        addFormField(mainPanel, gbc, "Conferma Password:", confirmPasswordField, 6);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton.setPreferredSize(new Dimension(150, 35));
        mainPanel.add(registerButton, gbc);

        JLabel loginLabel = new JLabel("Hai già un account? Accedi");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginLabel.setForeground(new Color(70, 130, 180));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentPanel != null) {
                    CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
                    cardLayout.show(parentPanel, Home.LOGIN_PANEL);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(messageLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private void setupEventHandlers() {
        registerButton.addActionListener(e -> {
            String firstName = nomeField.getText().trim();
            String lastName = cognomeField.getText().trim();
            String username = usernameField.getText().trim();
            String emailInput = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Controlli base
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || emailInput.isEmpty()
                    || password.isEmpty()) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Compila tutti i campi.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Le password non coincidono.");
                return;
            }

            boolean registered = controller.register(emailInput, username, password, firstName, lastName);

            if (registered) {
                messageLabel.setForeground(new Color(34, 139, 34)); // verde
                messageLabel.setText("Registrazione riuscita!");
                clearFields();
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Email già registrata o errore nella registrazione.");
            }
        });

        nomeField.addActionListener(e -> cognomeField.requestFocus());
        cognomeField.addActionListener(e -> usernameField.requestFocus());
        usernameField.addActionListener(e -> emailField.requestFocus());
        emailField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> confirmPasswordField.requestFocus());
        confirmPasswordField.addActionListener(e -> {
            // Vuoto: implementa tu la logica
        });
    }

    public void clearFields() {
        nomeField.setText("");
        cognomeField.setText("");
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        messageLabel.setText(" ");
    }

    public void loadUserData(User user) {
        if (user != null) {
            // Per esempio, prendi la mail:

            this.email = user.getEmail();
        }

    }
}
