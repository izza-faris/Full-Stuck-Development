import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeProcessing{
    public static void main(String[] args) {
        // Creating Employee List
        List<Employee> employees = Arrays.asList(
                new Employee(1, "John", 35, 75000, "IT"),
                new Employee(2, "Sara", 28, 55000, "HR"),
                new Employee(3, "David", 40, 95000, "IT"),
                new Employee(4, "Emma", 32, 65000, "Finance"),
                new Employee(5, "Alex", 25, 45000, "HR"),
                new Employee(6, "Mike", 38, 85000, "IT")
        );
 
        // Task 01
        System.out.println("\n----- Employees Salary Greater Than Rs.60000 -----");
        Predicate<Employee> highSalary =
                employee -> employee.getSalary() > 60000;
        employees.stream()
                .filter(highSalary)
                .forEach(System.out::println);
 
        // Task 02
        System.out.println("\n----- IT Employees Above Age 30 -----");
        Predicate<Employee> itEmployee =
                employee -> employee.getDepartment().equals("IT")
                        && employee.getAge() > 30;
        employees.stream()
                .filter(itEmployee)
                .forEach(System.out::println);
 
        // Task 03
        System.out.println("\n----- Count Employees Age Greater Than 30 -----");
        long count = employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .count();
        System.out.println("Count: " + count);
 
        // Task 04
        System.out.println("\n----- Highest Paid Employee -----");
        Employee highestPaid = employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .get();
        System.out.println(highestPaid);
 
        // Task 05
        System.out.println("\n----- Employee Names In Uppercase -----");
        employees.stream()
                .map(employee -> employee.getName().toUpperCase())
                .forEach(System.out::println);
 
        // Task 06
        System.out.println("\n----- Average Salary -----");
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .getAsDouble();
        System.out.println("Average Salary: Rs " + averageSalary);
 
        // Task 07
        System.out.println("\n----- Employees Grouped By Department -----");
        Map<String, List<Employee>> groupedEmployees =
                employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));
        groupedEmployees.forEach((department, employeeList) -> {
            System.out.println("\nDepartment: " + department);
            employeeList.forEach(System.out::println);
        });
 
        // Task 08
        System.out.println("\n----- Employees Sorted By Salary Descending -----");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary)
                        .reversed())
                .forEach(System.out::println);
 
        // Task 09
        System.out.println("\n----- First Employee Salary Greater Than ₹80000 -----");
        employees.stream()
                .filter(employee -> employee.getSalary() > 80000)
                .findFirst()
                .ifPresent(System.out::println);
 
        // Task 10
        System.out.println("\n----- Second Highest Salary -----");
        double secondHighestSalary = employees.stream()
                .map(Employee::getSalary)
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .get();
        System.out.println("Second Highest Salary: Rs "
                + secondHighestSalary);
    }
}
 

class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;
    private String department;
 
    public Employee(int id, String name, int age, double salary, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }
 
    public int getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public int getAge() {
        return age;
    }
 
    public double getSalary() {
        return salary;
    }
 
    public String getDepartment() {
        return department;
    }
 
    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Age: " + age +
                ", Salary: Rs " + salary +
                ", Department: " + department;
    }
}
