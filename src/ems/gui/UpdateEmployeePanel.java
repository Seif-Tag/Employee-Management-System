package ems.gui;

import ems.model.EmployeeRecord;
import ems.service.DataPersistenceException;
import ems.service.EmployeeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;

/**
 * Update section. Selecting a row in the table loads that employee into the
 * editable form (salary inputs follow the employee type); the update action
 * validates the form before handing the changes to the employee service.
 */
public class UpdateEmployeePanel extends JPanel {

    private static final String DEPARTMENT_PROMPT = "-- Select Department --";
    private static final String TYPE_PROMPT = "-- Select Employee Type --";

    private final EmployeeService employeeService;
    private final EmployeeTable employeeTable = new EmployeeTable();

    private final JTextField employeeIdField = new JTextField(15);
    private final JTextField fullNameField = new JTextField(15);
    private final JTextField ageField = new JTextField(15);
    private final JComboBox<String> departmentComboBox = new JComboBox<>();
    private final JTextField positionField = new JTextField(15);
    private final JComboBox<String> employeeTypeComboBox = new JComboBox<>();

    private final JLabel basicSalaryLabel = new JLabel("Basic Monthly Salary:");
    private final JTextField basicSalaryField = new JTextField(15);
    private final JLabel bonusLabel = new JLabel("Monthly Bonus:");
    private final JTextField bonusField = new JTextField(15);

    private final JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
    private final JTextField hourlyRateField = new JTextField(15);
    private final JLabel hoursLabel = new JLabel("Hours Worked per Month:");
    private final JTextField hoursField = new JTextField(15);

    public UpdateEmployeePanel(EmployeeService employeeService) {
        this.employeeService = employeeService;

        refreshDepartments();

        employeeTypeComboBox.addItem(TYPE_PROMPT);
        employeeTypeComboBox.addItem(EmployeeRecord.FULL_TIME);
        employeeTypeComboBox.addItem(EmployeeRecord.PART_TIME);
        employeeTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSalaryFields();
            }
        });

        employeeTable.addSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(employeeTable, BorderLayout.NORTH);
        JScrollPane formScroll = new JScrollPane(buildFormPanel());
        formScroll.setBorder(BorderFactory.createEmptyBorder());
        add(formScroll, BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        updateSalaryFields();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);

        addRow(form, gc, 0, new JLabel("Employee ID:"), employeeIdField);
        addRow(form, gc, 1, new JLabel("Full Name:"), fullNameField);
        addRow(form, gc, 2, new JLabel("Age:"), ageField);
        addRow(form, gc, 3, new JLabel("Department:"), departmentComboBox);
        addRow(form, gc, 4, new JLabel("Position:"), positionField);
        addRow(form, gc, 5, new JLabel("Employee Type:"), employeeTypeComboBox);
        addRow(form, gc, 6, basicSalaryLabel, basicSalaryField);
        addRow(form, gc, 7, bonusLabel, bonusField);
        addRow(form, gc, 8, hourlyRateLabel, hourlyRateField);
        addRow(form, gc, 9, hoursLabel, hoursField);

        return form;
    }

    private void addRow(JPanel form, GridBagConstraints gc, int row, JComponent label, JComponent field) {
        gc.gridx = 0;
        gc.gridy = row;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        form.add(label, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        form.add(field, gc);
    }

    private JPanel buildButtonPanel() {
        JButton updateButton = new JButton("Update Employee");
        updateButton.addActionListener(e -> updateEmployee());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(updateButton);
        return panel;
    }

    /** Rebuilds the department dropdown from the service, keeping the selection if possible. */
    public void refreshDepartments() {
        DepartmentCombo.refresh(departmentComboBox, DEPARTMENT_PROMPT, employeeService.getDepartments());
    }

    private void loadSelectedEmployee() {
        EmployeeRecord employee = employeeTable.getSelectedEmployee();
        if (employee == null) {
            return;
        }

        employeeIdField.setText(String.valueOf(employee.getEmployeeId()));
        fullNameField.setText(employee.getFullName());
        ageField.setText(String.valueOf(employee.getAge()));
        departmentComboBox.setSelectedItem(employee.getDepartment());
        positionField.setText(employee.getPosition());

        basicSalaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursField.setText("");

        employeeTypeComboBox.setSelectedItem(employee.getEmployeeType());

        if (EmployeeRecord.FULL_TIME.equals(employee.getEmployeeType())) {
            basicSalaryField.setText(String.valueOf(employee.getBasicSalary()));
            bonusField.setText(String.valueOf(employee.getMonthlyBonus()));
        } else {
            hourlyRateField.setText(String.valueOf(employee.getHourlyRate()));
            hoursField.setText(String.valueOf(employee.getHoursWorked()));
        }
    }

    private void updateSalaryFields() {
        Object selectedType = employeeTypeComboBox.getSelectedItem();
        boolean fullTime = EmployeeRecord.FULL_TIME.equals(selectedType);
        boolean partTime = EmployeeRecord.PART_TIME.equals(selectedType);

        basicSalaryLabel.setVisible(fullTime);
        basicSalaryField.setVisible(fullTime);
        bonusLabel.setVisible(fullTime);
        bonusField.setVisible(fullTime);

        hourlyRateLabel.setVisible(partTime);
        hourlyRateField.setVisible(partTime);
        hoursLabel.setVisible(partTime);
        hoursField.setVisible(partTime);

        revalidate();
        repaint();
    }

    private void updateEmployee() {
        EmployeeRecord selected = employeeTable.getSelectedEmployee();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an employee to update.",
                    "Update Employee",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int originalId = selected.getEmployeeId();

        String fullName = fullNameField.getText().trim();
        String position = positionField.getText().trim();
        Object department = departmentComboBox.getSelectedItem();
        Object employeeType = employeeTypeComboBox.getSelectedItem();

        if (fullName.isEmpty()) {
            showError("Full name is required.");
            return;
        }
        if (position.isEmpty()) {
            showError("Position is required.");
            return;
        }
        if (DEPARTMENT_PROMPT.equals(department)) {
            showError("Please select a department.");
            return;
        }
        if (!EmployeeRecord.FULL_TIME.equals(employeeType)
                && !EmployeeRecord.PART_TIME.equals(employeeType)) {
            showError("Please select an employee type.");
            return;
        }

        Integer employeeId = parseInteger(employeeIdField.getText());
        if (employeeId == null) {
            showError("Employee ID must be a non-empty whole number.");
            return;
        }
        if (!employeeId.equals(originalId) && employeeService.isEmployeeIdTaken(employeeId)) {
            showError("Employee ID " + employeeId + " is already in use.");
            return;
        }

        Integer age = parseInteger(ageField.getText());
        if (age == null || age <= 0) {
            showError("Age must be a positive whole number.");
            return;
        }

        String departmentName = department.toString();

        try {
            if (EmployeeRecord.FULL_TIME.equals(employeeType)) {
                Double basicSalary = parseDecimal(basicSalaryField.getText());
                if (basicSalary == null || basicSalary < 0) {
                    showError("Basic monthly salary must be a non-negative number.");
                    return;
                }
                Double bonus = parseDecimal(bonusField.getText());
                if (bonus == null || bonus < 0) {
                    showError("Monthly bonus must be a non-negative number.");
                    return;
                }
                employeeService.updateFullTimeEmployee(originalId, employeeId, fullName, age,
                        departmentName, position, basicSalary, bonus);
            } else {
                Double hourlyRate = parseDecimal(hourlyRateField.getText());
                if (hourlyRate == null || hourlyRate < 0) {
                    showError("Hourly rate must be a non-negative number.");
                    return;
                }
                Double hoursWorked = parseDecimal(hoursField.getText());
                if (hoursWorked == null || hoursWorked < 0) {
                    showError("Hours worked per month must be a non-negative number.");
                    return;
                }
                employeeService.updatePartTimeEmployee(originalId, employeeId, fullName, age,
                        departmentName, position, hourlyRate, hoursWorked);
            }
        } catch (DataPersistenceException e) {
            JOptionPane.showMessageDialog(this, "Could not save data: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshTable();
        JOptionPane.showMessageDialog(this, "Employee updated successfully!",
                "Update Employee", JOptionPane.INFORMATION_MESSAGE);
    }

    public void refreshTable() {
        employeeTable.setEmployees(employeeService.getEmployees());
    }

    private Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDecimal(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
