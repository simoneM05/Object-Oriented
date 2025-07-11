package com.example.gui;

import javax.swing.*;

import com.example.model.User;

import java.awt.*;

public class JudgeDashboard extends JPanel {

    private JList<String> projectList;
    private DefaultListModel<String> projectListModel;
    private JTextArea projectDetailsArea;
    private JSlider scoreSlider;
    private JButton submitScoreButton;
    private JButton reportProblemButton;
    private String email;

    public JudgeDashboard() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        addListeners();
        loadProjects(); // Dummy data
    }

    private void initComponents() {
        // Left Panel for Project List
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Projects"));
        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane projectListScrollPane = new JScrollPane(projectList);
        leftPanel.add(projectListScrollPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Center Panel for Project Details and Actions
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Project Details & Actions"));

        projectDetailsArea = new JTextArea();
        projectDetailsArea.setEditable(false);
        projectDetailsArea.setLineWrap(true);
        projectDetailsArea.setWrapStyleWord(true);
        JScrollPane detailsScrollPane = new JScrollPane(projectDetailsArea);
        centerPanel.add(detailsScrollPane, BorderLayout.CENTER);

        // Scoring Panel
        JPanel scoringPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        scoringPanel.setBorder(BorderFactory.createTitledBorder("Assign Score (0-100)"));
        scoreSlider = new JSlider(0, 100, 50);
        scoreSlider.setMajorTickSpacing(10);
        scoreSlider.setMinorTickSpacing(5);
        scoreSlider.setPaintTicks(true);
        scoreSlider.setPaintLabels(true);
        submitScoreButton = new JButton("Submit Score");
        scoringPanel.add(scoreSlider);
        scoringPanel.add(submitScoreButton);
        centerPanel.add(scoringPanel, BorderLayout.NORTH);

        // Problem Reporting Panel
        JPanel problemPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        reportProblemButton = new JButton("Report Problem");
        problemPanel.add(reportProblemButton);
        centerPanel.add(problemPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayProjectDetails(projectList.getSelectedValue());
            }
        });

        submitScoreButton.addActionListener(e -> submitScore());
        reportProblemButton.addActionListener(e -> reportProblem());
    }

    private void loadProjects() {
        projectListModel.addElement("Project Alpha - Team A");
        projectListModel.addElement("Project Beta - Team B");
        projectListModel.addElement("Project Gamma - Team C");
        projectListModel.addElement("Project Delta - Team D");

        if (!projectListModel.isEmpty()) {
            projectList.setSelectedIndex(0);
        }
    }

    private void displayProjectDetails(String projectName) {
        if (projectName == null) {
            projectDetailsArea.setText("");
            return;
        }

        projectDetailsArea.setText("Details for " + projectName + ":\n\n"
                + "This is a sample description for " + projectName + ".\n"
                + "It aims to solve a particular problem using innovative technologies.\n"
                + "Key features include: Feature 1, Feature 2, Feature 3.\n"
                + "Technologies used: Java, Python, AWS, etc.");
    }

    private void submitScore() {
        String selectedProject = projectList.getSelectedValue();
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(this, "Please select a project to score.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int score = scoreSlider.getValue();
        System.out.println("Submitted score " + score + " for project: " + selectedProject);
        JOptionPane.showMessageDialog(this, "Score " + score + " submitted for " + selectedProject, "Score Submitted",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void reportProblem() {
        String selectedProject = projectList.getSelectedValue();
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(this, "Please select a project to report a problem for.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String problem = JOptionPane.showInputDialog(this, "Describe the problem for " + selectedProject + ":",
                "Report Problem", JOptionPane.QUESTION_MESSAGE);
        if (problem != null && !problem.trim().isEmpty()) {
            System.out.println("Problem reported for project: " + selectedProject + "\nDescription: " + problem);
            JOptionPane.showMessageDialog(this, "Problem reported for " + selectedProject + ":\n" + problem,
                    "Problem Reported", JOptionPane.INFORMATION_MESSAGE);
        } else if (problem != null) {
            JOptionPane.showMessageDialog(this, "Problem description cannot be empty.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadUserData(User user) {
        if (user != null) {
            // Per esempio, prendi la mail:

            this.email = user.getEmail();
        }

    }
}
