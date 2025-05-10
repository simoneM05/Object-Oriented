package com.example.gui;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import com.example.controller.*;

public class Login {
    private JPanel loginPanel;
    private Controller controller;

    public Login() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Background color
        loginPanel.setBackground(new Color(245, 245, 245));

        // ! to fix
        String fields[] = { "Email", "Password" };
        for (String field : fields) {
            loginPanel.add(controller.createTextField(field));
        }
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}
