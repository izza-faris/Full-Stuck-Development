public class BankAccount6 {
    
      // Private Attributes
    private int accountNumber;
    private String accountHolderName ;
    private double balance;


    // Getter and Setter Methods

    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
     public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //deposit method

 public void deposit(double amount){
    balance+= amount;
    System.out.println("Amount deposited: $" + amount);

 }
 public void withdraw(double amount){
    if(balance >=amount){
        balance-=amount;
     System.out.println("Amount Withdrawn: $" + amount);
    }else{
        System.out.println("\"Insufficient Balance!\"");
    }
 }

  //Display Method//

  public void displayAccountDetails(){
    System.out.println("\n Account details\n");
    System.out.println("Account Number:" + accountNumber);
    System.out.println("Account Holder:" + accountHolderName );
    System.out.println("Balance:" + balance );
  }

}



