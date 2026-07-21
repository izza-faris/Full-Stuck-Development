class BankAccount {
    private final String accountName;
    private int balance;

    public BankAccount(String accountName, int balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public synchronized int getBalance() {
        return balance;
    }

    /*
     * Deposit money into the account.
     * notifyAll() wakes up any thread waiting for money.
     */
    public synchronized void deposit(int amount) {
        balance += amount;

        System.out.println(
                Thread.currentThread().getName()
                        + " deposited Rs." + amount
                        + " into " + accountName
                        + ". Balance: Rs." + balance
        );

        // Notify all waiting threads that money is available
        notifyAll();
    }

    /*
     * Withdraw money from the account.
     * If there is not enough balance, the thread waits.
     */
    public synchronized void withdraw(int amount)
            throws InterruptedException {

        while (balance < amount) {
            System.out.println(
                    Thread.currentThread().getName()
                            + " is waiting because "
                            + accountName
                            + " has insufficient balance."
            );

            // Wait until another thread deposits money
            wait();
        }

        balance -= amount;

        System.out.println(
                Thread.currentThread().getName()
                        + " withdrew Rs." + amount
                        + " from " + accountName
                        + ". Balance: Rs." + balance
        );
    }
}


/*
 * This class demonstrates a DEADLOCK scenario.
 *
 * Thread 1 locks Account A first and then waits for Account B.
 * Thread 2 locks Account B first and then waits for Account A.
 *
 * Both threads can wait for each other forever.
 */
class DeadlockTransfer implements Runnable {

    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final int amount;

    public DeadlockTransfer(
            BankAccount fromAccount,
            BankAccount toAccount,
            int amount) {

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {

        synchronized (fromAccount) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " locked "
                            + fromAccount.getAccountName()
            );

            try {
                // Give the other thread time to lock its account
                Thread.sleep(100);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (toAccount) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " locked "
                                + toAccount.getAccountName()
                );

                System.out.println(
                        Thread.currentThread().getName()
                                + " transferring Rs."
                                + amount
                );
            }
        }
    }
}


/*
 * This class performs a SAFE money transfer.
 *
 * Both accounts are always locked in the same order.
 * This consistent locking strategy prevents deadlock.
 */
class SafeMoneyTransfer implements Runnable {

    private final BankAccount fromAccount;
    private final BankAccount toAccount;
    private final int amount;

    public SafeMoneyTransfer(
            BankAccount fromAccount,
            BankAccount toAccount,
            int amount) {

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {

        /*
         * Lock accounts in a consistent order.
         * The account with the smaller identity hash code
         * is always locked first.
         */
        BankAccount firstLock;
        BankAccount secondLock;

        if (System.identityHashCode(fromAccount)
                < System.identityHashCode(toAccount)) {

            firstLock = fromAccount;
            secondLock = toAccount;

        } else {

            firstLock = toAccount;
            secondLock = fromAccount;
        }

        synchronized (firstLock) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " locked "
                            + firstLock.getAccountName()
            );

            synchronized (secondLock) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " locked "
                                + secondLock.getAccountName()
                );

                try {

                    /*
                     * Withdraw from source account.
                     * If balance is insufficient,
                     * the thread waits using wait().
                     */
                    fromAccount.withdraw(amount);

                    /*
                     * Deposit into destination account.
                     * notifyAll() is called inside deposit().
                     */
                    toAccount.deposit(amount);

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " successfully transferred Rs."
                                    + amount
                                    + " from "
                                    + fromAccount.getAccountName()
                                    + " to "
                                    + toAccount.getAccountName()
                    );

                } catch (InterruptedException e) {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " was interrupted."
                    );

                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}


/*
 * Main class
 */
public class Task02BankTransfer {

    public static void main(String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                " TASK 02 - BANK ACCOUNT MONEY TRANSFER"
        );

        System.out.println(
                "======================================\n"
        );


        /*
         * Create two bank accounts.
         */
        BankAccount accountA =
                new BankAccount("Account A", 1000);

        BankAccount accountB =
                new BankAccount("Account B", 500);


        /*
         * PART 1:
         * Demonstrate Deadlock
         */
        System.out.println(
                "PART 1: DEADLOCK DEMONSTRATION"
        );

        System.out.println(
                "--------------------------------------"
        );

        System.out.println(
                "Thread 1 will lock Account A first."
        );

        System.out.println(
                "Thread 2 will lock Account B first."
        );

        System.out.println(
                "This creates a potential deadlock.\n"
        );


        Thread deadlockThread1 =
                new Thread(
                        new DeadlockTransfer(
                                accountA,
                                accountB,
                                100
                        ),
                        "Deadlock Thread 1"
                );


        Thread deadlockThread2 =
                new Thread(
                        new DeadlockTransfer(
                                accountB,
                                accountA,
                                100
                        ),
                        "Deadlock Thread 2"
                );


        deadlockThread1.start();
        deadlockThread2.start();


        try {

            /*
             * Wait for a short time to demonstrate
             * the deadlock situation.
             */
            Thread.sleep(500);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }


        System.out.println(
                "\nDeadlock demonstration completed."
        );

        System.out.println(
                "The deadlock threads are not joined because"
                        + " they may remain blocked.\n"
        );


        /*
         * PART 2:
         * Prevent Deadlock
         */
        System.out.println(
                "PART 2: DEADLOCK PREVENTION"
        );

        System.out.println(
                "--------------------------------------"
        );

        System.out.println(
                "Using consistent lock ordering."
        );

        System.out.println(
                "Both threads lock accounts in the same order.\n"
        );


        /*
         * Create two safe transfer threads.
         *
         * Thread 1:
         * Account A -> Account B
         *
         * Thread 2:
         * Account B -> Account A
         */
        Thread transferThread1 =
                new Thread(
                        new SafeMoneyTransfer(
                                accountA,
                                accountB,
                                800
                        ),
                        "Transfer Thread 1"
                );


        Thread transferThread2 =
                new Thread(
                        new SafeMoneyTransfer(
                                accountB,
                                accountA,
                                300
                        ),
                        "Transfer Thread 2"
                );


        /*
         * Start both transfer threads.
         */
        transferThread1.start();
        transferThread2.start();


        try {

            /*
             * Wait until both threads complete.
             */
            transferThread1.join();
            transferThread2.join();

        } catch (InterruptedException e) {

            System.out.println(
                    "Main thread was interrupted."
            );

            Thread.currentThread().interrupt();
        }


        /*
         * Display final balances.
         */
        System.out.println(
                "\n======================================"
        );

        System.out.println(
                " FINAL ACCOUNT BALANCES"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                accountA.getAccountName()
                        + ": Rs."
                        + accountA.getBalance()
        );

        System.out.println(
                accountB.getAccountName()
                        + ": Rs."
                        + accountB.getBalance()
        );


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                " BANK TRANSFER SYSTEM COMPLETED"
        );

        System.out.println(
                "======================================"
        );
    }
}