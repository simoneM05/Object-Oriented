package com.example.gui;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import com.example.controller.*;

public class Singup {
    private JPanel loginPanel;
    private Controller controller;

    public Singup() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Background color
        loginPanel.setBackground(new Color(245, 245, 245));
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}
