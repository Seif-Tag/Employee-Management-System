package ems;

import ems.gui.LoginFrame;
import ems.service.AuthService;
import ems.service.EmployeeService;
import ems.service.StubAuthService;
import ems.service.StubEmployeeService;

import javax.swing.SwingUtilities;

/**
 * Application entry point. Starts the Swing UI on the event dispatch thread.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthService authService = new StubAuthService();
            EmployeeService employeeService = new StubEmployeeService();
            new LoginFrame(authService, employeeService).setVisible(true);
        });
    }
}
