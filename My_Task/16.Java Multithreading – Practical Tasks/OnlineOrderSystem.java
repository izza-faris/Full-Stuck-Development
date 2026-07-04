public class OnlineOrderSystem {

    static class OrderManager{
        public synchronized void processTask(String TaskName){
            System.out.println("---------------------------------");
            System.out.println( Thread.currentThread().getName() + " Started");
            System.out.println( TaskName);
            System.out.println( Thread.currentThread().getName() + " Completed");
            System.out.println("---------------------------------");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class OrderThread extends Thread{
        private OrderManager orderManager;
        private String taskName;

        public OrderThread(OrderManager manager,String ThreadkName, String task){
            super(ThreadkName);
            this.orderManager = manager;
            this.taskName = task;
        }

        @Override
        public void run() {
            orderManager.processTask(taskName);
        }
    }

    // Main Method
    public static void main(String[] args) {

        System.out.println("---------------------------------");
        System.out.println("Online Order System");
        System.out.println("---------------------------------");

        OrderManager orderManager = new OrderManager();

        OrderThread t1 = new OrderThread (orderManager, "OrderVerificationThread", "Verifying customer order");
        OrderThread t2 = new OrderThread (orderManager,  "PaymentProcessingThread", "Processing customer payment...");
        OrderThread t3 = new OrderThread (orderManager, "ShippingPreparationThread", "Preparing order for shipping...");

        // checking the thread states before starting the threads
          System.out.println("\nBefore Starting Threads:");
        System.out.println(t1.getName() + " Alive: " + t1.isAlive());
        System.out.println(t2.getName() + " Alive: " + t2.isAlive());
        System.out.println(t3.getName() + " Alive: " + t3.isAlive());
        
        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       
          // Check status after completion
        System.out.println("\nAfter Completing Threads:");
        System.out.println(t1.getName() + " Alive: " + t1.isAlive());
        System.out.println(t2.getName() + " Alive: " + t2.isAlive());
        System.out.println(t3.getName() + " Alive: " + t3.isAlive());

        System.out.println("\nAll order processing tasks completed successfully.\n");
    }
    }



