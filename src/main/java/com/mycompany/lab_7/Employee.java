package com.mycompany.lab_7;
import  java.io.Serializable;
public abstract class Employee implements Serializable {
    protected int id;
    protected String fullName;
    protected int age;
    protected Department department;
    protected String position;

    public Employee(int id, String fullName, int age,
                    Department department, String position) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Department getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public abstract double calculateSalary();
    public abstract String getType();
}
