import java.util.Scanner;

public class BankAccount1 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank account = new Bank(10001, "Izza", 6000.0);

        System.out.println("==================================");
        System.out.println("        BANK ACCOUNT SYSTEM       ");
        System.out.println("==================================");

        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder Name: " + account.getAccountHolderName());
        System.out.println("Available Balance: Rs." + account.getBalance());

        System.out.print("Enter the amount to withdraw: Rs.");
        double amount = scanner.nextDouble();

        try {
            account.withdraw(amount);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nFinal Balance: Rs." + account.getBalance());

        scanner.close();
    }
}

// Custom Exception
class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Bank Class
class Bank {

    private int accountNumber;
    private String accountHolderName;
    private double balance;

    public Bank(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient funds. Available balance: Rs." + balance);
        }

        balance -= amount;

        System.out.println("\nWithdrawal Successful!");
        System.out.println("Amount Withdrawn : Rs." + amount);
        System.out.println("Available Balance : Rs." + balance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }
}
