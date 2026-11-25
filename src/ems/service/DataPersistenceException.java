package ems.service;

/**
 * Unchecked failure raised by the employee service when saving or loading
 * data fails (file I/O problems, rejected backend input). The GUI catches it
 * and reports the problem with a dialog instead of crashing; the service
 * itself never shows UI.
 */
public class DataPersistenceException extends RuntimeException {

    public DataPersistenceException(String message) {
        super(message);
    }

    public DataPersistenceException(String message, Throwable cause) {
        super(message != null ? message : describe(cause), cause);
    }

    private static String describe(Throwable cause) {
        if (cause == null) {
            return "Unknown error";
        }
        return cause.getMessage() != null ? cause.getMessage() : cause.toString();
    }
}
