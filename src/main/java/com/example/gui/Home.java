package com.example.gui;

import com.example.controller.*;

import javax.swing.*;
import java.awt.*;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private Controller controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.setSize(800, 600);
        frameHome.setLocationRelativeTo(null);
        frameHome.setVisible(true);
    }

    public Home() {
        controller = new Controller(frameHome); // handle events
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // background color
        mainPanel.setBackground(new Color(245, 245, 245));

        // margin
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Login button action
        mainPanel.add(controller.buttonChangeFrame("Login", new Login().getPanel())); // change panel with
                                                                                      // Login panel

        mainPanel.add(Box.createVerticalStrut(20)); // Space between buttons

        // Sign Up button action
        mainPanel.add(controller.buttonChangeFrame("Sign Up", new Singup().getPanel())); // change panel
                                                                                         // with
        // Singup panel
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
