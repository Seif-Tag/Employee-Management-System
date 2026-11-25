package ems.service;

import ems.model.Department;
import ems.model.EmployeeRecord;

import java.util.List;

/**
 * Temporary employee and department service contract. The real backend
 * (domain objects and file storage) will replace this stub without changing
 * the UI code.
 */
public interface EmployeeService {

    /** Returns every department, in insertion order. */
    List<Department> getDepartments();

    /** Temporary mock uniqueness check for department IDs. */
    boolean isDepartmentIdTaken(String departmentId);

    /** Temporary mock uniqueness check for department names, ignoring letter case. */
    boolean isDepartmentNameTaken(String departmentName);

    /** Adds a department; the caller must have validated ID and name uniqueness. */
    void addDepartment(String departmentId, String departmentName);

    /** Temporary mock uniqueness check for employee IDs. */
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
