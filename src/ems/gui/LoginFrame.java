package ems.gui;

import ems.service.AuthService;
import ems.service.EmployeeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Login window. Collects credentials, delegates validation to the
 * {@link AuthService}, and opens the main window on success.
 */
public class LoginFrame extends JFrame {

    private static final int FIELD_COLUMNS = 15;

    private final AuthService authService;
    private final EmployeeService employeeService;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame(AuthService authService, EmployeeService employeeService) {
        super("Employee Management System - Login");
        this.authService = authService;
        this.employeeService = employeeService;

        usernameField = new JTextField(FIELD_COLUMNS);
        passwordField = new JPasswordField(FIELD_COLUMNS);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> performLogin());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        add(buildFormPanel(), BorderLayout.CENTER);
        add(buildButtonPanel(loginButton, exitButton), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("Username:"), gc);
        gc.gridx = 1;
        panel.add(usernameField, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("Password:"), gc);
        gc.gridx = 1;
        panel.add(passwordField, gc);

        return panel;
    }

    private JPanel buildButtonPanel(JButton loginButton, JButton exitButton) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(loginButton);
        panel.add(exitButton);
        return panel;
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both a username and a password.",
                    "Missing Credentials",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (authService.authenticate(username, password)) {
            new MainFrame(username, authService, employeeService).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password. Please try again.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocusInWindow();
        }
    }
}
