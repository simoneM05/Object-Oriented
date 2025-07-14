package com.example.gui;

import javax.swing.*;

import com.example.controller.ControllerGui;
import com.example.model.Document;
import com.example.model.Judge;
import com.example.model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JudgeDashboard extends JPanel {

    private String email;
    private JTextArea challengeTextArea;
    private JTextField challengeTitleField;
    private JButton submitChallengeButton;
    private JPanel submissionsPanel;
    private JScrollPane submissionsScrollPane;
    ControllerGui controllerGui = new ControllerGui();
    Judge judge;

    public JudgeDashboard() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
        addListeners();
    }

    private void initComponents() {
        // Pannello sinistro per l'inserimento della sfida
        JPanel challengePanel = createChallengePanel();
        add(challengePanel, BorderLayout.WEST);

        // Pannello destro per le submission dei team
        JPanel submissionsMainPanel = createSubmissionsPanel();
        add(submissionsMainPanel, BorderLayout.CENTER);
    }

    private JPanel createChallengePanel() {
        JPanel challengePanel = new JPanel(new BorderLayout(10, 10));
        challengePanel.setBorder(BorderFactory.createTitledBorder("Crea Nuova Sfida Hackathon"));
        challengePanel.setPreferredSize(new Dimension(400, 0));

        // Panel per titolo della sfida
        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        JLabel titleLabel = new JLabel("Titolo della Sfida:");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        challengeTitleField = new JTextField();
        challengeTitleField.setPreferredSize(new Dimension(0, 30));
        challengeTitleField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(challengeTitleField, BorderLayout.CENTER);
        challengePanel.add(titlePanel, BorderLayout.NORTH);

        // Area di testo per la descrizione della sfida
        JPanel descriptionPanel = new JPanel(new BorderLayout(5, 5));
        JLabel descLabel = new JLabel("Descrizione della Sfida:");
        descLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        challengeTextArea = new JTextArea(20, 30);
        challengeTextArea.setLineWrap(true);
        challengeTextArea.setWrapStyleWord(true);
        challengeTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        challengeTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane challengeScrollPane = new JScrollPane(challengeTextArea);
        challengeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionPanel.add(descLabel, BorderLayout.NORTH);
        descriptionPanel.add(challengeScrollPane, BorderLayout.CENTER);
        challengePanel.add(descriptionPanel, BorderLayout.CENTER);

        // Bottone submit
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitChallengeButton = new JButton("Pubblica Sfida");
        submitChallengeButton.setPreferredSize(new Dimension(140, 35));
        submitChallengeButton.setBackground(new Color(0, 123, 255));
        submitChallengeButton.setForeground(Color.WHITE);
        submitChallengeButton.setFocusPainted(false);
        submitChallengeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        buttonPanel.add(submitChallengeButton);
        challengePanel.add(buttonPanel, BorderLayout.SOUTH);

        return challengePanel;
    }

    private JPanel createSubmissionsPanel() {
        JPanel submissionsMainPanel = new JPanel(new BorderLayout());
        submissionsMainPanel.setBorder(BorderFactory.createTitledBorder("Submission dei Team"));

        // Panel container per le submission con GridBagLayout
        submissionsPanel = new JPanel(new GridBagLayout());
        submissionsPanel.setBackground(new Color(248, 249, 250));

        // ScrollPane per le submission
        submissionsScrollPane = new JScrollPane(submissionsPanel);
        submissionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        submissionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        submissionsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        submissionsMainPanel.add(submissionsScrollPane, BorderLayout.CENTER);

        return submissionsMainPanel;
    }

    private void loadDummySubmissions() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        this.judge = controllerGui.getJudgeByEmail(email);

        if (judge == null) {
            JLabel noJudgeLabel = new JLabel("Nessun giudice trovato. Assicurati di essere loggato.");
            noJudgeLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
            noJudgeLabel.setForeground(new Color(108, 117, 125));
            submissionsPanel.add(noJudgeLabel);
            return;
        }
        List<Document> documents = controllerGui.getDocumentsByHackathonId(judge.getHackathonId());
        if (documents.isEmpty()) {
            JLabel noSubmissionsLabel = new JLabel("Nessuna submission disponibile.");
            noSubmissionsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
            noSubmissionsLabel.setForeground(new Color(108, 117, 125));
            submissionsPanel.add(noSubmissionsLabel);
            return;
        }

        int i = 0;
        for (Document document : documents) {
            String teamName = controllerGui.getTeamById(document.getTeamId()).getName();
            String submissionTime = new SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .format(Date.from(document.getUploadDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
            gbc.gridy = submissionsPanel.getComponentCount();
            JPanel submissionCard = createSubmissionCard(teamName, submissionTime, document.getFileName());
            submissionsPanel.add(submissionCard, gbc);

            // Spacer per spingere le card verso l'alto
            gbc.gridy = documents.size();
            gbc.weighty = 1.0;
            submissionsPanel.add(Box.createVerticalGlue(), gbc);
            i++;
        }

    }

    private JPanel createSubmissionCard(String teamName, String submissionTime, String documentName) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 200));

        // Panel sinistro con info team
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Aggiunta ordinata
        JLabel teamLabel = new JLabel("Team name:" + teamName);
        teamLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        teamLabel.setForeground(new Color(33, 37, 41));
        leftPanel.add(teamLabel);

        JLabel documentNameLabel = new JLabel("Document name:" + documentName);
        documentNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        documentNameLabel.setForeground(new Color(33, 37, 41));
        leftPanel.add(documentNameLabel);

        JLabel timeLabel = new JLabel("Inviato il: " + submissionTime);
        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        timeLabel.setForeground(new Color(108, 117, 125));
        leftPanel.add(timeLabel);

        card.add(leftPanel, BorderLayout.CENTER);

        // Panel destro per il voto
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(Color.WHITE);

        // Panel per organizzare voto verticalmente
        JPanel votePanel = new JPanel(new BorderLayout(5, 5));
        votePanel.setBackground(Color.WHITE);

        JLabel voteLabel = new JLabel("Voto:");
        voteLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        voteLabel.setHorizontalAlignment(JLabel.CENTER);
        votePanel.add(voteLabel, BorderLayout.NORTH);

        // Spinner per il voto da 1 a 10
        JSpinner voteSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        voteSpinner.setPreferredSize(new Dimension(60, 30));
        voteSpinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        votePanel.add(voteSpinner, BorderLayout.CENTER);

        // Bottone per confermare il voto
        JButton voteButton = new JButton("Invia Voto");
        voteButton.setPreferredSize(new Dimension(80, 25));
        voteButton.setBackground(new Color(40, 167, 69));
        voteButton.setForeground(Color.WHITE);
        voteButton.setFocusPainted(false);
        voteButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        votePanel.add(voteButton, BorderLayout.SOUTH);

        rightPanel.add(votePanel);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private void addListeners() {
        // Listener per il bottone di submit della sfida (demo - non implementato)
        submitChallengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica per pubblicare la sfida
                if (challengeTitleField.getText().isEmpty() || challengeTextArea.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(JudgeDashboard.this, "Compila tutti i campi!", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Logica per pubblicare la sfida
                judge.setProblem(challengeTextArea.getText());
                JOptionPane.showMessageDialog(JudgeDashboard.this, "sfida pubblicata con successo!", "successo",
                        JOptionPane.INFORMATION_MESSAGE);

                String title = challengeTitleField.getText();
                String description = challengeTextArea.getText();
                System.out.println("Sfida pubblicata:");
                System.out.println("Titolo: " + title);
                System.out.println("Descrizione: " + description);
            }
        });
    }

    public void loadUserData(User user) {
        if (user != null) {
            this.email = user.getEmail();
            loadDummySubmissions();
        }
    }
}