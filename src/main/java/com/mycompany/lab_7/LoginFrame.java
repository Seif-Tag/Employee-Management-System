package com.mycompany.lab_7;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;

    public LoginFrame() {

        setTitle("Employee Management System");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(4, 2, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");

        add(new JLabel("Username:"));
        add(usernameField);

        add(new JLabel("Password:"));
        add(passwordField);

        add(loginButton);
        add(exitButton);

        loginButton.addActionListener(e -> login());

        exitButton.addActionListener(e -> System.exit(0));
    }

    private void login() {

        String username = usernameField.getText();
        String password =
                new String(passwordField.getPassword());

        if (username.equals("admin") &&
            password.equals("admin123")) {

            JOptionPane.showMessageDialog(this,
                    "Login Successful!");

            new Dashboard().setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password!");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
