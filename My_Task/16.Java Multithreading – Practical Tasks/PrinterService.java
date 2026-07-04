class Printer {

    synchronized void printDocument(String employeeName, String document) {

        System.out.println(employeeName + " started printing " + document);

        for (int i = 1; i <= 5; i++) {
            System.out.println(employeeName + " Printing page " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println(employeeName + " finished printing " + document);
        System.out.println();
    }
}

class Employee extends Thread {

    Printer printer;
    String employeeName;
    String document;

    Employee(Printer printer, String employeeName, String document) {
        this.printer = printer;
        this.employeeName = employeeName;
        this.document = document;
    }

    public void run() {
        printer.printDocument(employeeName, document);
    }
}

public class PrinterService {

    public static void main(String[] args) {

        Printer printer = new Printer();

        Employee e1 = new Employee(printer, "Ahmed", "Report.pdf");
        Employee e2 = new Employee(printer, "Ali", "Invoice.pdf");
        Employee e3 = new Employee(printer, "John", "Project.docx");

        // Set Thread Priority
        e1.setPriority(Thread.MAX_PRIORITY);
        e2.setPriority(Thread.NORM_PRIORITY);
        e3.setPriority(Thread.MIN_PRIORITY);

        // Start Threads
        e1.start();
        e2.start();
        e3.start();

        // Wait for all threads to complete
        try {
            e1.join();
            e2.join();
            e3.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("All print jobs completed.");
    }
}
