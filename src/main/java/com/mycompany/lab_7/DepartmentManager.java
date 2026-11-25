package com.mycompany.lab_7;
import java.io.IOException;
import java.util.ArrayList;

public class DepartmentManager {

    private ArrayList<Department> departments;
    private FileHandler fileHandler = new FileHandler();

    public DepartmentManager() {
        departments = new ArrayList<>();
        departments.add(new Department(1, "IT"));
        departments.add(new Department(2, "Human Resources"));
        departments.add(new Department(3, "Finance"));
        departments.add(new Department(4, "Marketing"));
        departments.add(new Department(5, "Sales"));
        }

    /** Loads saved departments; keeps the defaults when no file exists yet. */
    public void loadData() throws IOException, ClassNotFoundException {
        ArrayList<Department> saved = fileHandler.loadDepartments();
        if (!saved.isEmpty()) {
            departments = saved;
        }
    }

    public void addDepartment(Department department) throws IOException {

        if (department == null) {
            throw new IllegalArgumentException("Invalid department.");
        }

        if (department.getDepartmentID() <= 0) {
            throw new IllegalArgumentException(
                "Department ID must be positive.");
        }

        if (department.getDepartmentName() == null ||
            department.getDepartmentName().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Department name cannot be empty.");
        }

        for (Department d : departments) {

            if (d.getDepartmentID() ==
                department.getDepartmentID()) {
                throw new IllegalArgumentException(
                    "Department ID already exists.");
            }

            if (d.getDepartmentName().equalsIgnoreCase(
                department.getDepartmentName().trim())) {
                throw new IllegalArgumentException(
                    "Department name already exists.");
            }
        }

        departments.add(department);
        fileHandler.saveDepartments(departments);
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }
}

