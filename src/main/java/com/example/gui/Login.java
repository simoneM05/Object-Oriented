package com.example.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import com.example.controller.*;

public class Login {
    private JPanel loginPanel;
    private Controller controller;

    public Login() {
        controller = new Controller();
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Background color
        loginPanel.setBackground(new Color(245, 245, 245));

        String[] fields = { "Name", "Subname", "Username", "Email", "Password" };

        for (String field : fields) {
            if (field.equals("Password"))
                loginPanel.add(controller.createPasswordField(field));
            else
                loginPanel.add(controller.createTextField(field));
            loginPanel.add(Box.createVerticalStrut(10)); // Space between buttons

        }
        loginPanel.add(controller.createButton("Submit", null));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
    }

    public JPanel getPanel() {
        return loginPanel;
    }
}
