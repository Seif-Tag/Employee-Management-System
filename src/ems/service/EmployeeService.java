package ems.service;

import ems.model.Department;
import ems.model.EmployeeRecord;

import java.util.List;

/**
 * Employee and department service contract shared by every GUI panel.
 * Implementations persist through the file-backed backend; failures while
 * saving or loading are reported as the unchecked
 * {@link DataPersistenceException} so the GUI can show a dialog instead of
 * crashing. Implementations contain no UI code.
 */
public interface EmployeeService {

    /**
     * Loads previously saved employees and departments from disk. Called once
     * at startup; a failure is raised as {@link DataPersistenceException}.
     */
    void loadData();

    /** Returns every department, in insertion order. */
    List<Department> getDepartments();

    /** Uniqueness check for department IDs, ignoring non-numeric IDs. */
    boolean isDepartmentIdTaken(String departmentId);

    /** Uniqueness check for department names, ignoring letter case. */
    boolean isDepartmentNameTaken(String departmentName);

    /** Adds a department; the caller must have validated ID and name uniqueness. */
    void addDepartment(String departmentId, String departmentName);

    /** Uniqueness check for employee IDs. */
    boolean isEmployeeIdTaken(int employeeId);

    void addFullTimeEmployee(int employeeId, String fullName, int age, String department,
                             String position, double basicSalary, double bonus);

    void addPartTimeEmployee(int employeeId, String fullName, int age, String department,
                             String position, double hourlyRate, double hoursWorked);

    /** Returns every stored employee in insertion order. */
    List<EmployeeRecord> getEmployees();

    /** Returns the employee with the given ID, or an empty list if none exists. */
    List<EmployeeRecord> searchById(int employeeId);

    /** Returns employees of the given department, matching without regard to case. */
    List<EmployeeRecord> searchByDepartment(String department);

    /** Removes the employee with the given ID, if present. */
    void deleteEmployee(int employeeId);

    /**
     * Replaces the full-time employee stored under {@code originalId}.
     * The caller must ensure {@code employeeId} is not used by a different employee.
     */
    void updateFullTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                String department, String position, double basicSalary,
                                double bonus);

    /**
     * Replaces the part-time employee stored under {@code originalId}.
     * The caller must ensure {@code employeeId} is not used by a different employee.
     */
    void updatePartTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                String department, String position, double hourlyRate,
                                double hoursWorked);
}
