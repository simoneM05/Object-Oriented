package com.example.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.example.controller.ControllerGui;
import com.example.model.Hackathon;
import com.example.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrganizerDashboard extends JPanel {

    // Campi del form completo
    private JTextField hackathonNameField;
    private JTextField locationField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField maxParticipantsField;
    private JTextField maxTeamSizeField;
    private JButton createHackathonButton;
    private JPanel hackathonListPanel;
    private JScrollPane hackathonScrollPane;
    private JButton refreshButton; // Riferimento al bottone di refresh

    // controller gui
    ControllerGui controller = new ControllerGui();
    private String email;

    // Colori del tema
    private static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);

    public OrganizerDashboard() {
        setLayout(new BorderLayout(15, 15));
        setBackground(LIGHT_GRAY);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        initComponents();
        addListeners();
        refreshHackathonList(); // Carica gli hackathon iniziali al startup
    }

    private void initComponents() {
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main content con due sezioni
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setOpaque(false);

        // Sezione creazione hackathon
        JPanel creationSection = createCreationSection();
        mainPanel.add(creationSection);

        // Sezione gestione hackathon esistenti
        JPanel managementSection = createManagementSection();
        mainPanel.add(managementSection);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Hackathon Organizer Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Crea e gestisci i tuoi hackathon");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // Bottone di aggiornamento
        refreshButton = new JButton("Aggiorna"); // Assegna al campo refreshButton
        refreshButton.setFocusPainted(false);
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createCreationSection() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);

        // Titolo sezione
        JLabel sectionTitle = new JLabel(" Crea Nuovo Hackathon");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);

        // Form panel con scrolling
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)));

        // Nome Hackathon
        formPanel.add(createFormField("Nome Hackathon", hackathonNameField = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(5));

        // Località
        formPanel.add(createFormField("Località", locationField = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(5));

        // Date fields
        JPanel datePanel = new JPanel(new GridLayout(1, 2, 15, 0));
        datePanel.setOpaque(false);

        JPanel startDatePanel = createFormField("Data Inizio Hackathon (YYYY-MM-DD)",
                startDateField = createStyledTextField());
        JPanel endDatePanel = createFormField("Data Fine Hackathon (YYYY-MM-DD)",
                endDateField = createStyledTextField());

        datePanel.add(startDatePanel);
        datePanel.add(endDatePanel);
        formPanel.add(datePanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Participants and Team Size fields
        JPanel participantsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        participantsPanel.setOpaque(false);

        JPanel maxParticipantsPanel = createFormField("Numero Max Partecipanti",
                maxParticipantsField = createStyledTextField());
        JPanel maxTeamSizePanel = createFormField("Dimensione Max Team",
                maxTeamSizeField = createStyledTextField());

        participantsPanel.add(maxParticipantsPanel);
        participantsPanel.add(maxTeamSizePanel);
        formPanel.add(participantsPanel);
        formPanel.add(Box.createVerticalStrut(25));

        // Create button
        createHackathonButton = new JButton("Crea Hackathon");
        styleButton(createHackathonButton, SUCCESS_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createHackathonButton);
        formPanel.add(buttonPanel);

        // Wrapper con scroll
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        section.add(formScrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createManagementSection() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);

        // Titolo sezione
        JLabel sectionTitle = new JLabel(" Gestisci Hackathon Esistenti");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);

        // Lista hackathon
        hackathonListPanel = new JPanel();
        hackathonListPanel.setLayout(new BoxLayout(hackathonListPanel, BoxLayout.Y_AXIS));
        hackathonListPanel.setBackground(LIGHT_GRAY);

        hackathonScrollPane = new JScrollPane(hackathonListPanel);
        hackathonScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        hackathonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hackathonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        hackathonScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        section.add(hackathonScrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 8));
        fieldPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(PRIMARY_COLOR);

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);

        return fieldPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        field.setBackground(new Color(250, 250, 250));
        return field;
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = backgroundColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private JPanel createHackathonCard(Hackathon hackathon, String status) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Info panel
        JPanel infoPanel = new JPanel(new BorderLayout(0, 8));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(hackathon.getTitle());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(PRIMARY_COLOR);

        // Panel per le info dettagliate
        JPanel detailInfoPanel = new JPanel(new BorderLayout(0, 3));
        detailInfoPanel.setOpaque(false);

        JLabel locationLabel = new JLabel(hackathon.getLocation());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        locationLabel.setForeground(new Color(120, 120, 120));

        JLabel hackathonLabel = new JLabel(hackathon.getStartDate().toLocalDate()
                + " → " + hackathon.getEndDate().toLocalDate());
        hackathonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hackathonLabel.setForeground(new Color(120, 120, 120));

        JLabel participantsLabel = new JLabel(hackathon.getMaxParticipants()
                + " partecipanti | Team max " + hackathon.getMaxTeamSize());
        participantsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        participantsLabel.setForeground(SECONDARY_COLOR);

        detailInfoPanel.add(locationLabel, BorderLayout.NORTH);
        detailInfoPanel.add(hackathonLabel, BorderLayout.CENTER);
        detailInfoPanel.add(participantsLabel, BorderLayout.SOUTH);

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(detailInfoPanel, BorderLayout.SOUTH);

        // Status e action panel
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setOpaque(false);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(new EmptyBorder(4, 12, 4, 12));

        if (status.equals("Attivo")) {
            statusLabel.setBackground(SUCCESS_COLOR);
            statusLabel.setForeground(Color.WHITE);
        } else if (status.equals("Terminato")) {
            statusLabel.setBackground(new Color(149, 165, 166));
            statusLabel.setForeground(Color.WHITE);
        } else { // Presuppone "Programmato" o "Non iniziato" per gli altri casi
            statusLabel.setBackground(SECONDARY_COLOR);
            statusLabel.setForeground(Color.WHITE);
        }

        // Panel per i bottoni
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        buttonPanel.setOpaque(false);

        if (status.equals("Attivo")
                && hackathon.getRegistrationStart() == null
                && hackathon.getRegistrationEnd() == null) {

            JButton openRegistrationButton = new JButton("Apri Iscrizioni");
            styleButton(openRegistrationButton, WARNING_COLOR);

            openRegistrationButton.addActionListener(e -> {
                openRegistrations(hackathon.getTitle());
                refreshHackathonList(); // Aggiorna la lista dopo l'azione
            });

            buttonPanel.add(openRegistrationButton);
        }

        // Pulsante "Elimina" - disponibile solo per hackathon "Non iniziato" o
        // "Terminato"
        if (status.equals("Non iniziato") || status.equals("Terminato")) {
            JButton deleteButton = new JButton("Elimina");
            styleButton(deleteButton, DANGER_COLOR);
            deleteButton.addActionListener(e -> {
                deleteHackathon(hackathon.getTitle());
                refreshHackathonList(); // Aggiorna la lista dopo l'azione
            });
            buttonPanel.add(deleteButton);
        }

        rightPanel.add(statusLabel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    /**
     * Metodo per ricaricare e visualizzare la lista degli hackathon.
     */
    private void refreshHackathonList() {
        hackathonListPanel.removeAll(); // Rimuove tutti i componenti esistenti

        List<Hackathon> hackathons = controller.getAllHackathons(); // Ottieni i dati più recenti

        if (hackathons.isEmpty()) {
            JLabel noHackathonsLabel = new JLabel("Nessun hackathon da visualizzare.");
            noHackathonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noHackathonsLabel.setForeground(PRIMARY_COLOR);
            JPanel centerPanel = new JPanel(new GridBagLayout()); // Usa GridBagLayout per centrare
            centerPanel.setOpaque(false);
            centerPanel.add(noHackathonsLabel);
            hackathonListPanel.add(centerPanel);
        } else {
            for (Hackathon hackathon : hackathons) {
                String status;
                LocalDateTime now = LocalDateTime.now();

                // Logica per determinare lo stato dell'hackathon
                if (now.isBefore(hackathon.getStartDate())) {
                    status = "Non iniziato";
                } else if (now.isAfter(hackathon.getEndDate())) {
                    status = "Terminato";
                } else {
                    status = "Attivo";
                }
                hackathonListPanel.add(createHackathonCard(hackathon, status));
                hackathonListPanel.add(Box.createVerticalStrut(15)); // Spazio tra le card
            }
        }

        hackathonListPanel.add(Box.createVerticalGlue()); // Aggiungi glue alla fine per allineare in alto

        // Ridisegna il pannello
        hackathonListPanel.revalidate();
        hackathonListPanel.repaint();
    }

    private void addListeners() {
        createHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHackathon();
            }
        });

        // Aggiungi l'ActionListener al bottone di refresh
        refreshButton.addActionListener(e -> refreshHackathonList());
    }

    private void createHackathon() {
        String title = hackathonNameField.getText().trim();
        String location = locationField.getText().trim();
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();
        String maxParticipantsStr = maxParticipantsField.getText().trim();
        String maxTeamSizeStr = maxTeamSizeField.getText().trim();

        if (title.isEmpty() || location.isEmpty() || startDateStr.isEmpty()
                || endDateStr.isEmpty() || maxParticipantsStr.isEmpty() || maxTeamSizeStr.isEmpty()) {
            showStyledMessage("Errore", "Per favore compila tutti i campi.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Parsing delle date e orari (es. "2025-08-01 09:00")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, formatter);

            // Validazione date
            if (endDateTime.isBefore(startDateTime)) {
                showStyledMessage("Errore", "La data di fine non può essere precedente alla data di inizio.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parsing dei numeri
            int maxParticipants = Integer.parseInt(maxParticipantsStr);
            int maxTeamSize = Integer.parseInt(maxTeamSizeStr);

            // Validazione numeri
            if (maxParticipants <= 0 || maxTeamSize <= 0) {
                showStyledMessage("Errore",
                        "Il numero massimo di partecipanti e la dimensione massima del team devono essere numeri positivi.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Costruzione oggetto Hackathon (modifica costruttore se necessario)
            Hackathon hackathon = new Hackathon(
                    title,
                    location,
                    startDateTime,
                    endDateTime,
                    maxParticipants,
                    maxTeamSize,
                    null, // registrationStart
                    null, // problemDescription
                    email);

            hackathon.setRegistrationEnd(null); // Imposta a null se non necessario

            System.out.println("start: " + startDateTime + ", end: " + endDateTime);
            System.out.println("Hackathon: " + hackathon);

            // alice.smith@example.com

            controller.createHackathon(hackathon);

            showStyledMessage("Successo", "Hackathon '" + title + "' creato con successo!",
                    JOptionPane.INFORMATION_MESSAGE);

            // Pulisci i campi
            hackathonNameField.setText("");
            locationField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            maxParticipantsField.setText("");
            maxTeamSizeField.setText("");

            // Aggiorna la lista degli hackathon per mostrare quello appena creato
            refreshHackathonList();

        } catch (java.time.format.DateTimeParseException e) {
            showStyledMessage("Errore di Formato Data", "Il formato della data e ora deve essere 'YYYY-MM-DD HH:MM'.",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            showStyledMessage("Errore di Formato Numero",
                    "Il numero massimo di partecipanti e la dimensione massima del team devono essere numeri validi.",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showStyledMessage("Errore di Creazione",
                    "Si è verificato un errore durante la creazione dell'hackathon: " + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void openRegistrations(String hackathonName) {
        Hackathon hackathon = controller.getHackathonByTitle(hackathonName).orElse(null);
        if (hackathon == null) {
            showStyledMessage("Errore", "Hackathon non trovato.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        hackathon.setRegistrationStart(LocalDateTime.now());
        hackathon.setRegistrationEnd(LocalDateTime.now().plusDays(3));

        controller.updateHackathon(hackathon);

        showStyledMessage("Successo", "Iscrizioni aperte per '" + hackathonName + "'!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteHackathon(String hackathonName) {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Sei sicuro di voler eliminare l'hackathon '" + hackathonName + "'?",
                "Conferma Eliminazione",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            controller.getHackathonByTitle(hackathonName).ifPresent(controller::deleteHackathon);
            System.out.println("Eliminazione hackathon: " + hackathonName);
            // Esempio: controller.deleteHackathon(hackathonName);
            showStyledMessage("Successo", "Hackathon eliminato con successo!", JOptionPane.INFORMATION_MESSAGE);
            refreshHackathonList(); // Aggiorna la lista dopo l'eliminazione
        }
    }

    private void showStyledMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void loadUserData(User user) {
        if (user != null) {
            // Per esempio, prendi la mail:

            this.email = user.getEmail();
        }

    }

}