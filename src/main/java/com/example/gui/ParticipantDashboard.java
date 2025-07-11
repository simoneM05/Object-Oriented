package com.example.gui;

import javax.swing.*;

import com.example.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParticipantDashboard extends JPanel {

    private JLabel welcomeLabel;
    private JTabbedPane mainTabbedPane;
    private JList<String> joinedHackathonsList;
    private DefaultListModel<String> joinedHackathonsModel;
    private JList<String> availableHackathonsList;
    private DefaultListModel<String> availableHackathonsModel;
    private JButton joinHackathonButton;
    private JButton viewMyProjectButton;
    private String email;

    public ParticipantDashboard() {
        setLayout(new BorderLayout(10, 10));

        initComponents();
        addListeners();
        loadInitialData(); // Load some dummy data
    }

    private void initComponents() {
        welcomeLabel = new JLabel("Welcome, Participant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        mainTabbedPane = new JTabbedPane();

        // Panel for Joined Hackathons
        JPanel joinedHackathonsPanel = new JPanel(new BorderLayout());
        joinedHackathonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        joinedHackathonsModel = new DefaultListModel<>();
        joinedHackathonsList = new JList<>(joinedHackathonsModel);
        joinedHackathonsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane joinedScrollPane = new JScrollPane(joinedHackathonsList);
        joinedHackathonsPanel.add(new JLabel("Your Joined Hackathons:"), BorderLayout.NORTH);
        joinedHackathonsPanel.add(joinedScrollPane, BorderLayout.CENTER);
        viewMyProjectButton = new JButton("View My Project");
        JPanel joinedButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        joinedButtonPanel.add(viewMyProjectButton);
        joinedHackathonsPanel.add(joinedButtonPanel, BorderLayout.SOUTH);

        // Panel for Available Hackathons
        JPanel availableHackathonsPanel = new JPanel(new BorderLayout());
        availableHackathonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        availableHackathonsModel = new DefaultListModel<>();
        availableHackathonsList = new JList<>(availableHackathonsModel);
        availableHackathonsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane availableScrollPane = new JScrollPane(availableHackathonsList);
        availableHackathonsPanel.add(new JLabel("Available Hackathons:"), BorderLayout.NORTH);
        availableHackathonsPanel.add(availableScrollPane, BorderLayout.CENTER);
        joinHackathonButton = new JButton("Join Selected Hackathon");
        JPanel availableButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        availableButtonPanel.add(joinHackathonButton);
        availableHackathonsPanel.add(availableButtonPanel, BorderLayout.SOUTH);

        mainTabbedPane.addTab("My Hackathons", joinedHackathonsPanel);
        mainTabbedPane.addTab("Discover Hackathons", availableHackathonsPanel);

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
        // Dummy data for demonstration. In a real app, this would come from a database.
        joinedHackathonsModel.addElement("Spring Code Fest 2025 (Joined)");
        joinedHackathonsModel.addElement("AI Innovation Challenge (Joined)");

        availableHackathonsModel.addElement("Summer Dev Jam 2025");
        availableHackathonsModel.addElement("Data Science Hackathon");
        availableHackathonsModel.addElement("Mobile Appathon");
    }

    // Placeholder for joining a hackathon
    private void joinHackathon() {
        String selectedHackathon = availableHackathonsList.getSelectedValue();
        if (selectedHackathon == null) {
            JOptionPane.showMessageDialog(this, "Please select a hackathon to join.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Attempting to join: " + selectedHackathon);
        JOptionPane.showMessageDialog(this, "You have joined '" + selectedHackathon + "'!", "Success",
                JOptionPane.INFORMATION_MESSAGE);

        // Move from available to joined list for demonstration
        availableHackathonsModel.removeElement(selectedHackathon);
        joinedHackathonsModel.addElement(selectedHackathon.replace(" (Joined)", "") + " (Joined)");
    }

    // Placeholder for viewing participant's project
    private void viewMyProject() {
        String selectedJoinedHackathon = joinedHackathonsList.getSelectedValue();
        if (selectedJoinedHackathon == null) {
            JOptionPane.showMessageDialog(this, "Please select one of your joined hackathons to view its project.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Viewing project for: " + selectedJoinedHackathon);
        JOptionPane.showMessageDialog(this,
                "Displaying project details for " + selectedJoinedHackathon + ". (Logic to be implemented)",
                "View Project", JOptionPane.INFORMATION_MESSAGE);
    }

    public void loadUserData(User user) {
        if (user != null) {
            // Per esempio, prendi la mail:

            this.email = user.getEmail();
        }

    }
}
