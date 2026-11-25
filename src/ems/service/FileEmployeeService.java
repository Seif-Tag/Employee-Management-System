package ems.service;

import com.mycompany.lab_7.DepartmentManager;
import com.mycompany.lab_7.Employee;
import com.mycompany.lab_7.EmployeeManager;
import com.mycompany.lab_7.FullTimeEmployee;
import com.mycompany.lab_7.PartTimeEmployee;

import ems.model.Department;
import ems.model.EmployeeRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee service backed by the real {@code com.mycompany.lab_7} managers
 * and their file storage. The backend saves to disk on every mutation, so
 * each add, update, and delete is persisted immediately; failures surface as
 * the unchecked {@link DataPersistenceException} for the GUI to report.
 *
 * <p>Two representations meet at this boundary and are normalized here so
 * the UI code never sees backend details:</p>
 * <ul>
 *   <li>employment type: backend {@code "Full-time"/"part-time"} becomes
 *       {@link EmployeeRecord#FULL_TIME}/{@link EmployeeRecord#PART_TIME};</li>
 *   <li>department ID: the interface keeps {@code String} IDs while the
 *       backend requires a positive {@code int}.</li>
 * </ul>
 */
public class FileEmployeeService implements EmployeeService {

    private final EmployeeManager employeeManager = new EmployeeManager();
    private final DepartmentManager departmentManager = new DepartmentManager();

    /** Loads saved employees and departments from disk into the managers. */
    @Override
    public void loadData() {
        try {
            employeeManager.loadData();
            departmentManager.loadData();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        for (com.mycompany.lab_7.Department department : departmentManager.getDepartments()) {
            departments.add(new Department(String.valueOf(department.getDepartmentID()),
                    department.getDepartmentName()));
        }
        return departments;
    }

    @Override
    public boolean isDepartmentIdTaken(String departmentId) {
        Integer id = parsePositiveId(departmentId);
        if (id == null) {
            return false;
        }
        for (com.mycompany.lab_7.Department department : departmentManager.getDepartments()) {
            if (department.getDepartmentID() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDepartmentNameTaken(String departmentName) {
        for (com.mycompany.lab_7.Department department : departmentManager.getDepartments()) {
            if (department.getDepartmentName().equalsIgnoreCase(departmentName.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addDepartment(String departmentId, String departmentName) {
        Integer id = parsePositiveId(departmentId);
        if (id == null) {
            throw new DataPersistenceException("Department ID must be a positive whole number.");
        }
        try {
            departmentManager.addDepartment(
                    new com.mycompany.lab_7.Department(id, departmentName.trim()));
        } catch (IllegalArgumentException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        } catch (IOException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isEmployeeIdTaken(int employeeId) {
        return employeeManager.findById(employeeId) != null;
    }

    @Override
    public void addFullTimeEmployee(int employeeId, String fullName, int age, String department,
                                    String position, double basicSalary, double bonus) {
        FullTimeEmployee employee = new FullTimeEmployee(employeeId, fullName, age,
                resolveDepartment(department), position, basicSalary, bonus);
        saveNewEmployee(employee);
    }

    @Override
    public void addPartTimeEmployee(int employeeId, String fullName, int age, String department,
                                    String position, double hourlyRate, double hoursWorked) {
        PartTimeEmployee employee = new PartTimeEmployee(employeeId, fullName, age,
                resolveDepartment(department), position, hourlyRate, hoursWorked);
        saveNewEmployee(employee);
    }

    private void saveNewEmployee(Employee employee) {
        try {
            if (!employeeManager.addEmployee(employee)) {
                throw new DataPersistenceException(
                        "Employee ID " + employee.getId() + " is already in use.");
            }
        } catch (IOException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public List<EmployeeRecord> getEmployees() {
        return toRecords(employeeManager.getAll());
    }

    @Override
    public List<EmployeeRecord> searchById(int employeeId) {
        Employee match = employeeManager.findById(employeeId);
        return match == null ? List.of() : List.of(toRecord(match));
    }

    @Override
    public List<EmployeeRecord> searchByDepartment(String department) {
        return toRecords(employeeManager.searchByDepartment(department));
    }

    @Override
    public void deleteEmployee(int employeeId) {
        try {
            if (!employeeManager.deleteEmployee(employeeId)) {
                throw new DataPersistenceException(
                        "Employee ID " + employeeId + " does not exist.");
            }
        } catch (IOException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateFullTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                       String department, String position, double basicSalary,
                                       double bonus) {
        FullTimeEmployee updated = new FullTimeEmployee(employeeId, fullName, age,
                resolveDepartment(department), position, basicSalary, bonus);
        replaceEmployee(originalId, updated);
    }

    @Override
    public void updatePartTimeEmployee(int originalId, int employeeId, String fullName, int age,
                                       String department, String position, double hourlyRate,
                                       double hoursWorked) {
        PartTimeEmployee updated = new PartTimeEmployee(employeeId, fullName, age,
                resolveDepartment(department), position, hourlyRate, hoursWorked);
        replaceEmployee(originalId, updated);
    }

    private void replaceEmployee(int originalId, Employee updated) {
        try {
            if (!employeeManager.updateEmployee(originalId, updated)) {
                throw new DataPersistenceException(
                        "Employee ID " + updated.getId() + " is already in use.");
            }
        } catch (IOException e) {
            throw new DataPersistenceException(e.getMessage(), e);
        }
    }

    /** Maps backend employees to UI records, normalizing the type strings. */
    private List<EmployeeRecord> toRecords(List<Employee> employees) {
        List<EmployeeRecord> records = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            records.add(toRecord(employee));
        }
        return records;
    }

    private EmployeeRecord toRecord(Employee employee) {
        String departmentName = employee.getDepartment() == null
                ? "" : employee.getDepartment().getDepartmentName();
        if (employee instanceof FullTimeEmployee fullTime) {
            return EmployeeRecord.fullTime(employee.getId(), employee.getFullName(),
                    employee.getAge(), departmentName, employee.getPosition(),
                    fullTime.getBasicSalary(), fullTime.getBonus());
        }
        if (employee instanceof PartTimeEmployee partTime) {
            return EmployeeRecord.partTime(employee.getId(), employee.getFullName(),
                    employee.getAge(), departmentName, employee.getPosition(),
                    partTime.getHourlyRate(), partTime.getHoursWorked());
        }
        throw new DataPersistenceException(
                "Unsupported employee type: " + employee.getClass().getName());
    }

    /** Finds the backend department matching the given name (ignoring case). */
    private com.mycompany.lab_7.Department resolveDepartment(String departmentName) {
        for (com.mycompany.lab_7.Department department : departmentManager.getDepartments()) {
            if (department.getDepartmentName().equalsIgnoreCase(departmentName.trim())) {
                return department;
            }
        }
        throw new DataPersistenceException("Unknown department: " + departmentName);
    }

    private Integer parsePositiveId(String departmentId) {
        try {
            int id = Integer.parseInt(departmentId.trim());
            return id > 0 ? id : null;
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }
}
