package com.mycompany.lab_7;



public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(int id, String name, int age,
            Department department, String position,
            double hourlyRate, double hoursWorked) {

        super(id, name, age, department, position);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        if (hourlyRate < 0 || hoursWorked < 0 ||
    !Double.isFinite(hourlyRate) ||
    !Double.isFinite(hoursWorked)) {
    throw new IllegalArgumentException("Invalid hourly rate or hours.");
}
        return hourlyRate * hoursWorked;
    }
    @Override
public String getType() {
    return "part-time";
}

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

}