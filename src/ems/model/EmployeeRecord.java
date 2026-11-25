package ems.model;

/**
 * Immutable employee data holder shared by the service layer and the table
 * view. The monthly salary follows the employment type: full-time employees
 * earn basic salary plus bonus, part-time employees earn hourly rate times
 * hours worked.
 */
public final class EmployeeRecord {

    public static final String FULL_TIME = "Full-Time";
    public static final String PART_TIME = "Part-Time";

    private final int employeeId;
    private final String fullName;
    private final int age;
    private final String department;
    private final String position;
    private final String employeeType;
    private final Double basicSalary;
    private final Double monthlyBonus;
    private final Double hourlyRate;
    private final Double hoursWorked;

    private EmployeeRecord(int employeeId, String fullName, int age, String department,
                           String position, String employeeType, Double basicSalary,
                           Double monthlyBonus, Double hourlyRate, Double hoursWorked) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.position = position;
        this.employeeType = employeeType;
        this.basicSalary = basicSalary;
        this.monthlyBonus = monthlyBonus;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public static EmployeeRecord fullTime(int employeeId, String fullName, int age,
                                          String department, String position,
                                          double basicSalary, double monthlyBonus) {
        return new EmployeeRecord(employeeId, fullName, age, department, position,
                FULL_TIME, basicSalary, monthlyBonus, null, null);
    }

    public static EmployeeRecord partTime(int employeeId, String fullName, int age,
                                          String department, String position,
                                          double hourlyRate, double hoursWorked) {
        return new EmployeeRecord(employeeId, fullName, age, department, position,
                PART_TIME, null, null, hourlyRate, hoursWorked);
    }

    public double getMonthlySalary() {
        if (FULL_TIME.equals(employeeType)) {
            return basicSalary + monthlyBonus;
        }
        return hourlyRate * hoursWorked;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public Double getMonthlyBonus() {
        return monthlyBonus;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }
}
