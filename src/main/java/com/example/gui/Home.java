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
        controller = new Controller(); // handle events
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // background color
        mainPanel.setBackground(new Color(245, 245, 245));

        // margin
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));

        String[] fields = { "First Name :", "Last Name :", "Email :", "Username :", "Password :" }; // fields for singup

        // singup interface

        for (String field : fields) {
            // textfield
            mainPanel.add(controller.createTextField(field));
            mainPanel.add(Box.createVerticalStrut(10)); // space textfield
        }

        mainPanel.add(Box.createVerticalStrut(20)); // Space Button
        mainPanel.add(controller.createButton("Submit"));

    }
}
