package ems.gui;

import ems.model.Department;
import ems.model.EmployeeRecord;
import ems.service.DataPersistenceException;
import ems.service.EmployeeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * View section listing employees and their calculated monthly salary.
 * Supports selecting a row, searching by ID or by department (case
 * insensitive in the service), and deleting a record; all data access goes
 * through the employee service.
 */
public class ViewEmployeesPanel extends JPanel {

    private static final String SEARCH_BY_ID = "Employee ID";
    private static final String SEARCH_BY_DEPARTMENT = "Department";
    private static final String DEPARTMENT_PROMPT = "-- Select Department --";

    private final EmployeeService employeeService;
    private final EmployeeTable employeeTable = new EmployeeTable();
    private final JComboBox<String> searchCriteriaComboBox =
            new JComboBox<>(new String[]{SEARCH_BY_ID, SEARCH_BY_DEPARTMENT});
    private final JTextField idSearchField = new JTextField(15);
    private final JComboBox<String> departmentSearchComboBox = new JComboBox<>();

    public ViewEmployeesPanel(EmployeeService employeeService) {
        this.employeeService = employeeService;

        refreshDepartments();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildSearchPanel(), BorderLayout.NORTH);
        add(employeeTable, BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        updateSearchInput();
        refreshTable();
    }

    private JPanel buildSearchPanel() {
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> search());

        JButton showAllButton = new JButton("Show All");
        showAllButton.addActionListener(e -> refreshTable());

        searchCriteriaComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSearchInput();
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Search by:"));
        panel.add(searchCriteriaComboBox);
        panel.add(idSearchField);
        panel.add(departmentSearchComboBox);
        panel.add(searchButton);
        panel.add(showAllButton);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(e -> deleteEmployee());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(deleteButton);
        return panel;
    }

    /** Shows the input matching the chosen search criterion. */
    private void updateSearchInput() {
        boolean byId = SEARCH_BY_ID.equals(searchCriteriaComboBox.getSelectedItem());
        idSearchField.setVisible(byId);
        departmentSearchComboBox.setVisible(!byId);
        revalidate();
        repaint();
    }

    public void refreshTable() {
        employeeTable.setEmployees(employeeService.getEmployees());
    }

    /** Rebuilds the department search dropdown from the service, keeping the selection if possible. */
    public void refreshDepartments() {
        DepartmentCombo.refresh(departmentSearchComboBox, DEPARTMENT_PROMPT,
                employeeService.getDepartments());
    }

    private void search() {
        String query;
        List<EmployeeRecord> results;

        if (SEARCH_BY_ID.equals(searchCriteriaComboBox.getSelectedItem())) {
            query = idSearchField.getText().trim();
            if (query.isEmpty()) {
                showError("Please enter an employee ID to search for.");
                return;
            }
            Integer employeeId = parseInteger(query);
            if (employeeId == null) {
                showError("Employee ID must be a whole number.");
                return;
            }
            results = employeeService.searchById(employeeId);
        } else {
            Object selectedDepartment = departmentSearchComboBox.getSelectedItem();
            if (selectedDepartment == null || DEPARTMENT_PROMPT.equals(selectedDepartment)) {
                showError("Please select a department to search for.");
                return;
            }
            query = selectedDepartment.toString();
            results = employeeService.searchByDepartment(query);
        }

        employeeTable.setEmployees(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No employees found matching \"" + query + "\".",
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteEmployee() {
        EmployeeRecord selected = employeeTable.getSelectedEmployee();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an employee to delete.",
                    "Delete Employee",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete employee " + selected.getEmployeeId() + " (" + selected.getFullName() + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            employeeService.deleteEmployee(selected.getEmployeeId());
        } catch (DataPersistenceException e) {
            JOptionPane.showMessageDialog(this, "Could not save data: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        refreshTable();
    }

    private Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
