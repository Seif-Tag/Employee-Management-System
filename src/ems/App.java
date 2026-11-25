package ems;

import ems.gui.LoginFrame;
import ems.service.AuthService;
import ems.service.DataPersistenceException;
import ems.service.EmployeeService;
import ems.service.FileEmployeeService;
import ems.service.StubAuthService;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Application entry point. Starts the Swing UI on the event dispatch thread
 * and loads previously saved data from the backend files; a load failure is
 * reported once and the application continues with empty storage.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthService authService = new StubAuthService();
            EmployeeService employeeService = new FileEmployeeService();
            try {
                employeeService.loadData();
            } catch (DataPersistenceException e) {
                JOptionPane.showMessageDialog(null,
                        "Could not load saved data: " + e.getMessage(),
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            new LoginFrame(authService, employeeService).setVisible(true);
        });
    }
}
