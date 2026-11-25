package ems.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Dashboard section shown after a successful login. Displays a welcome
 * message and the log out control.
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel(String username, Runnable logOutAction) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 26f));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(e -> logOutAction.run());
        add(logOutButton, BorderLayout.SOUTH);
    }
}
