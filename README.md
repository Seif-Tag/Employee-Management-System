# Employee Management System (EMS)

A desktop application developed in Java using Java Swing for the graphical user interface and Object-Oriented Programming (OOP) principles for backend management, salary calculation, and persistent file storage. The system enables administrators or HR personnel to manage company departments and employee records across full-time and part-time employment models.

## System Overview

The Employee Management System provides a graphical interface for performing core HR and administrative operations without requiring application restarts between actions. It supports two employee categories that share common profile attributes while applying distinct salary rules:

* **Full-Time Employee:** Paid a fixed monthly basic salary plus an optional monthly bonus (`Monthly Salary = Basic Salary + Bonus`).
* **Part-Time Employee:** Paid based on an hourly rate and monthly hours worked (`Monthly Salary = Hourly Rate * Hours Worked`).
* **Shared Employee Attributes:** Unique integer Employee ID, Full Name, Age, Department, Position, and Employee Type.
* **Departments:** Identified by a Department ID and Department Name (such as IT, Human Resources, Finance, Marketing, and Sales), with enforcement against duplicate IDs and case-insensitive duplicate names.

## Main Features

* **Login Screen:** Authenticates access before opening the main workspace using username and password fields, a Login button, and an Exit/Cancel button (default demonstration credentials: `admin` / `admin123`), displaying error messages on invalid entries.
* **Dashboard & Navigation:** Displays a welcome message after login, provides navigation across system functions (such as adding, viewing, updating, deleting, searching employees, and managing departments), and includes a Log Out action to return to the login screen.
* **Add Employee:** Provides a graphical form with dynamic salary inputs based on the selected employment type (basic salary and bonus for full-time; hourly rate and working hours for part-time), validates all fields and ID uniqueness, creates the corresponding employee instance, saves changes to storage, and confirms success.
* **View Employees:** Displays registered employees and their calculated monthly salaries in a `JTable` with row selection support.
* **Update Employee:** Loads a selected employee's record into editable fields (adjusting salary inputs to match the employee type), validates updated values while preserving ID uniqueness, updates the record, saves changes, and refreshes the table.
* **Delete Employee:** Prompts for confirmation via `JOptionPane` before removing a selected employee, cancels cleanly if declined, and updates storage and the table upon confirmation.
* **Search Employees:** Supports searching records by Employee ID or by Department (case-insensitive) and displays matching results or an appropriate message when no matches are found.
* **Department Management:** Allows adding and viewing departments, prevents duplicate IDs and case-insensitive duplicate names, keeps department selection dropdowns (`JComboBox`) synchronized, and persists department records.
* **File Persistence:** Loads saved employee and department data on startup and saves changes whenever records are added, updated, or deleted, preserving underlying object types and salary fields rather than table display text alone, while handling missing files or I/O errors gracefully.

## Object-Oriented Design & Project Structure

The project is structured around the four core OOP principles—**Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**—and separates user interface code from business logic and file operations:

* **Domain / Model Layer:** Abstract employee base class, `FullTimeEmployee` and `PartTimeEmployee` subclasses overriding salary calculation behavior, and `Department` representing department entities with encapsulated fields.
* **Service / Manager Layer:** Coordinates employee and department operations, uniqueness checks, search/filtering, salary calculation calls, and persistence workflows.
* **Persistence / File Handler Layer:** Reads and writes employee and department data to disk (text, CSV, or serialized objects) and reconstructs typed records cleanly.
* **GUI Layer (Java Swing):** Implements the login view, dashboard, navigation container (`CardLayout` or `JTabbedPane`), forms, `JTable` views, event listeners, and `JOptionPane` dialogs while delegating business rules and file handling to backend classes.
* **UML Class Diagram:** Documents classes, attributes (with visibility and types), methods (with parameters and return types), inheritance, associations, and relationships across GUI, management logic, and file handling.

## Input Validation & Error Handling

* Rejects blank values in required fields.
* Enforces unique integer Employee IDs and positive integer ages.
* Enforces non-negative numeric values for basic salary, bonus, hourly rate, and working hours.
* Validates selected departments and supported employee types.
* Alerts the user via `JOptionPane` if an update or delete is attempted without selecting an employee.
* Handles missing files (such as first launch) and file read/write errors without crashing or corrupting stored records.

## Build & Run

1. Compile all `.java` source files into a build directory (for example, `out`):
   ```bash
   javac -d out $(find src -name "*.java")