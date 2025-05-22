package com.example.gui;

import com.example.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends JPanel {
    private Controller controller;
    private JPanel invitationsListPanel; // Pannello per visualizzare gli inviti ricevuti
    private JPanel subscribedHackathonsPanel; // Nuovo pannello per gli hackathon iscritti

    public HomePage(Controller controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(20, 20)); // Usa BorderLayout per la struttura generale
        setBackground(new Color(240, 240, 240)); // Sfondo grigio chiaro

        // Pannello superiore per Titolo e Pulsante Logout
        JPanel topPanel = new JPanel(new BorderLayout()); // Usa BorderLayout per il pannello superiore principale
        topPanel.setBackground(new Color(220, 220, 220)); // Grigio leggermente più scuro
        topPanel.setBorder(new EmptyBorder(15, 20, 15, 20)); // Padding generale per il pannello superiore

        // Contenitore per il pulsante Logout (allineato a destra)
        JPanel logoutButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Nessun gap
                                                                                           // orizzontale/verticale
        logoutButtonContainer.setBackground(new Color(220, 220, 220)); // Abbina lo sfondo del pannello padre

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutButton.setBackground(new Color(220, 80, 80)); // Rosso per il logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new EmptyBorder(8, 15, 8, 15));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursore a mano sul pulsante
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLogout(); // Chiama il controller per gestire il logout
            }
        });
        logoutButtonContainer.add(logoutButton);
        topPanel.add(logoutButtonContainer, BorderLayout.NORTH); // Posiziona il contenitore del logout in alto

        JLabel titleLabel = new JLabel("Gestisci i tuoi Hackathon e Inviti"); // Testo modificato
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Titolo leggermente più grande
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra il titolo orizzontalmente
        topPanel.add(titleLabel, BorderLayout.CENTER); // Posiziona il titolo al centro del pannello superiore

        add(topPanel, BorderLayout.NORTH);

        // JTabbedPane per organizzare le diverse sezioni
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 17)); // Font leggermente più grande per le schede

        // --- Tab 1: I miei Hackathon (Funzionalità esistente con pulsante Iscriviti)
        // ---
        JPanel hackathonsPanel = new JPanel();
        hackathonsPanel.setLayout(new BoxLayout(hackathonsPanel, BoxLayout.Y_AXIS)); // Impila gli hackathon
                                                                                     // verticalmente
        hackathonsPanel.setBackground(new Color(240, 240, 240));
        hackathonsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Aggiungi hackathon fittizi con pulsante Iscriviti
        addHackathonCardWithSubscribe(hackathonsPanel, "Hackathon Innovazione Digitale", "20-22 Settembre 2024",
                "Un evento di 48 ore per sviluppare soluzioni innovative nel campo della trasformazione digitale. Focus su AI e Blockchain.");
        addHackathonCardWithSubscribe(hackathonsPanel, "GreenTech Challenge", "10-12 Ottobre 2024",
                "Crea progetti tecnologici per affrontare le sfide ambientali. Dalla gestione dei rifiuti all'energia rinnovabile.");
        addHackathonCardWithSubscribe(hackathonsPanel, "HealthTech Sprint", "05-07 Novembre 2024",
                "Sviluppa applicazioni e dispositivi per migliorare la salute e il benessere. Big data e telemedicina.");
        addHackathonCardWithSubscribe(hackathonsPanel, "Smart City Hack", "01-03 Dicembre 2024",
                "Idee e prototipi per rendere le nostre città più intelligenti e sostenibili. Trasporti, sicurezza e connettività.");

        JScrollPane hackathonsScrollPane = new JScrollPane(hackathonsPanel);
        hackathonsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        hackathonsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("Hackathon List", hackathonsScrollPane);

        // --- Tab 2: Hackathon Iscritto (NUOVA SEZIONE) ---
        JPanel subscribedHackathonsContainer = new JPanel(new BorderLayout());
        subscribedHackathonsContainer.setBackground(new Color(240, 240, 240));
        subscribedHackathonsContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel subscribedHackathonsTitle = new JLabel("Hackathon Iscritto");
        subscribedHackathonsTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        subscribedHackathonsTitle.setForeground(new Color(50, 50, 50));
        subscribedHackathonsContainer.add(subscribedHackathonsTitle, BorderLayout.NORTH);

        subscribedHackathonsPanel = new JPanel();
        subscribedHackathonsPanel.setLayout(new BoxLayout(subscribedHackathonsPanel, BoxLayout.Y_AXIS));
        subscribedHackathonsPanel.setBackground(new Color(240, 240, 240));
        JScrollPane subscribedHackathonsScrollPane = new JScrollPane(subscribedHackathonsPanel);
        subscribedHackathonsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        subscribedHackathonsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        subscribedHackathonsContainer.add(subscribedHackathonsScrollPane, BorderLayout.CENTER);

        // --- Tab 3: I miei Inviti ---
        JPanel invitationsPanel = new JPanel(new BorderLayout());
        invitationsPanel.setBackground(new Color(240, 240, 240));
        invitationsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel invitationsTitle = new JLabel("Inviti Ricevuti");
        invitationsTitle.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Titolo leggermente più grande
        invitationsTitle.setForeground(new Color(50, 50, 50));
        invitationsPanel.add(invitationsTitle, BorderLayout.NORTH);

        invitationsListPanel = new JPanel();
        invitationsListPanel.setLayout(new BoxLayout(invitationsListPanel, BoxLayout.Y_AXIS));
        invitationsListPanel.setBackground(new Color(240, 240, 240));
        JScrollPane invitationsScrollPane = new JScrollPane(invitationsListPanel);
        invitationsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        invitationsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        invitationsPanel.add(invitationsScrollPane, BorderLayout.CENTER);

        // Simula alcuni inviti
        displayFictitiousInvitations();

        tabbedPane.addTab("I miei Inviti", invitationsPanel);

        // --- Tab 4: Invia Invito (Lista di utenti con pulsanti) ---
        JPanel sendInvitationPanel = new JPanel(new BorderLayout());
        sendInvitationPanel.setBackground(new Color(240, 240, 240));
        sendInvitationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel sendInvitationTitle = new JLabel("Invia un Nuovo Invito");
        sendInvitationTitle.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Titolo leggermente più grande
        sendInvitationTitle.setForeground(new Color(50, 50, 50));
        sendInvitationTitle.setHorizontalAlignment(SwingConstants.CENTER); // Centra il titolo
        sendInvitationPanel.add(sendInvitationTitle, BorderLayout.NORTH);

        JPanel usersListContainer = new JPanel();
        usersListContainer.setLayout(new BoxLayout(usersListContainer, BoxLayout.Y_AXIS));
        usersListContainer.setBackground(new Color(240, 240, 240));
        usersListContainer.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding per la lista

        // Lista fittizia di utenti
        String[] fictitiousUsers = { "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi" };

        if (fictitiousUsers.length == 0) {
            JLabel noUsersLabel = new JLabel("Nessun utente disponibile per l'invio di inviti.");
            noUsersLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noUsersLabel.setForeground(new Color(120, 120, 120));
            usersListContainer.add(noUsersLabel);
        } else {
            for (String user : fictitiousUsers) {
                JPanel userCard = new JPanel(new BorderLayout(15, 0)); // Aumentato il gap orizzontale
                userCard.setBackground(Color.WHITE);
                userCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(200, 200, 200), 1),
                        new EmptyBorder(12, 20, 12, 20) // Padding interno leggermente aumentato
                ));
                userCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                userCard.setMaximumSize(new Dimension(700, 70)); // Altezza fissa per le card utente

                JLabel userNameLabel = new JLabel(user);
                userNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                userNameLabel.setForeground(new Color(80, 80, 80));
                userCard.add(userNameLabel, BorderLayout.WEST);

                JButton sendButton = new JButton("Invia Invito");
                sendButton.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Font leggermente più grande
                sendButton.setBackground(new Color(80, 180, 120)); // Verde per inviare
                sendButton.setForeground(Color.WHITE);
                sendButton.setFocusPainted(false);
                sendButton.setBorder(new EmptyBorder(7, 15, 7, 15)); // Padding per il pulsante
                sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursore a mano sul pulsante
                // Usa lambda per catturare la variabile 'user' per l'action listener
                sendButton.addActionListener(e -> controller.handleSendInvitation(user));
                userCard.add(sendButton, BorderLayout.EAST);

                usersListContainer.add(userCard);
                usersListContainer.add(Box.createRigidArea(new Dimension(0, 12))); // Spazio tra le card utente
            }
        }

        JScrollPane usersScrollPane = new JScrollPane(usersListContainer);
        usersScrollPane.setBorder(BorderFactory.createEmptyBorder());
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        sendInvitationPanel.add(usersScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Invia Invito", sendInvitationPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Helper method to create and add a hackathon card to the content panel.
     * This version includes a subscribe button.
     */
    private void addHackathonCardWithSubscribe(JPanel parentPanel, String name, String date, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0)); // Use BorderLayout for name/desc and buttons
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1), // Subtle border
                new EmptyBorder(15, 20, 15, 20) // Internal padding
        ));
        card.setAlignmentX(Component.CENTER_ALIGNMENT); // Center cards horizontally
        card.setMaximumSize(new Dimension(700, Short.MAX_VALUE)); // Max width for cards

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(new Color(60, 60, 60));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer

        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setForeground(new Color(100, 100, 100));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(dateLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descriptionArea.setForeground(new Color(80, 80, 80));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(descriptionArea);

        card.add(textPanel, BorderLayout.CENTER);

        JButton subscribeButton = new JButton("Iscriviti");
        subscribeButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        subscribeButton.setBackground(new Color(60, 140, 220)); // Blue for subscribe
        subscribeButton.setForeground(Color.WHITE);
        subscribeButton.setFocusPainted(false);
        subscribeButton.setBorder(new EmptyBorder(7, 15, 7, 15));
        subscribeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        subscribeButton.addActionListener(e -> controller.handleSubscribeHackathon(name)); // Call controller to
                                                                                           // subscribe
        card.add(subscribeButton, BorderLayout.EAST);

        parentPanel.add(card);
        parentPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Space between cards
    }

    /**
     * Simula la visualizzazione di inviti fittizi. In un'app reale, questo
     * recupererebbe da una fonte dati.
     */
    private void displayFictitiousInvitations() {
        invitationsListPanel.removeAll(); // Cancella gli inviti precedenti

        List<String[]> fictitiousInvitations = new ArrayList<>();
        fictitiousInvitations.add(new String[] { "Alice", "per Hackathon AI Futuristico" });
        fictitiousInvitations.add(new String[] { "Bob", "per GreenTech Summit" });
        fictitiousInvitations.add(new String[] { "Charlie", "per Health Innovation Sprint" });

        if (fictitiousInvitations.isEmpty()) {
            JLabel noInvitationsLabel = new JLabel("Nessun invito ricevuto al momento.");
            noInvitationsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noInvitationsLabel.setForeground(new Color(120, 120, 120));
            invitationsListPanel.add(noInvitationsLabel);
        } else {
            for (String[] invitation : fictitiousInvitations) {
                JPanel invitationCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                invitationCard.setBackground(Color.WHITE);
                invitationCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(200, 200, 200), 1),
                        new EmptyBorder(10, 15, 10, 15)));
                invitationCard.setMaximumSize(new Dimension(700, 70)); // Altezza fissa per le card invito

                JLabel invitationText = new JLabel("Invito da " + invitation[0] + " " + invitation[1]);
                invitationText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                invitationText.setForeground(new Color(80, 80, 80));
                invitationCard.add(invitationText);

                JButton acceptButton = new JButton("Accetta");
                acceptButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                acceptButton.setBackground(new Color(60, 180, 120));
                acceptButton.setForeground(Color.WHITE);
                acceptButton.setFocusPainted(false);
                acceptButton.setBorder(new EmptyBorder(5, 10, 5, 10));
                acceptButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursore a mano sul pulsante
                acceptButton.addActionListener(
                        e -> JOptionPane.showMessageDialog(this, "Invito da " + invitation[0] + " accettato!",
                                "Invito Accettato", JOptionPane.INFORMATION_MESSAGE));
                invitationCard.add(acceptButton);

                JButton declineButton = new JButton("Rifiuta");
                declineButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                declineButton.setBackground(new Color(220, 80, 80));
                declineButton.setForeground(Color.WHITE);
                declineButton.setFocusPainted(false);
                declineButton.setBorder(new EmptyBorder(5, 10, 5, 10));
                declineButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursore a mano sul pulsante
                declineButton.addActionListener(
                        e -> JOptionPane.showMessageDialog(this, "Invito da " + invitation[0] + " rifiutato.",
                                "Invito Rifiutato", JOptionPane.INFORMATION_MESSAGE));
                invitationCard.add(declineButton);

                invitationsListPanel.add(invitationCard);
                invitationsListPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spazio tra le card invito
            }
        }
        invitationsListPanel.revalidate();
        invitationsListPanel.repaint();
    }
}
