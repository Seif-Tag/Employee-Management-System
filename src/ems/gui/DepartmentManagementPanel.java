package ems.gui;

import ems.model.Department;
import ems.service.EmployeeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Department management section: lists departments with their ID and name
 * and adds new ones. All validation and storage go through the employee
 * service; {@code onDepartmentsChanged} lets the main window sync the
 * department dropdowns in the other sections.
 */
public class DepartmentManagementPanel extends JPanel {

    private static final String[] COLUMNS = {"Department ID", "Department Name"};

    private final EmployeeService employeeService;
    private final Runnable onDepartmentsChanged;

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    private final JTextField departmentIdField = new JTextField(12);
    private final JTextField departmentNameField = new JTextField(12);

    public DepartmentManagementPanel(EmployeeService employeeService, Runnable onDepartmentsChanged) {
        this.employeeService = employeeService;
        this.onDepartmentsChanged = onDepartmentsChanged;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel buildFormPanel() {
        JButton addButton = new JButton("Add Department");
        addButton.addActionListener(e -> addDepartment());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Department ID:"));
        panel.add(departmentIdField);
        panel.add(new JLabel("Department Name:"));
        panel.add(departmentNameField);
        panel.add(addButton);
        return panel;
    }

    private void addDepartment() {
        String departmentId = departmentIdField.getText().trim();
        String departmentName = departmentNameField.getText().trim();

        if (departmentId.isEmpty()) {
            showError("Department ID is required.");
            return;
        }
        if (departmentName.isEmpty()) {
            showError("Department name is required.");
            return;
        }
        if (employeeService.isDepartmentIdTaken(departmentId)) {
            showError("Department ID \"" + departmentId + "\" is already in use.");
            return;
        }
        if (employeeService.isDepartmentNameTaken(departmentName)) {
            showError("Department name \"" + departmentName
                    + "\" already exists (names are unique ignoring case).");
            return;
        }

        employeeService.addDepartment(departmentId, departmentName);

        departmentIdField.setText("");
        departmentNameField.setText("");
        refreshTable();
        onDepartmentsChanged.run();

        JOptionPane.showMessageDialog(this, "Department added successfully!",
                "Add Department", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Department department : employeeService.getDepartments()) {
            tableModel.addRow(new Object[]{
                    department.getDepartmentId(),
                    department.getDepartmentName()
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
