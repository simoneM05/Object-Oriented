package com.example.gui;

import javax.swing.*;
import com.example.controller.ControllerGui;
import com.example.model.Partecipant;
import com.example.model.Role;
import com.example.model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends JPanel {

    private JLabel welcomeLabel;
    private JPanel hackathonsPanel;
    private JScrollPane scrollPane;
    private String email;
    private List<HackathonCard> hackathonCards;
    ControllerGui controller = new ControllerGui();

    public UserDashboard() {
        setLayout(new BorderLayout(10, 10));
        hackathonCards = new ArrayList<>();

        initComponents();
        loadInitialData(); // Load some dummy data
    }

    private void initComponents() {
        welcomeLabel = new JLabel("Welcome, User!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panel principale per contenere le card degli hackathon
        hackathonsPanel = new JPanel();
        hackathonsPanel.setLayout(new BoxLayout(hackathonsPanel, BoxLayout.Y_AXIS));
        hackathonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ScrollPane per permettere lo scroll se ci sono molti hackathon
        scrollPane = new JScrollPane(hackathonsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Pannello per il titolo della sezione
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Available Hackathons");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Riposiziona il welcome label in un pannello header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(welcomeLabel, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void loadInitialData() {
        // Crea le card utilizzando i dati del controller
        controller.getAllHackathons().forEach(hackathon -> {
            createHackathonCard(
                    hackathon.getId(),
                    hackathon.getTitle(),
                    hackathon.getProblemDescription(),
                    hackathon.getStartDate(),
                    hackathon.getEndDate(),
                    hackathon.getLocation(),
                    hackathon.getRegistrationStart(),
                    hackathon.getRegistrationEnd());
        });

    }

    private void createHackathonCard(int hackathonId, String title, String description, LocalDateTime startDate,
            LocalDateTime endDate,
            String location, LocalDateTime startRegistration, LocalDateTime endRegistration) {
        HackathonCard card = new HackathonCard(
                hackathonId, title, description, startDate, endDate, location, startRegistration,
                endRegistration);
        hackathonCards.add(card);
        hackathonsPanel.add(card);
        hackathonsPanel.add(Box.createVerticalStrut(15)); // Spazio tra le card
    }

    public void loadUserData(User user) {
        if (user != null) {
            this.email = user.getEmail();
        }
    }

    // Classe interna per rappresentare una card di hackathon
    private class HackathonCard extends JPanel {
        private String hackathonTitle;
        private int hackathonId; // Assuming you have an ID for the hackathon
        private JButton joinButton;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime startRegistration;
        private LocalDateTime endRegistration;
        private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        public HackathonCard(int hackathonId, String title, String description, LocalDateTime startDate,
                LocalDateTime endDate,
                String location, LocalDateTime startRegistration, LocalDateTime endRegistration) {
            this.hackathonTitle = title;
            this.hackathonId = hackathonId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.startRegistration = startRegistration;
            this.endRegistration = endRegistration;

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
            setBackground(Color.WHITE);

            // Pannello per il titolo
            JPanel titlePanel = new JPanel(new BorderLayout());
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setForeground(new Color(51, 51, 51));
            titlePanel.add(titleLabel, BorderLayout.WEST);

            add(titlePanel, BorderLayout.NORTH);

            // Pannello centrale con descrizione e pannelli di stato
            JPanel centerPanel = new JPanel(new BorderLayout());

            // Pannello per la descrizione
            JTextArea descriptionArea = new JTextArea(description);
            descriptionArea.setEditable(false);
            descriptionArea.setOpaque(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descriptionArea.setForeground(new Color(102, 102, 102));
            descriptionArea.setRows(2);
            centerPanel.add(descriptionArea, BorderLayout.NORTH);

            // Pannello per i pannelli di stato
            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            statusPanel.setOpaque(false);

            // Pannello stato registrazioni
            JPanel registrationStatusPanel = createRegistrationStatusPanel();
            statusPanel.add(registrationStatusPanel);

            // Pannello stato evento
            JPanel eventStatusPanel = createEventStatusPanel();
            statusPanel.add(eventStatusPanel);

            centerPanel.add(statusPanel, BorderLayout.SOUTH);
            add(centerPanel, BorderLayout.CENTER);

            // Pannello per informazioni e bottone
            JPanel bottomPanel = new JPanel(new BorderLayout());

            // Pannello per le informazioni
            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            infoPanel.setOpaque(false);

            // Data e ora evento
            JLabel dateIconLabel = new JLabel(formatDateTimeRange(startDate, endDate));
            dateIconLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            dateIconLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(dateIconLabel);

            // Località
            JLabel locationIconLabel = new JLabel(location);
            locationIconLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            locationIconLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(locationIconLabel);

            // Date registrazioni
            JLabel registrationDateLabel = new JLabel(
                    "Registration: " + formatDateTimeRange(startRegistration, endRegistration));
            registrationDateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            registrationDateLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(registrationDateLabel);

            bottomPanel.add(infoPanel, BorderLayout.WEST);

            // Bottone per iscriversi
            joinButton = new JButton("Join Hackathon");
            updateJoinButtonState();

            joinButton.setFocusPainted(false);
            joinButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            joinButton.setFont(new Font("Arial", Font.BOLD, 12));
            joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Effetto hover solo se il bottone è abilitato
            joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (joinButton.isEnabled()) {
                        joinButton.setBackground(new Color(25, 118, 210));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (joinButton.isEnabled()) {
                        joinButton.setBackground(new Color(33, 150, 243));
                    }
                }
            });

            joinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    joinHackathon(hackathonId);
                }
            });

            bottomPanel.add(joinButton, BorderLayout.EAST);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private JPanel createRegistrationStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(true);
            panel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

            LocalDateTime now = LocalDateTime.now();
            JLabel statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
            statusLabel.setForeground(Color.WHITE);

            if (now.isBefore(startRegistration)) {
                statusLabel.setText("REGISTRATIONS NOT OPEN");
                panel.setBackground(new Color(158, 158, 158)); // Grigio
            } else if (now.isAfter(endRegistration)) {
                statusLabel.setText("REGISTRATIONS CLOSED");
                panel.setBackground(new Color(244, 67, 54)); // Rosso
            } else {
                statusLabel.setText("REGISTRATIONS OPEN");
                panel.setBackground(new Color(76, 175, 80)); // Verde
            }

            panel.add(statusLabel);
            return panel;
        }

        private JPanel createEventStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(true);
            panel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

            LocalDateTime now = LocalDateTime.now();
            JLabel statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
            statusLabel.setForeground(Color.WHITE);

            if (now.isBefore(startDate)) {
                statusLabel.setText("UPCOMING");
                panel.setBackground(new Color(33, 150, 243)); // Blu
            } else if (now.isAfter(endDate)) {
                statusLabel.setText("FINISHED");
                panel.setBackground(new Color(96, 125, 139)); // Grigio scuro
            } else {
                statusLabel.setText("IN PROGRESS");
                panel.setBackground(new Color(255, 152, 0)); // Arancione
            }

            panel.add(statusLabel);
            return panel;
        }

        private void updateJoinButtonState() {
            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(startRegistration) || now.isAfter(endRegistration)) {
                joinButton.setEnabled(false);
                joinButton.setText("Registration Closed");
                joinButton.setBackground(new Color(189, 189, 189));
                joinButton.setForeground(new Color(117, 117, 117));
            } else {
                joinButton.setEnabled(true);
                joinButton.setText("Join Hackathon");
                joinButton.setBackground(new Color(33, 150, 243));
                joinButton.setForeground(Color.WHITE);
            }
        }

        private String formatDateTimeRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
            if (startDateTime == null || endDateTime == null) {
                return "TBD";
            }

            String startDateStr = startDateTime.format(dateFormatter);
            String endDateStr = endDateTime.format(dateFormatter);

            if (startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
                // Stesso giorno - mostra solo la data una volta con gli orari
                return startDateStr + " (" + startDateTime.format(timeFormatter) +
                        " - " + endDateTime.format(timeFormatter) + ")";
            } else {
                // Giorni diversi - mostra date complete
                return startDateStr + " " + startDateTime.format(timeFormatter) +
                        " - " + endDateStr + " " + endDateTime.format(timeFormatter);
            }
        }

        private void joinHackathon(int hackathonId) {
            System.out.println("Attempting to join hackathon: " + hackathonTitle);
            User user = controller.getUserByEmail(email);
            Partecipant participant = new Partecipant(user, hackathonId, null);
            controller.savePartecipant(participant);

            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to join '" + hackathonTitle + "'?",
                    "Confirm Registration", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            Home home = new Home();
            home.showDashboard(Role.PARTICIPANT);
            if (result == JOptionPane.YES_OPTION) {
                joinButton.setText("Joined!");
                joinButton.setForeground(Color.WHITE);
                joinButton.setBackground(new Color(76, 175, 80));
                joinButton.setEnabled(false);

                JOptionPane.showMessageDialog(this,
                        "Successfully joined '" + hackathonTitle + "'!\nYou will receive further details via email.",
                        "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}