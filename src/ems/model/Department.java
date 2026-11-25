package ems.model;

/**
 * Immutable department record: a unique ID and a name shared by employees.
 */
public final class Department {

    private final String departmentId;
    private final String departmentName;

    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
