package ems.service;

import ems.model.EmployeeRecord;

import ems.model.Department;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Placeholder employee service. Keeps records in memory so adding, viewing,
 * searching, and deleting behave realistically until the real backend with
 * file storage exists.
 */
public class StubEmployeeService implements EmployeeService {

    private static final List<Department> DEFAULT_DEPARTMENTS = List.of(
            new Department("1", "IT"),
            new Department("2", "Human Resources"),
            new Department("3", "Finance"),
            new Department("4", "Marketing"),
            new Department("5", "Sales"));

    private final List<Department> departments = new ArrayList<>(DEFAULT_DEPARTMENTS);
    private final Map<Integer, EmployeeRecord> employees = new LinkedHashMap<>();

    @Override
    public List<Department> getDepartments() {
        return List.copyOf(departments);
    }

    @Override
    public boolean isDepartmentIdTaken(String departmentId) {
        for (Department department : departments) {
            if (department.getDepartmentId().equals(departmentId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDepartmentNameTaken(String departmentName) {
        for (Department department : departments) {
            if (department.getDepartmentName().equalsIgnoreCase(departmentName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addDepartment(String departmentId, String departmentName) {
        departments.add(new Department(departmentId, departmentName));
    }

    @Override
    public boolean isEmployeeIdTaken(int employeeId) {
        return employees.containsKey(employeeId);
    }

    @Override
    public void addFullTimeEmployee(int employeeId, String fullName, int age, String department,
                                    String position, double basicSalary, double bonus) {
        employees.put(employeeId,
                EmployeeRecord.fullTime(employeeId, fullName, age, department, position,
                        basicSalary, bonus));
    }

    @Override
    public void addPartTimeEmployee(int employeeId, String fullName, int age, String department,
                                    String position, double hourlyRate, double hoursWorked) {
        employees.put(employeeId,
                EmployeeRecord.partTime(employeeId, fullName, age, department, position,
                        hourlyRate, hoursWorked));
    }

    @Override
    public List<EmployeeRecord> getEmployees() {
        return List.copyOf(employees.values());
    }

    @Override
    public List<EmployeeRecord> searchById(int employeeId) {
        EmployeeRecord match = employees.get(employeeId);
        return match == null ? List.of() : List.of(match);
    }

    @Override
    public List<EmployeeRecord> searchByDepartment(String department) {
        List<EmployeeRecord> matches = new ArrayList<>();
        for (EmployeeRecord employee : employees.values()) {
            if (employee.getDepartment().equalsIgnoreCase(department)) {
                matches.add(employee);
            }
        }
        return matches;
    }

    @Override
    public void deleteEmployee(int employeeId) {
        employees.remove(employeeId);
    }

    @Override
    public void updateFullTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                       String department, String position, double basicSalary,
                                       double bonus) {
        if (originalId != employeeId) {
            employees.remove(originalId);
        }
        employees.put(employeeId,
                EmployeeRecord.fullTime(employeeId, fullName, age, department, position,
                        basicSalary, bonus));
    }

    @Override
    public void updatePartTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                       String department, String position, double hourlyRate,
                                       double hoursWorked) {
        if (originalId != employeeId) {
            employees.remove(originalId);
        }
        employees.put(employeeId,
                EmployeeRecord.partTime(employeeId, fullName, age, department, position,
                        hourlyRate, hoursWorked));
    }
}
