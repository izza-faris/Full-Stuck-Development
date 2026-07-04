public class StudentResultGenerator {

    // Shared Report Class
    static class Report {

        public synchronized void writeResult(String studentName, int marks) {
            System.out.println("----------------------------------");
            System.out.println(Thread.currentThread().getName() + " is writing result...");
            System.out.println("Student : " + studentName);
            System.out.println("Marks   : " + marks);
            System.out.println("Result saved successfully.");
            System.out.println("----------------------------------");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Student Thread Class
    static class Student extends Thread {

        private String studentName;
        private int marks;
        private Report report;

        public Student(String studentName, int marks, Report report) {
            this.studentName = studentName;
            this.marks = marks;
            this.report = report;
        }

        @Override
        public void run() {

            System.out.println(getName() + " is calculating marks for " + studentName + "...");

            try {
                Thread.sleep(2000); // Simulate calculation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            report.writeResult(studentName, marks);

            System.out.println(getName() + " completed.\n");
        }
    }

    // Main Method
    public static void main(String[] args) {

        Report report = new Report();

        Student s1 = new Student("Alice", 450, report);
        Student s2 = new Student("Bob", 390, report);
        Student s3 = new Student("Charlie", 480, report);

        s1.setName("Thread-1");
        s2.setName("Thread-2");
        s3.setName("Thread-3");

        System.out.println("========== STUDENT RESULT GENERATOR ==========\n");

        s1.start();
        s2.start();
        s3.start();

        // Check thread status using isAlive()
        while (s1.isAlive() || s2.isAlive() || s3.isAlive()) {

            System.out.println("\nChecking Thread Status...");
            System.out.println("Thread-1 Alive : " + s1.isAlive());
            System.out.println("Thread-2 Alive : " + s2.isAlive());
            System.out.println("Thread-3 Alive : " + s3.isAlive());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n==================================");
        System.out.println("All student results generated successfully.");
        System.out.println("==================================");
    }
}