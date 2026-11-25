package ems.gui;

import ems.model.Department;
import ems.service.EmployeeService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;

/**
 * Form for registering a new employee. The visible salary inputs switch
 * dynamically with the selected employee type, and every field is validated
 * before any data is handed to the employee service.
 */
public class AddEmployeePanel extends JPanel {

    private static final String DEPARTMENT_PROMPT = "-- Select Department --";
    private static final String TYPE_PROMPT = "-- Select Employee Type --";
    private static final String FULL_TIME = "Full-Time";
    private static final String PART_TIME = "Part-Time";

    private final EmployeeService employeeService;

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

    public AddEmployeePanel(EmployeeService employeeService) {
        this.employeeService = employeeService;

        refreshDepartments();

        employeeTypeComboBox.addItem(TYPE_PROMPT);
        employeeTypeComboBox.addItem(FULL_TIME);
        employeeTypeComboBox.addItem(PART_TIME);
        employeeTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSalaryFields();
            }
        });

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(buildFormPanel(), BorderLayout.CENTER);
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
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(e -> addEmployee());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(addButton);
        return panel;
    }

    /** Rebuilds the department dropdown from the service, keeping the selection if possible. */
    public void refreshDepartments() {
        DepartmentCombo.refresh(departmentComboBox, DEPARTMENT_PROMPT, employeeService.getDepartments());
    }

    private void updateSalaryFields() {
        Object selectedType = employeeTypeComboBox.getSelectedItem();
        boolean fullTime = FULL_TIME.equals(selectedType);
        boolean partTime = PART_TIME.equals(selectedType);

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

    private void addEmployee() {
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
        if (!FULL_TIME.equals(employeeType) && !PART_TIME.equals(employeeType)) {
            showError("Please select an employee type.");
            return;
        }

        Integer employeeId = parseInteger(employeeIdField.getText());
        if (employeeId == null) {
            showError("Employee ID must be a non-empty whole number.");
            return;
        }
        if (employeeService.isEmployeeIdTaken(employeeId)) {
            showError("Employee ID " + employeeId + " is already in use.");
            return;
        }

        Integer age = parseInteger(ageField.getText());
        if (age == null || age <= 0) {
            showError("Age must be a positive whole number.");
            return;
        }

        String departmentName = department.toString();

        if (FULL_TIME.equals(employeeType)) {
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
            employeeService.addFullTimeEmployee(employeeId, fullName, age, departmentName,
                    position, basicSalary, bonus);
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
            employeeService.addPartTimeEmployee(employeeId, fullName, age, departmentName,
                    position, hourlyRate, hoursWorked);
        }

        JOptionPane.showMessageDialog(this, "Employee added successfully!",
                "Add Employee", JOptionPane.INFORMATION_MESSAGE);
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
