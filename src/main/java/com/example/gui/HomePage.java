package com.example.gui;

import com.example.controller.Controller;
import com.example.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomePage extends JPanel {
    private Controller controller;
    private JPanel invitationsListPanel;
    private JPanel availableHackathonsPanel;
    private JPanel myTeamsPanel;

    public HomePage(Controller controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // ✅ MODIFICATO: Logica condizionale per i tab
        if (!controller.isUserRegisteredToAnyHackathon()) {
            // Utente NON registrato - mostra solo hackathon disponibili
            availableHackathonsPanel = new JPanel();
            availableHackathonsPanel.setLayout(new BoxLayout(availableHackathonsPanel, BoxLayout.Y_AXIS));
            JScrollPane hackathonScrollPane = new JScrollPane(availableHackathonsPanel);
            hackathonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            hackathonScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            tabbedPane.addTab("Hackathon Disponibili", hackathonScrollPane);
        } else {
            // Utente registrato - mostra il suo hackathon e opzioni team
            availableHackathonsPanel = new JPanel();
            availableHackathonsPanel.setLayout(new BoxLayout(availableHackathonsPanel, BoxLayout.Y_AXIS));
            JScrollPane hackathonScrollPane = new JScrollPane(availableHackathonsPanel);
            hackathonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            hackathonScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            tabbedPane.addTab("Il Mio Hackathon", hackathonScrollPane);

            // Tab Team
            myTeamsPanel = new JPanel();
            myTeamsPanel.setLayout(new BoxLayout(myTeamsPanel, BoxLayout.Y_AXIS));
            JScrollPane teamsScrollPane = new JScrollPane(myTeamsPanel);
            teamsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            teamsScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            tabbedPane.addTab("I Miei Team", teamsScrollPane);

            // Tab Richieste
            invitationsListPanel = new JPanel();
            invitationsListPanel.setLayout(new BoxLayout(invitationsListPanel, BoxLayout.Y_AXIS));
            JScrollPane invitationsScrollPane = new JScrollPane(invitationsListPanel);
            invitationsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            invitationsScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            tabbedPane.addTab("Richieste Team", invitationsScrollPane);
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 140, 220));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Welcome message
        User currentUser = controller.getCurrentUser();
        String welcomeText = currentUser != null ?
                "Benvenuto, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!" :
                "Benvenuto!";

        JLabel welcomeLabel = new JLabel(welcomeText);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setBackground(new Color(60, 140, 220));

        // Create Hackathon Button
        JButton createHackathonBtn = createStyledButton("Crea Hackathon");
        createHackathonBtn.addActionListener(e -> openCreateHackathonDialog());
        buttonsPanel.add(createHackathonBtn);

        // Refresh Button
        JButton refreshBtn = createStyledButton("Aggiorna");
        refreshBtn.addActionListener(e -> refreshData());
        buttonsPanel.add(refreshBtn);

        // Logout Button
        JButton logoutBtn = createStyledButton("Logout");
        logoutBtn.addActionListener(e -> controller.handleLogout());
        buttonsPanel.add(logoutBtn);

        headerPanel.add(buttonsPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(60, 140, 220));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * ✅ MODIFICATO: Aggiorna tutti i dati e ricostruisce la UI se necessario
     */
    public void refreshData() {
        SwingUtilities.invokeLater(() -> {
            // Ricostruisci l'intera UI per aggiornare i tab
            this.removeAll();
            initializeUI();
            
            // Carica i dati appropriati
            displayAvailableHackathons();
            
            if (controller.isUserRegisteredToAnyHackathon()) {
                if (myTeamsPanel != null) {
                    displayMyTeams();
                }
                if (invitationsListPanel != null) {
                    displayTeamRequests();
                }
            }
            
            this.revalidate();
            this.repaint();
        });
    }

    /**
     * ✅ MODIFICATO: Mostra hackathon disponibili o hackathon dell'utente
     */
    private void displayAvailableHackathons() {
        availableHackathonsPanel.removeAll();

        if (controller.isUserRegisteredToAnyHackathon()) {
            // Mostra solo l'hackathon a cui è registrato
            Hackathon userHackathon = controller.getUserRegisteredHackathon();
            if (userHackathon != null) {
                addHackathonCard(availableHackathonsPanel, userHackathon, false, true);
            } else {
                JLabel noDataLabel = new JLabel("Errore nel recupero del tuo hackathon.");
                noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                availableHackathonsPanel.add(noDataLabel);
            }
        } else {
            // Mostra tutti gli hackathon disponibili per la partecipazione
            List<Hackathon> allHackathons = controller.getAllHackathons();

            if (allHackathons == null || allHackathons.isEmpty()) {
                JLabel noDataLabel = new JLabel("Nessun hackathon disponibile al momento.");
                noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                availableHackathonsPanel.add(noDataLabel);
            } else {
                for (Hackathon hackathon : allHackathons) {
                    boolean registrationOpen = isRegistrationOpen(hackathon);
                    addHackathonCard(availableHackathonsPanel, hackathon, registrationOpen, false);
                }
            }
        }

        availableHackathonsPanel.revalidate();
        availableHackathonsPanel.repaint();
    }

    /**
     * ✅ NUOVO: Verifica se le registrazioni per un hackathon sono aperte
     */
    private boolean isRegistrationOpen(Hackathon hackathon) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(hackathon.getRegistrationStart()) && 
               now.isBefore(hackathon.getRegistrationEnd());
    }

    /**
     * ✅ MODIFICATO: Mostra i team solo per l'hackathon dell'utente
     */
    private void displayMyTeams() {
        if (myTeamsPanel == null) return;
        
        myTeamsPanel.removeAll();

        Hackathon userHackathon = controller.getUserRegisteredHackathon();
        if (userHackathon != null) {
            Team userTeam = controller.getCurrentUserTeamForHackathon(userHackathon.getId());
            if (userTeam != null) {
                addTeamCard(myTeamsPanel, userTeam, userHackathon);
            } else {
                JLabel noDataLabel = new JLabel("Non fai ancora parte di nessun team per questo hackathon.");
                noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                myTeamsPanel.add(noDataLabel);
            }
        } else {
            JLabel noDataLabel = new JLabel("Errore nel recupero del tuo hackathon.");
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            myTeamsPanel.add(noDataLabel);
        }

        myTeamsPanel.revalidate();
        myTeamsPanel.repaint();
    }

    /**
     * ✅ NUOVO: Mostra richieste di team effettive
     */
    private void displayTeamRequests() {
        if (invitationsListPanel == null) return;
        
        invitationsListPanel.removeAll();

        List<Request> requests = controller.getCurrentUserRequests();

        if (requests == null || requests.isEmpty()) {
            JLabel noDataLabel = new JLabel("Nessuna richiesta di team ricevuta.");
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            invitationsListPanel.add(noDataLabel);
        } else {
            for (Request request : requests) {
                if ("PENDING".equals(request.getStatus())) {
                    addRequestCard(invitationsListPanel, request);
                }
            }
        }

        invitationsListPanel.revalidate();
        invitationsListPanel.repaint();
    }

    /**
     * ✅ MODIFICATO: Crea card per hackathon con nuova logica
     */
    private void addHackathonCard(JPanel parentPanel, Hackathon hackathon, boolean showParticipateButton, boolean isUserHackathon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setPreferredSize(new Dimension(600, 120));

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(hackathon.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(titleLabel);

        JLabel locationLabel = new JLabel("Location: " + hackathon.getLocation());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(locationLabel);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JLabel dateLabel = new JLabel("Date: " + hackathon.getStartDate().format(formatter) +
                " - " + hackathon.getEndDate().format(formatter));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(dateLabel);

        JLabel participantsLabel = new JLabel("Max partecipanti: " + hackathon.getMaxParticipants() +
                " | Max team size: " + hackathon.getMaxTeamSize());
        participantsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(participantsLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        // ✅ NUOVO: Buttons panel con logica aggiornata
        if (showParticipateButton) {
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);

            JButton participateBtn = new JButton("Partecipa");
            participateBtn.setBackground(new Color(60, 140, 220));
            participateBtn.setForeground(Color.WHITE);
            participateBtn.setFocusPainted(false);
            participateBtn.addActionListener(e -> {
                controller.handleRegisterToHackathon(hackathon.getId());
                refreshData(); // Aggiorna dopo la registrazione
            });
            buttonPanel.add(participateBtn);

            card.add(buttonPanel, BorderLayout.EAST);
        } else if (isUserHackathon) {
            // Pulsanti per l'hackathon dell'utente
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);

            JButton createTeamBtn = new JButton("Crea Team");
            createTeamBtn.setBackground(new Color(40, 180, 60));
            createTeamBtn.setForeground(Color.WHITE);
            createTeamBtn.setFocusPainted(false);
            createTeamBtn.addActionListener(e -> openCreateTeamDialog(hackathon));
            buttonPanel.add(createTeamBtn);

            JButton viewTeamsBtn = new JButton("Vedi Team");
            viewTeamsBtn.setBackground(new Color(220, 140, 60));
            viewTeamsBtn.setForeground(Color.WHITE);
            viewTeamsBtn.setFocusPainted(false);
            viewTeamsBtn.addActionListener(e -> openViewTeamsDialog(hackathon));
            buttonPanel.add(viewTeamsBtn);

            card.add(buttonPanel, BorderLayout.EAST);
        }

        parentPanel.add(card);
        parentPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * ✅ NUOVO: Crea card per team
     */
    private void addTeamCard(JPanel parentPanel, Team team, Hackathon hackathon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        infoPanel.setBackground(Color.WHITE);

        JLabel teamNameLabel = new JLabel("Team: " + team.getTeamName());
        teamNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoPanel.add(teamNameLabel);

        JLabel hackathonLabel = new JLabel("Hackathon: " + hackathon.getTitle());
        hackathonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(hackathonLabel);

        List<Participant> members = controller.getTeamMembers(team.getId());
        int memberCount = members != null ? members.size() : 0;
        JLabel membersLabel = new JLabel("Membri: " + memberCount + "/" + team.getMaxMembers());
        membersLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(membersLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton inviteBtn = new JButton("Invita Membro");
        inviteBtn.setBackground(new Color(60, 140, 220));
        inviteBtn.setForeground(Color.WHITE);
        inviteBtn.setFocusPainted(false);
        inviteBtn.addActionListener(e -> openInviteMemberDialog(team));
        buttonPanel.add(inviteBtn);

        card.add(buttonPanel, BorderLayout.EAST);

        parentPanel.add(card);
        parentPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * ✅ NUOVO: Crea card per richieste
     */
    private void addRequestCard(JPanel parentPanel, Request request) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        infoPanel.setBackground(Color.WHITE);

        JLabel senderLabel = new JLabel("Da: " + request.getSenderUserEmail());
        senderLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        infoPanel.add(senderLabel);

        JLabel messageLabel = new JLabel(request.getMessage());
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoPanel.add(messageLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton acceptBtn = new JButton("Accetta");
        acceptBtn.setBackground(new Color(40, 180, 60));
        acceptBtn.setForeground(Color.WHITE);
        acceptBtn.setFocusPainted(false);
        acceptBtn.addActionListener(e -> {
            controller.handleRequestResponse(request.getId(), true);
            refreshData();
        });
        buttonPanel.add(acceptBtn);

        JButton rejectBtn = new JButton("Rifiuta");
        rejectBtn.setBackground(new Color(220, 60, 60));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFocusPainted(false);
        rejectBtn.addActionListener(e -> {
            controller.handleRequestResponse(request.getId(), false);
            refreshData();
        });
        buttonPanel.add(rejectBtn);

        card.add(buttonPanel, BorderLayout.EAST);

        parentPanel.add(card);
        parentPanel.add(Box.createVerticalStrut(10));
    }

    // ========== DIALOG METHODS ==========

    private void openCreateHackathonDialog() {
        JOptionPane.showMessageDialog(this, "Dialog Crea Hackathon - Da implementare",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openCreateTeamDialog(Hackathon hackathon) {
        String teamName = JOptionPane.showInputDialog(this,
                "Nome del team per l'hackathon '" + hackathon.getTitle() + "':");

        if (teamName != null && !teamName.trim().isEmpty()) {
            String maxMembersStr = JOptionPane.showInputDialog(this,
                    "Numero massimo di membri (max " + hackathon.getMaxTeamSize() + "):");

            try {
                int maxMembers = Integer.parseInt(maxMembersStr);
                controller.handleCreateTeam(teamName.trim(), hackathon.getId(), maxMembers);
                refreshData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Numero non valido", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openViewTeamsDialog(Hackathon hackathon) {
        List<Team> teams = controller.getTeamsByHackathon(hackathon.getId());

        if (teams == null || teams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun team trovato per questo hackathon.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder teamsInfo = new StringBuilder("Team per " + hackathon.getTitle() + ":\n\n");
        for (Team team : teams) {
            List<Participant> members = controller.getTeamMembers(team.getId());
            int memberCount = members != null ? members.size() : 0;
            teamsInfo.append("• ").append(team.getTeamName())
                    .append(" (").append(memberCount).append("/").append(team.getMaxMembers()).append(" membri)\n");
        }

        JOptionPane.showMessageDialog(this, teamsInfo.toString(), "Team Disponibili", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openInviteMemberDialog(Team team) {
        String email = JOptionPane.showInputDialog(this, "Email dell'utente da invitare:");

        if (email != null && !email.trim().isEmpty()) {
            String message = JOptionPane.showInputDialog(this, "Messaggio motivazionale (opzionale):");
            controller.handleSendTeamInvitation(email.trim(), team.getId(), message != null ? message : "");
        }
    }
}