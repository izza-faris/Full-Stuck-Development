import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Task 01 - Student Search
 *
 * Demonstrates the use of Optional<Student> to avoid returning null
 * when a student is not found, and uses isPresent() to check the result.
 */
public class Task01_StudentSearch {

    public static void main(String[] args) {
        StudentService studentService = new StudentService();

        // Test case 1: Existing student ID
        System.out.println("---- Searching for Student ID: 102 ----");
        Optional<Student> studentOne = studentService.findStudentById(102);
        if (studentOne.isPresent()) {
            System.out.println("Student Found: " + studentOne.get().getName());
        } else {
            System.out.println("Student not found");
        }

        // Test case 2: Non-existing student ID
        System.out.println("\n---- Searching for Student ID: 999 ----");
        
        Optional<Student> studentTwo = studentService.findStudentById(999);
        if (studentTwo.isPresent()) {
            System.out.println("Student Found: " + studentTwo.get().getName());
        } else {
            System.out.println("Student not found");
        }
    }
}

/**
 * Student class - represents a single student record.
 */
class Student {

    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}

/**
 * StudentService class - contains the search logic for students.
 */
class StudentService {

    private List<Student> studentList;

    public StudentService() {
        studentList = new ArrayList<>();
        studentList.add(new Student(101, "Kavindu Perera"));
        studentList.add(new Student(102, "Nimasha Fernando"));
        studentList.add(new Student(103, "Ashan Silva"));
    }

    /**
     * Searches for a student by ID.
     * Returns Optional<Student> instead of null to avoid
     * NullPointerException risks when the student is not found.
     */
    public Optional<Student> findStudentById(int id) {
        for (Student student : studentList) {
            if (student.getId() == id) {
                // Optional.of() wraps the found student safely
                return Optional.of(student);
            }
        }
        // Optional.empty() represents "no value" instead of null
        return Optional.empty();
    }
}