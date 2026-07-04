public class NumberPrinter {
    
    static class Printer {
        public synchronized void printNumbers(boolean isOdd, String threadName) {
            String ThreadName = Thread.currentThread().getName();

            System.out.println("---------------------------------");
            System.out.println(ThreadName + " Started");
            if (isOdd) {
                for (int i = 1; i <= 10; i += 2) {
                    System.out.println(threadName + " prints: " + i);
                    try {
                        Thread.sleep(500); // Simulate some work
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 2; i <= 10; i += 2) {
                    System.out.println(threadName + " prints: " + i);
                    try {
                        Thread.sleep(500); // Simulate some work
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(ThreadName + " Completed");
            System.out.println("---------------------------------");
        }
    }
    //ODD Thread
    static class OddThread extends Thread {
        private Printer printer;

        public OddThread(Printer printer) {
            this.printer = printer;
        }

        @Override
        public void run() {
            printer.printNumbers(true, "OddThread");
        }
    }
    //EVEN Thread
    static class EvenThread extends Thread {
        private Printer printer;

        public EvenThread(Printer printer) {
            this.printer = printer;
        }

        @Override
        public void run() {
            printer.printNumbers(false, "EvenThread");
        }
    }
    // Main Method
    public static void main(String[] args) {
         Printer printer = new Printer();

        OddThread oddThread = new OddThread(printer);
        EvenThread evenThread = new EvenThread(printer);

        oddThread.setName("Odd Thread");
        evenThread.setName("Even Thread");

        oddThread.start();
        evenThread.start();
    }
}
