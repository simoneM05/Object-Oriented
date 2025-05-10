package com.example.controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.example.gui.Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<JTextField> Date = new ArrayList<>();

    private JFrame frame;
    private boolean main = true; // if main panel is loaded, main = true, else main = false

    public Controller(JFrame frame) {
        this.frame = frame;
    }

    public Controller() {
    }

    // *TEXTFIELD CREATE */
    public JPanel createTextField(String labelString) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // set layout for position panel
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); // set background transparent

        JLabel label = new JLabel(labelString);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setColumns(20); // Set the number of columns for uniform width (20 columns is roughly equal to
                                  // 400px width)
        textField.setPreferredSize(new Dimension(200, 20)); // Set the preferred size with a fixed width and height
        textField.setMaximumSize(new Dimension(300, 30)); // Ensure maximum size matches preferred size
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set rounded borders with a larger radius
        Border roundedBorder = new LineBorder(Color.GRAY, 1, true); // Border color and thickness
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10); // Adjust padding inside textfield for
                                                                            // better space
        textField.setBorder(new CompoundBorder(roundedBorder, emptyBorder));

        panel.add(textField);

        // Store the textField if necessary
        Date.add(textField); // Store textField

        return panel;
    }

    public JPanel createPasswordField(String labelString) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // set layout for position panel
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); // set background transparent

        JLabel label = new JLabel(labelString);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 30)); // Set the preferred size (width x height)
        passwordField.setMaximumSize(new Dimension(300, 30)); // Ensure the size remains consistent
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set rounded borders with a larger radius
        int radius = 25; // Increase the radius for more rounded corners
        Border roundedBorder = new LineBorder(Color.GRAY, 1, true); // Border color and thickness
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10); // Adjust padding inside the password field
                                                                            // for better space
        passwordField.setBorder(new CompoundBorder(roundedBorder, emptyBorder));

        panel.add(passwordField);

        // Store the passwordField if necessary
        Date.add(passwordField); // Store passwordField

        return panel;
    }

    // *BUTTON CREATE */
    public JButton createButton(String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40)); // max size for botton
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        button.setFocusPainted(false); // remove bottom border for text
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set margin botton
        button.addActionListener(actionListener); // add action for botton

        return button;

    }

    // ?BUTTON CREATE
    // @overloading
    // used for change panel in frame
    public JButton buttonChangeFrame(String buttonText, JPanel NewPanel) {
        JButton button = new JButton(buttonText);
        // position
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        // size
        button.setMaximumSize(new Dimension(200, 40)); // max size for botton
        // color
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        // border
        button.setFocusPainted(false); // remove bottom border for text
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set margin botton
        // action
        button.addActionListener(e -> {
            changePanel(NewPanel);
            main = !main;
        }); // add action for botton

        return button;

    }

    public JButton buttonBack() {
        JButton button = new JButton("Back");
        button.setMaximumSize(new Dimension(50, 40)); // max size for botton
        // Coloro
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        // Border
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        button.setBorderPainted(false);
        button.setFocusPainted(false); // remove bottom border for text
        // Action
        button.addActionListener(e -> {
            changePanel(new Home().getMainPanel());
            main = !main;
        }); // add action for botton

        return button;

    }

    void topPanel(JPanel panel) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(buttonBack());
        topPanel.setOpaque(false);
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
    }

    // * used for frame
    private void changePanel(JPanel NewPanel) { // remove frame
        frame.getContentPane().removeAll();
        if (main)
            topPanel(NewPanel);
        frame.getContentPane().add(NewPanel, BorderLayout.CENTER);
        reloadFrame();
    }

    private void reloadFrame() {
        frame.revalidate();
        frame.repaint();
    }

}
