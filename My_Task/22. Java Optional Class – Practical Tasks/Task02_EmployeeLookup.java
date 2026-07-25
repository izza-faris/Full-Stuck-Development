import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Task 02 - Employee Lookup
 *
 * Demonstrates the use of Optional<Employee> and orElse() to
 * provide a default Employee object when the requested employee
 * is not found, instead of returning or checking for null.
 */
public class Task02_EmployeeLookup {

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();

        // Test case 1: Existing employee ID
        System.out.println("---- Searching for Employee ID: 201 ----");
        Employee foundEmployee = employeeService.findEmployee(201)
                .orElse(new Employee(0, "Unknown Employee", 0.0));
        printEmployeeDetails(foundEmployee);

        // Test case 2: Non-existing employee ID -> orElse() supplies default
        System.out.println("\n---- Searching for Employee ID: 555 ----");
        Employee defaultEmployee = employeeService.findEmployee(555)
                .orElse(new Employee(0, "Unknown Employee", 0.0));
        printEmployeeDetails(defaultEmployee);
    }

    private static void printEmployeeDetails(Employee employee) {
        System.out.println("ID     : " + employee.getId());
        System.out.println("Name   : " + employee.getName());
        System.out.println("Salary : " + employee.getSalary());
    }
}

/**
 * Employee class - represents a single employee record.
 */
class Employee {

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary + "}";
    }
}

/**
 * EmployeeService class - contains the search logic for employees.
 */
class EmployeeService {

    private List<Employee> employeeList;

    public EmployeeService() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(201, "Ruwan Jayasuriya", 85000.0));
        employeeList.add(new Employee(202, "Dilani Wickramasinghe", 92000.0));
        employeeList.add(new Employee(203, "Sanduni Ratnayake", 78000.0));
    }

    /**
     * Searches for an employee by ID.
     * Returns Optional<Employee> so the caller can decide how to
     * handle a missing employee, e.g. using orElse() for a default value.
     */
    public Optional<Employee> findEmployee(int id) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }
}
