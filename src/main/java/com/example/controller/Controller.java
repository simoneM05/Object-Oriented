package com.example.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.example.gui.Home;
import com.example.gui.Singup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
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

    // *TEXTFIELD CREATE */
    public JPanel createTextField(String labelString) { // return Jpanel for return all Jcomponent
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // set layout for position panel
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); // set background transparent

        JLabel label = new JLabel(labelString);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(400, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textField);

        Date.add(textField); // store textfield

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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40)); // max size for botton
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        button.setFocusPainted(false); // remove bottom border for text
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set margin botton
        button.addActionListener(e -> {
            changePanel(NewPanel);
            main = !main;
        }); // add action for botton

        return button;

    }

    public JButton buttonBack() {
        JButton button = new JButton("Back");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40)); // max size for botton
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        button.setFocusPainted(false); // remove bottom border for text
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set margin botton
        button.addActionListener(e -> {
            changePanel(new Home().getMainPanel());
            main = !main;
        }); // add action for botton

        return button;

    }

    // * used for frame
    private void changePanel(JPanel NewPanel) { // remove frame
        frame.getContentPane().removeAll();
        if (main)
            frame.add(buttonBack());
        frame.getContentPane().add(NewPanel);
        reloadFrame();
    }

    private void reloadFrame() {
        frame.revalidate();
        frame.repaint();
    }

}
