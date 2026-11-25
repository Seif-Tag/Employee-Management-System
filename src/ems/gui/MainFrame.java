package ems.gui;

import ems.service.AuthService;
import ems.service.EmployeeService;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

/**
 * Main application window. Hosts the section navigation and the dashboard,
 * and returns to the login screen when the user logs out.
 */
public class MainFrame extends JFrame {

    private final AuthService authService;
    private final EmployeeService employeeService;
    private final AddEmployeePanel addEmployeePanel;
    private final ViewEmployeesPanel viewEmployeesPanel;
    private final UpdateEmployeePanel updateEmployeesPanel;

    public MainFrame(String username, AuthService authService, EmployeeService employeeService) {
        super("Employee Management System");
        this.authService = authService;
        this.employeeService = employeeService;

        addEmployeePanel = new AddEmployeePanel(employeeService);
        viewEmployeesPanel = new ViewEmployeesPanel(employeeService);
        updateEmployeesPanel = new UpdateEmployeePanel(employeeService);
        DepartmentManagementPanel departmentManagementPanel =
                new DepartmentManagementPanel(employeeService, this::refreshDepartmentDropdowns);

        JTabbedPane navigation = new JTabbedPane();
        navigation.addTab("Dashboard", new DashboardPanel(username, this::logOut));
        navigation.addTab("Add Employee", addEmployeePanel);
        navigation.addTab("View Employees", viewEmployeesPanel);
        navigation.addTab("Update Employee", updateEmployeesPanel);
        navigation.addTab("Departments", departmentManagementPanel);
        navigation.addChangeListener(e -> {
            if (navigation.getSelectedComponent() == viewEmployeesPanel) {
                viewEmployeesPanel.refreshTable();
            }
            if (navigation.getSelectedComponent() == updateEmployeesPanel) {
                updateEmployeesPanel.refreshTable();
            }
        });
        add(navigation);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
    }

    /** Keeps every department dropdown in sync after a department is added. */
    private void refreshDepartmentDropdowns() {
        addEmployeePanel.refreshDepartments();
        viewEmployeesPanel.refreshDepartments();
        updateEmployeesPanel.refreshDepartments();
    }

    private void logOut() {
        new LoginFrame(authService, employeeService).setVisible(true);
        dispose();
    }
}
