
public class Task01_PrinterScanner {

    // ---------------------------------------------------------
    // Shared Resource Class
    // ---------------------------------------------------------
    static class Resource {
        private final String resourceName;
        private boolean available = true;

        public Resource(String resourceName) {
            this.resourceName = resourceName;
        }

        /**
         * Acquires the resource.
         * If the resource is busy, the thread waits.
         */
        public synchronized void acquire(String userName)
                throws InterruptedException {

            while (!available) {
                System.out.println(
                        userName + " is waiting for " + resourceName + "..."
                );

                wait();
            }

            available = false;

            System.out.println(
                    userName + " acquired the " + resourceName + "."
            );
        }

        /**
         * Releases the resource and notifies
         * all waiting threads.
         */
        public synchronized void release(String userName) {

            available = true;

            System.out.println(
                    userName + " released the " + resourceName + "."
            );

            notifyAll();
        }

        public String getResourceName() {
            return resourceName;
        }
    }


    // ---------------------------------------------------------
    // Deadlock Demonstration
    // ---------------------------------------------------------
    static class DeadlockUser implements Runnable {

        private final String userName;
        private final Resource firstResource;
        private final Resource secondResource;

        public DeadlockUser(
                String userName,
                Resource firstResource,
                Resource secondResource) {

            this.userName = userName;
            this.firstResource = firstResource;
            this.secondResource = secondResource;
        }

        @Override
        public void run() {

            synchronized (firstResource) {

                System.out.println(
                        userName + " locked " +
                        firstResource.getResourceName()
                );

                // Small delay to make deadlock more likely
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                        userName + " is trying to lock " +
                        secondResource.getResourceName()
                );

                synchronized (secondResource) {

                    System.out.println(
                            userName + " acquired both resources."
                    );
                }
            }
        }
    }


    // ---------------------------------------------------------
    // Deadlock Prevention Demonstration
    // ---------------------------------------------------------
    static class SafeUser implements Runnable {

        private final String userName;
        private final Resource printer;
        private final Resource scanner;

        public SafeUser(
                String userName,
                Resource printer,
                Resource scanner) {

            this.userName = userName;
            this.printer = printer;
            this.scanner = scanner;
        }

        @Override
        public void run() {

            try {

                /*
                 * Both users always acquire resources
                 * in the same order:
                 *
                 * 1. Printer
                 * 2. Scanner
                 *
                 * This consistent lock ordering prevents deadlock.
                 */

                printer.acquire(userName);

                try {

                    scanner.acquire(userName);

                    try {

                        System.out.println(
                                userName +
                                " is using the Printer and Scanner."
                        );

                        Thread.sleep(1000);

                        System.out.println(
                                userName +
                                " completed the printing and scanning task."
                        );

                    } finally {

                        scanner.release(userName);
                    }

                } finally {

                    printer.release(userName);
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        userName +
                        " was interrupted while waiting for a resource."
                );
            }
        }
    }


    
    // Main Method
   
    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("TASK 01 - PRINTER AND SCANNER MANAGEMENT");
        System.out.println("==========================================");


        // -----------------------------------------------------
        // PART 1: Demonstrating Deadlock
        // -----------------------------------------------------

        System.out.println("\n--- PART 1: DEADLOCK DEMONSTRATION ---");

        Resource printerForDeadlock =
                new Resource("Printer");

        Resource scannerForDeadlock =
                new Resource("Scanner");


        /*
         * User1 locks Printer first and then tries Scanner.
         *
         * User2 locks Scanner first and then tries Printer.
         *
         * This creates a circular waiting condition:
         *
         * User1 -> Printer -> waiting for Scanner
         * User2 -> Scanner -> waiting for Printer
         *
         * Therefore, a deadlock can occur.
         */

        Thread user1Deadlock = new Thread(
                new DeadlockUser(
                        "User1",
                        printerForDeadlock,
                        scannerForDeadlock
                )
        );

        Thread user2Deadlock = new Thread(
                new DeadlockUser(
                        "User2",
                        scannerForDeadlock,
                        printerForDeadlock
                )
        );


        System.out.println(
                "Starting User1 and User2 for deadlock demonstration..."
        );

        user1Deadlock.start();
        user2Deadlock.start();


        /*
         * Wait for a short time to demonstrate that
         * the threads may remain blocked because of deadlock.
         */

        try {

            user1Deadlock.join(1000);
            user2Deadlock.join(1000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }


        if (user1Deadlock.isAlive() || user2Deadlock.isAlive()) {

            System.out.println(
                    "\nDeadlock detected!"
            );

            System.out.println(
                    "Both users are waiting for resources "
                    + "held by each other."
            );

        } else {

            System.out.println(
                    "\nDeadlock did not occur in this execution."
            );
        }


        // -----------------------------------------------------
        // PART 2: Deadlock Prevention
        // -----------------------------------------------------

        System.out.println(
                "\n--- PART 2: DEADLOCK PREVENTION ---"
        );


        Resource printer =
                new Resource("Printer");

        Resource scanner =
                new Resource("Scanner");


        /*
         * Both User1 and User2 use the same resource order:
         *
         * Printer -> Scanner
         *
         * Therefore, circular waiting is avoided.
         */

        Thread user1 = new Thread(
                new SafeUser(
                        "User1",
                        printer,
                        scanner
                )
        );

        Thread user2 = new Thread(
                new SafeUser(
                        "User2",
                        printer,
                        scanner
                )
        );


        System.out.println(
                "Starting User1 and User2 with deadlock prevention..."
        );


        user1.start();
        user2.start();


        try {

            user1.join();
            user2.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Main thread was interrupted."
            );
        }


        System.out.println(
                "\nBoth users completed successfully."
        );

        System.out.println(
                "Deadlock was prevented using consistent "
                + "resource ordering."
        );

        System.out.println(
                "wait() and notifyAll() were used to manage "
                + "busy resources."
        );


        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "TASK 01 COMPLETED SUCCESSFULLY"
        );

        System.out.println(
                "=========================================="
        );
    }
}