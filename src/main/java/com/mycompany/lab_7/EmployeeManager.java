package com.mycompany.lab_7;
import java.io.IOException;
import java.util.ArrayList;

public class EmployeeManager {
   

    private ArrayList<Employee> employees = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();

  
    public void loadData() throws IOException, ClassNotFoundException {
        employees = fileHandler.loadEmployees();
    }

    public ArrayList<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    public Employee findById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

   
    public boolean addEmployee(Employee emp) throws IOException {
        if (findById(emp.getId()) != null) {
            return false;
        }
        employees.add(emp);
        fileHandler.saveEmployees(employees);
        return true;
    }

   
    public boolean updateEmployee(int oldId, Employee updated) throws IOException {
        if (updated.getId() != oldId && findById(updated.getId()) != null) {
            return false;
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == oldId) {
                employees.set(i, updated);
                fileHandler.saveEmployees(employees);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmployee(int id) throws IOException {
        Employee emp = findById(id);
        if (emp == null) {
            return false;
        }
        employees.remove(emp);
        fileHandler.saveEmployees(employees);
        return true;
    }

   
    public ArrayList<Employee> searchByDepartment(String deptName) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getDepartment().getDepartmentName().equalsIgnoreCase(deptName)) {
                result.add(e);
            }
        }
        return result;
    }
}
