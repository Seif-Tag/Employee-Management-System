package ems.gui;

import ems.model.EmployeeRecord;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

/**
 * Read-only table of employee records shared by the view and update
 * sections. Rows always match the list handed to {@link #setEmployees(List)},
 * so a selected row maps directly to its record.
 */
class EmployeeTable extends JPanel {

    private static final String[] COLUMNS = {
            "Employee ID", "Full Name", "Age", "Department", "Position", "Employee Type",
            "Basic Salary", "Monthly Bonus", "Hourly Rate", "Hours Worked", "Monthly Salary"
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    /** Records currently shown in the table, in table row order. */
    private List<EmployeeRecord> displayedEmployees = List.of();

    EmployeeTable() {
        setLayout(new BorderLayout());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(new Dimension(600, 160));
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    void setEmployees(List<EmployeeRecord> employees) {
        displayedEmployees = List.copyOf(employees);
        tableModel.setRowCount(0);
        for (EmployeeRecord employee : displayedEmployees) {
            tableModel.addRow(new Object[]{
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getAge(),
                    employee.getDepartment(),
                    employee.getPosition(),
                    employee.getEmployeeType(),
                    formatAmount(employee.getBasicSalary()),
                    formatAmount(employee.getMonthlyBonus()),
                    formatAmount(employee.getHourlyRate()),
                    formatAmount(employee.getHoursWorked()),
                    formatAmount(employee.getMonthlySalary())
            });
        }
        table.clearSelection();
    }

    /** Returns the selected record, or {@code null} when no row is selected. */
    EmployeeRecord getSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        return selectedRow == -1 ? null : displayedEmployees.get(selectedRow);
    }

    void addSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    private String formatAmount(Double value) {
        return value == null ? "" : String.format("%.2f", value);
    }
}
