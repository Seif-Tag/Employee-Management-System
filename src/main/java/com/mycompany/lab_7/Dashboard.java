package com.mycompany.lab_7;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Employee Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel welcome = new JLabel(
                "Welcome to Employee Management System",
                SwingConstants.CENTER
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 20));

        add(welcome, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton addEmployee = new JButton("Add Employee");
        JButton viewEmployees = new JButton("View Employees");
        JButton searchEmployee = new JButton("Search Employee");
        JButton updateEmployee = new JButton("Update Employee");
        JButton deleteEmployee = new JButton("Delete Employee");
        JButton department = new JButton("Department Management");
        JButton logout = new JButton("Logout");

        buttonPanel.add(addEmployee);
        buttonPanel.add(viewEmployees);
        buttonPanel.add(searchEmployee);
        buttonPanel.add(updateEmployee);
        buttonPanel.add(deleteEmployee);
        buttonPanel.add(department);

        add(buttonPanel, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        addEmployee.addActionListener(e ->
                showMessage("Add Employee"));

        viewEmployees.addActionListener(e ->
                showMessage("View Employees"));

        searchEmployee.addActionListener(e ->
                showMessage("Search Employee"));

        updateEmployee.addActionListener(e ->
                showMessage("Update Employee"));

        deleteEmployee.addActionListener(e ->
                showMessage("Delete Employee"));

        department.addActionListener(e ->
                showMessage("Department Management"));

        logout.addActionListener(e -> {

            new LoginFrame().setVisible(true);

            dispose();
        });
    }

    private void showMessage(String operation) {

        JOptionPane.showMessageDialog(this,
                operation + " feature will be implemented.");
    }
}