package com.example.controller;

import javax.swing.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<JTextField> Date = new ArrayList<>();

    public ActionListener actionSubmit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add action
                for (JTextField textField : Date) { // print date insert in text field
                    String text = textField.getText();
                    System.out.println("Dati inseriti: " + text); // Stampa i dati
                }
            }
        };
    }

    public JPanel createTextField(String labelString) { // return Jpanel for return all Jcomponent
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS)); // set layout for position panel
        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldPanel.setOpaque(false); // set background transparent

        JLabel label = new JLabel(labelString);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldPanel.add(label);

        JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(400, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldPanel.add(textField);

        Date.add(textField); // store textfield

        return fieldPanel;
    }

    public JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40)); // max size for botton
        button.setBackground(new Color(70, 130, 180)); // background color
        button.setForeground(Color.WHITE); // text color
        button.setFocusPainted(false); // remove bottom border for text
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set margin botton
        button.addActionListener(actionSubmit()); // add action for botton

        return button;

    }
}
