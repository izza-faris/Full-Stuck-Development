public class Task03BankAccountTransfer {

    // Bank Account class
    static class BankAccount {
        private final int accountNumber;
        private double balance;

        public BankAccount(int accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public int getAccountNumber() {
            return accountNumber;
        }

        public double getBalance() {
            return balance;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }

        public void deposit(double amount) {
            balance += amount;
        }
    }

    // Transfer class
    static class MoneyTransfer {

        // This method intentionally creates a deadlock
        public static void transferWithDeadlock(
                BankAccount from,
                BankAccount to,
                double amount) {

            synchronized (from) {
                System.out.println(
                        Thread.currentThread().getName()
                                + " locked Account "
                                + from.getAccountNumber());

                // Small delay to make deadlock easier to demonstrate
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (to) {
                    System.out.println(
                            Thread.currentThread().getName()
                                    + " locked Account "
                                    + to.getAccountNumber());

                    from.withdraw(amount);
                    to.deposit(amount);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " transferred Rs."
                                    + amount
                                    + " from Account "
                                    + from.getAccountNumber()
                                    + " to Account "
                                    + to.getAccountNumber());
                }
            }
        }

        // This method prevents deadlock
        // by always locking accounts in the same order
        public static void transferSafely(
                BankAccount from,
                BankAccount to,
                double amount) {

            BankAccount firstLock;
            BankAccount secondLock;

            // Always lock the account with the smaller
            // account number first
            if (from.getAccountNumber() < to.getAccountNumber()) {
                firstLock = from;
                secondLock = to;
            } else {
                firstLock = to;
                secondLock = from;
            }

            synchronized (firstLock) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " locked Account "
                                + firstLock.getAccountNumber());

                synchronized (secondLock) {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " locked Account "
                                    + secondLock.getAccountNumber());

                    // Perform money transfer
                    from.withdraw(amount);
                    to.deposit(amount);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " transferred Rs."
                                    + amount
                                    + " from Account "
                                    + from.getAccountNumber()
                                    + " to Account "
                                    + to.getAccountNumber());
                }
            }
        }
    }

    public static void main(String[] args) {

        // Create two bank accounts
        BankAccount accountA =
                new BankAccount(101, 10000);

        BankAccount accountB =
                new BankAccount(102, 5000);

        System.out.println("=================================");
        System.out.println("BANK ACCOUNT TRANSFER SYSTEM");
        System.out.println("=================================");

        System.out.println("\nInitial Balances:");
        System.out.println(
                "Account A: Rs." + accountA.getBalance());
        System.out.println(
                "Account B: Rs." + accountB.getBalance());


        // =========================================
        // PART 1 - DEADLOCK DEMONSTRATION
        // =========================================

        System.out.println("\n=================================");
        System.out.println("PART 1: DEADLOCK DEMONSTRATION");
        System.out.println("=================================");

        Thread thread1 = new Thread(() -> {

            // Thread 1 locks A first, then tries to lock B
            MoneyTransfer.transferWithDeadlock(
                    accountA,
                    accountB,
                    1000);

        }, "Thread 1");


        Thread thread2 = new Thread(() -> {

            // Thread 2 locks B first, then tries to lock A
            MoneyTransfer.transferWithDeadlock(
                    accountB,
                    accountA,
                    500);

        }, "Thread 2");


        System.out.println(
                "Thread 1: Account A -> Account B");

        System.out.println(
                "Thread 2: Account B -> Account A");

        System.out.println(
                "\nWARNING: Running this section may cause DEADLOCK!");

        // Uncomment these lines to demonstrate deadlock
        /*
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        */

        System.out.println(
                "Deadlock demonstration code is ready.");
        System.out.println(
                "It is commented to allow the program to finish normally.");


        // =========================================
        // PART 2 - DEADLOCK PREVENTION
        // =========================================

        System.out.println("\n=================================");
        System.out.println("PART 2: DEADLOCK PREVENTION");
        System.out.println("=================================");

        Thread safeThread1 = new Thread(() -> {

            // Account A -> Account B
            MoneyTransfer.transferSafely(
                    accountA,
                    accountB,
                    1000);

        }, "Safe Thread 1");


        Thread safeThread2 = new Thread(() -> {

            // Account B -> Account A
            MoneyTransfer.transferSafely(
                    accountB,
                    accountA,
                    500);

        }, "Safe Thread 2");


        // Start both threads
        safeThread1.start();
        safeThread2.start();


        // Wait for both threads to finish
        try {
            safeThread1.join();
            safeThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        // =========================================
        // FINAL BALANCES
        // =========================================

        System.out.println("\n=================================");
        System.out.println("FINAL ACCOUNT BALANCES");
        System.out.println("=================================");

        System.out.println(
                "Account A: Rs." + accountA.getBalance());

        System.out.println(
                "Account B: Rs." + accountB.getBalance());

        System.out.println(
                "\nAll transfers completed successfully!");

        System.out.println(
                "Deadlock was prevented using consistent lock ordering.");
    }
}