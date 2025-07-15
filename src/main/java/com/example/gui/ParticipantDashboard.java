package com.example.gui;

import javax.swing.*;

import com.example.controller.ControllerGui;
import com.example.model.Hackathon;
import com.example.model.Partecipant;
import com.example.model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ParticipantDashboard extends JPanel {

    private JLabel welcomeLabel;
    private JTabbedPane mainTabbedPane;

    // Components for My Hackathons tab
    private JList<String> joinedHackathonsList;
    private DefaultListModel<String> joinedHackathonsModel;
    private JButton viewMyProjectButton;

    // Components for Discover Hackathons tab
    private JList<String> availableHackathonsList;
    private DefaultListModel<String> availableHackathonsModel;
    private JButton joinHackathonButton;

    private Partecipant loginUser;
    private Hackathon hackathon;
    private ControllerGui controller = new ControllerGui();

    public ParticipantDashboard() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(248, 249, 250));

        initComponents();
        addListeners();
        // Non chiamare loadInitialData() qui - sarà chiamato in loadUserData()
    }

    private void initComponents() {
        // Header con messaggio di benvenuto
        welcomeLabel = new JLabel("Welcome, Participant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(33, 37, 41));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // Pannello principale con tab
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainTabbedPane.setBackground(Color.WHITE);

        // Tab 1: My Hackathons - Hackathon iscritti con funzionalità
        JPanel myHackathonsPanel = createMyHackathonsPanel();
        mainTabbedPane.addTab("My Hackathons", myHackathonsPanel);

        // Tab 2: My Team - Team di cui si fa parte o creazione team
        JPanel myTeamPanel = createMyTeamPanel();
        mainTabbedPane.addTab("My Team", myTeamPanel);

        // Tab 3: Team Requests - Richieste da altri team
        JPanel teamRequestsPanel = createTeamRequestsPanel();
        mainTabbedPane.addTab("Team Requests", teamRequestsPanel);

        // Tab 4: Discover Hackathons - Hackathon disponibili con funzionalità
        JPanel discoverPanel = createDiscoverHackathonsPanel();
        mainTabbedPane.addTab("Discover Hackathons", discoverPanel);

        add(mainTabbedPane, BorderLayout.CENTER);
    }

    private void addListeners() {
        joinHackathonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinHackathon();
            }
        });

        viewMyProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMyProject();
            }
        });
    }

    private void loadInitialData() {
        // Verifica che i dati necessari siano stati caricati
        if (loginUser == null || hackathon == null) {
            System.err.println("Warning: User data not loaded yet. Cannot load initial data.");
            return;
        }

        // Carica l'hackathon dell'utente se presente
        if (hackathon != null) {
            joinedHackathonsModel.addElement(hackathon.getTitle() + " (Joined)");
        }

        // Carica tutti gli hackathon disponibili
        try {
            controller.getAllHackathons().forEach(availableHackathon -> {
                // Verifica che l'hackathon non sia già nella lista degli iscritti
                String hackathonTitle = availableHackathon.getTitle();
                boolean alreadyJoined = false;

                // Controlla se l'utente è già iscritto a questo hackathon
                // FIX: Usa Objects.equals() per confrontare safely i valori
                if (Objects.equals(loginUser.getHackathonID(), availableHackathon.getId())) {
                    alreadyJoined = true;
                }

                if (!alreadyJoined) {
                    availableHackathonsModel.addElement(hackathonTitle);
                }
            });
        } catch (Exception e) {
            System.err.println("Error loading hackathons: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel createMyHackathonsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Your Joined Hackathons");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Lista hackathon iscritti con stile migliorato
        joinedHackathonsModel = new DefaultListModel<>();
        joinedHackathonsList = new JList<>(joinedHackathonsModel);
        joinedHackathonsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        joinedHackathonsList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        joinedHackathonsList.setBackground(Color.WHITE);
        joinedHackathonsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Custom renderer per una migliore visualizzazione
        joinedHackathonsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                if (isSelected) {
                    setBackground(new Color(0, 123, 255));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(new Color(33, 37, 41));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(joinedHackathonsList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottone per visualizzare progetto
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        viewMyProjectButton = new JButton("View My Project");
        viewMyProjectButton.setBackground(new Color(0, 123, 255));
        viewMyProjectButton.setForeground(Color.WHITE);
        viewMyProjectButton.setFocusPainted(false);
        viewMyProjectButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        viewMyProjectButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonPanel.add(viewMyProjectButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMyTeamPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Team Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Simula due scenari: con team e senza team
        boolean hasTeam = true; // Cambia a false per vedere l'interfaccia senza team

        if (hasTeam) {
            panel.add(createTeamDetailsPanel(), BorderLayout.CENTER);
        } else {
            panel.add(createNoTeamPanel(), BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createTeamDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Info team
        JPanel teamInfoPanel = new JPanel(new BorderLayout(10, 10));
        teamInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        "Team Information"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        teamInfoPanel.setBackground(Color.WHITE);

        JLabel teamNameLabel = new JLabel("Team Name: Code Warriors");
        teamNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        teamInfoPanel.add(teamNameLabel, BorderLayout.NORTH);

        JLabel hackathonLabel = new JLabel("Hackathon: Spring Code Fest 2025");
        hackathonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hackathonLabel.setForeground(new Color(108, 117, 125));
        teamInfoPanel.add(hackathonLabel, BorderLayout.CENTER);

        panel.add(teamInfoPanel, BorderLayout.NORTH);

        // Lista membri del team
        JPanel membersPanel = new JPanel(new BorderLayout(10, 10));
        membersPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        "Team Members"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        membersPanel.setBackground(Color.WHITE);

        String[] members = {
                "👑 John Doe (Team Leader) - john.doe@email.com",
                "👨‍💻 You (Developer) - participant@email.com",
                "🎨 Alice Smith (Designer) - alice.smith@email.com",
                "📊 Bob Johnson (Data Analyst) - bob.johnson@email.com"
        };

        DefaultListModel<String> membersModel = new DefaultListModel<>();
        for (String member : members) {
            membersModel.addElement(member);
        }

        JList<String> membersList = new JList<>(membersModel);
        membersList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        membersList.setBackground(Color.WHITE);
        JScrollPane membersScrollPane = new JScrollPane(membersList);
        membersPanel.add(membersScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        JButton leaveTeamButton = new JButton("Leave Team");
        leaveTeamButton.setBackground(new Color(220, 53, 69));
        leaveTeamButton.setForeground(Color.WHITE);
        leaveTeamButton.setFocusPainted(false);
        leaveTeamButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        leaveTeamButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to leave the team?",
                    "Leave Team",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "You have left the team.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(leaveTeamButton);
        membersPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(membersPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createNoTeamPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        JLabel infoLabel = new JLabel(
                "<html><center>You are not part of any team yet.<br>Create a new team or wait for team invitations.</center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(new Color(108, 117, 125));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(infoLabel, BorderLayout.NORTH);

        // Form per creare nuovo team
        JPanel createTeamPanel = new JPanel(new GridBagLayout());
        createTeamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        "Create New Team"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        createTeamPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        createTeamPanel.add(new JLabel("Team Name:"), gbc);

        gbc.gridx = 1;
        JTextField teamNameField = new JTextField(20);
        createTeamPanel.add(teamNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        createTeamPanel.add(new JLabel("Hackathon:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> hackathonCombo = new JComboBox<>(new String[] {
                "Spring Code Fest 2025", "AI Innovation Challenge"
        });
        createTeamPanel.add(hackathonCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton createTeamButton = new JButton("Create Team");
        createTeamButton.setBackground(new Color(40, 167, 69));
        createTeamButton.setForeground(Color.WHITE);
        createTeamButton.setFocusPainted(false);
        createTeamButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createTeamButton.addActionListener(e -> {
            String teamName = teamNameField.getText().trim();
            if (teamName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a team name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Team '" + teamName + "' created successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            teamNameField.setText("");
        });
        createTeamPanel.add(createTeamButton, gbc);

        panel.add(createTeamPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTeamRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Team Invitations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Container per le richieste
        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(Color.WHITE);

        // Richiesta 1
        JPanel request1 = createTeamRequestCard(
                "Tech Innovators",
                "Spring Code Fest 2025",
                "Sarah Connor",
                "We're looking for a skilled developer to join our team. We have a great idea for the inventory management challenge!");
        requestsPanel.add(request1);
        requestsPanel.add(Box.createVerticalStrut(15));

        // Richiesta 2
        JPanel request2 = createTeamRequestCard(
                "Data Wizards",
                "AI Innovation Challenge",
                "Mike Johnson",
                "Join our AI-focused team! We need someone with your skills to help us build the food waste reduction solution.");
        requestsPanel.add(request2);
        requestsPanel.add(Box.createVerticalStrut(15));

        // Richiesta 3
        JPanel request3 = createTeamRequestCard(
                "Creative Coders",
                "Spring Code Fest 2025",
                "Emma Davis",
                "Looking for a passionate developer to complete our team. We have a unique approach to the business management problem!");
        requestsPanel.add(request3);

        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTeamRequestCard(String teamName, String hackathon, String senderName, String message) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel teamLabel = new JLabel(teamName);
        teamLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        teamLabel.setForeground(new Color(33, 37, 41));
        headerPanel.add(teamLabel, BorderLayout.WEST);

        JLabel hackathonLabel = new JLabel(hackathon);
        hackathonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hackathonLabel.setForeground(new Color(108, 117, 125));
        headerPanel.add(hackathonLabel, BorderLayout.EAST);

        card.add(headerPanel, BorderLayout.NORTH);

        // Messaggio
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.WHITE);

        JLabel senderLabel = new JLabel("From: " + senderName);
        senderLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        senderLabel.setForeground(new Color(108, 117, 125));
        messagePanel.add(senderLabel, BorderLayout.NORTH);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageArea.setForeground(new Color(73, 80, 87));
        messageArea.setBackground(Color.WHITE);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        messagePanel.add(messageArea, BorderLayout.CENTER);

        card.add(messagePanel, BorderLayout.CENTER);

        // Bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton declineButton = new JButton("Decline");
        declineButton.setBackground(new Color(220, 53, 69));
        declineButton.setForeground(Color.WHITE);
        declineButton.setFocusPainted(false);
        declineButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        declineButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Invitation declined.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(declineButton);

        JButton acceptButton = new JButton("Accept");
        acceptButton.setBackground(new Color(40, 167, 69));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);
        acceptButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        acceptButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Invitation accepted! Welcome to " + teamName + "!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(acceptButton);

        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createDiscoverHackathonsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Available Hackathons");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 37, 41));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Lista hackathon disponibili con stile migliorato
        availableHackathonsModel = new DefaultListModel<>();
        availableHackathonsList = new JList<>(availableHackathonsModel);
        availableHackathonsList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        availableHackathonsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableHackathonsList.setBackground(Color.WHITE);
        availableHackathonsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Custom renderer per una migliore visualizzazione
        availableHackathonsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
                if (isSelected) {
                    setBackground(new Color(40, 167, 69));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(new Color(33, 37, 41));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(availableHackathonsList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        joinHackathonButton = new JButton("Join Selected Hackathon");
        joinHackathonButton.setBackground(new Color(40, 167, 69));
        joinHackathonButton.setForeground(Color.WHITE);
        joinHackathonButton.setFocusPainted(false);
        joinHackathonButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        joinHackathonButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonPanel.add(joinHackathonButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Implementazione metodi funzionali
    private void joinHackathon() {
        String selectedHackathon = availableHackathonsList.getSelectedValue();
        if (selectedHackathon == null) {
            JOptionPane.showMessageDialog(this, "Please select a hackathon to join.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Attempting to join: " + selectedHackathon);

        // Trova l'hackathon selezionato dal controller
        try {
            Hackathon selectedHackathonObj = controller.getAllHackathons().stream()
                    .filter(h -> h.getTitle().equals(selectedHackathon))
                    .findFirst()
                    .orElse(null);

            if (selectedHackathonObj != null) {
                // FIX: Aggiornamento sicuro dell'hackathon ID dell'utente
                loginUser.setHackathonID(selectedHackathonObj.getId());

                // Qui dovresti implementare la logica per aggiornare il database
                // Ad esempio: controller.updateParticipant(loginUser);

                JOptionPane.showMessageDialog(this, "You have joined '" + selectedHackathon + "'!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Move from available to joined list
                availableHackathonsModel.removeElement(selectedHackathon);
                joinedHackathonsModel.addElement(selectedHackathon + " (Joined)");

                // Aggiorna l'oggetto hackathon corrente
                this.hackathon = selectedHackathonObj;
            } else {
                JOptionPane.showMessageDialog(this, "Error: Hackathon not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error joining hackathon: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error joining hackathon: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewMyProject() {
        String selectedJoinedHackathon = joinedHackathonsList.getSelectedValue();
        if (selectedJoinedHackathon == null) {
            JOptionPane.showMessageDialog(this, "Please select one of your joined hackathons to view its project.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Viewing project for: " + selectedJoinedHackathon);

        // Rimuovi il suffisso " (Joined)" dal nome
        String hackathonTitle = selectedJoinedHackathon.replace(" (Joined)", "");

        try {
            // Trova l'hackathon selezionato
            Hackathon selectedHackathonObj = controller.getAllHackathons().stream()
                    .filter(h -> h.getTitle().equals(hackathonTitle))
                    .findFirst()
                    .orElse(null);

            if (selectedHackathonObj != null) {
                // FIX: Verifica sicura che l'utente sia iscritto a questo hackathon
                if (Objects.equals(loginUser.getHackathonID(), selectedHackathonObj.getId())) {
                    // Qui dovresti implementare la logica per mostrare i dettagli del progetto
                    // Ad esempio: aprire una nuova finestra con i dettagli del progetto
                    JOptionPane.showMessageDialog(this,
                            "Displaying project details for " + hackathonTitle + ".\n" +
                                    "Hackathon ID: " + selectedHackathonObj.getId() + "\n" +
                                    "(Project details logic to be implemented)",
                            "View Project", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error: You are not registered for this hackathon.",
                            "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: Hackathon not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error viewing project: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error viewing project: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carica i dati dell'utente e inizializza l'interfaccia
     * Questo metodo deve essere chiamato dopo la creazione del pannello
     */
    public void loadUserData(User user) {
        if (user == null) {
            System.err.println("Error: User is null");
            return;
        }

        try {
            // Carica i dati del partecipante
            this.loginUser = controller.getPartecipantByEmail(user.getEmail());

            if (this.loginUser == null) {
                System.err.println("Error: Participant not found for email: " + user.getEmail());
                return;
            }

            // Aggiorna il messaggio di benvenuto con il nome dell'utente
            welcomeLabel.setText("Welcome, " + user.getEmail() + "!");

            // Carica l'hackathon dell'utente se presente
            if (Objects.nonNull(this.loginUser.getHackathonID())) {
                this.hackathon = controller.getHackathonById(this.loginUser.getHackathonID());
                if (this.hackathon == null) {
                    System.err.println("Warning: Hackathon not found for ID: " + this.loginUser.getHackathonID());
                }
            }

            // Ora che i dati sono caricati, carica i dati iniziali nell'interfaccia
            loadInitialData();

        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading user data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aggiorna i dati dell'interfaccia (può essere chiamato quando i dati cambiano)
     */
    public void refreshData() {
        if (loginUser != null) {
            // Pulisci i modelli esistenti
            joinedHackathonsModel.clear();
            availableHackathonsModel.clear();

            // Ricarica i dati
            loadInitialData();
        }
    }
}