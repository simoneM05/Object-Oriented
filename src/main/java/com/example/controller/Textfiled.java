package com.example.controller;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Textfiled { // interface for structure textfiled
    JPanel fieldPanel;
    JLabel label;
    JTextField textField;

    public Textfiled(JLabel label, JTextField textField, JPanel fieldPanel) {
        this.fieldPanel = fieldPanel;
        this.label = label;
        this.textField = textField;
    }
}