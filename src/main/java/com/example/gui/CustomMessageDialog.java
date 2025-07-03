package com.example.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomMessageDialog extends JDialog {

    /**
     * Creates and displays a custom message dialog.
     *
     * @param parentComponent The parent component for the dialog.
     * @param message         The message to display.
     * @param title           The title of the dialog.
     * @param messageType     The type of message (e.g.,
     *                        JOptionPane.INFORMATION_MESSAGE,
     *                        JOptionPane.ERROR_MESSAGE).
     */
    public static void showMessage(Component parentComponent, String message, String title, int messageType) {
        CustomMessageDialog dialog = new CustomMessageDialog(parentComponent, title, message, messageType);
        dialog.setVisible(true);
    }

    private CustomMessageDialog(Component parentComponent, String title, String message, int messageType) {
        super(SwingUtilities.getWindowAncestor(parentComponent), title, ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Imposta una dimensione preferita più larga
        setPreferredSize(new Dimension(450, 130)); // Larghezza 450, Altezza 200

        // Main panel for content
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 245, 245)); // Light grey background
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Icon based on message type
        JLabel iconLabel = new JLabel();
        Icon icon = getIconForMessageType(messageType);
        if (icon != null) {
            iconLabel.setIcon(icon);
            panel.add(iconLabel, BorderLayout.WEST);
        }

        // Message label
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageArea.setForeground(new Color(60, 60, 60));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setBackground(new Color(245, 245, 245));
        messageArea.setBorder(null); // Remove default border
        panel.add(messageArea, BorderLayout.CENTER);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        okButton.setBackground(new Color(60, 140, 220)); // Blue color
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorder(new EmptyBorder(8, 20, 8, 20));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        pack(); // Adjusts dialog size to fit contents based on preferred size
        setLocationRelativeTo(SwingUtilities.getWindowAncestor(parentComponent)); // Center relative to parent
    }

    /**
     * Returns an appropriate icon based on the message type.
     */
    private Icon getIconForMessageType(int messageType) {
        switch (messageType) {
            case JOptionPane.INFORMATION_MESSAGE:
                return UIManager.getIcon("OptionPane.informationIcon");
            case JOptionPane.WARNING_MESSAGE:
                return UIManager.getIcon("OptionPane.warningIcon");
            case JOptionPane.ERROR_MESSAGE:
                return UIManager.getIcon("OptionPane.errorIcon");
            case JOptionPane.QUESTION_MESSAGE:
                return UIManager.getIcon("OptionPane.questionIcon");
            default:
                return null;
        }
    }
}
