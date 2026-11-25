package com.mycompany.lab_7;
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String Employee_file = "employees.dat";

    
    public void saveEmployees(ArrayList<Employee> employees) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(Employee_file))) {
            out.writeObject(employees);
        }
    }

   
    @SuppressWarnings("unchecked")
    public ArrayList<Employee> loadEmployees() throws IOException, ClassNotFoundException {
        File file = new File(Employee_file);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {
            return (ArrayList<Employee>) in.readObject();
        }
    }
}