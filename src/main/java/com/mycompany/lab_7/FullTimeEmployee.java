package com.mycompany.lab_7;



public class FullTimeEmployee extends Employee {
    private double basicSalary;
    private double bonus;

    public FullTimeEmployee(int id, String name, int age,
            Department department, String position,
            double basicSalary, double bonus) {

        super(id, name, age, department, position);
        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        if (basicSalary < 0 || bonus < 0 ||
    !Double.isFinite(basicSalary) ||
    !Double.isFinite(bonus)) {
    throw new IllegalArgumentException("Invalid salary or bonus.");
}
       
        return basicSalary + bonus;
    }
    @Override
public String getType() {
    return "Full-time";
}

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    
}
