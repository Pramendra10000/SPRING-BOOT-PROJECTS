package com.cu.chat.oops;

 class BankAccount {
    // Step 1: Private fields
    private double balance;

    // Step 2: Constructor to initialize the balance
    public BankAccount(double initialBalance) {
        if (initialBalance > 0) {
            balance = initialBalance;
        } else {
            System.out.println("Initial balance must be greater than 0");
        }
    }
 
    // Step 3: Getter method to access the balance
    public double getBalance() {
        return balance;
    }

    // Step 4: Setter method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance +  amount;
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Step 5: Method to withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance =  amount;
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }
}

public class Encapsulation {
    public static void main(String[] args) {
        // Step 6: Creating a BankAccount object
        BankAccount account = new BankAccount(100.0);

        // Step 7: Accessing the balance through getter
        System.out.println("Initial Balance: " + account.getBalance());

        // Step 8: Depositing money
        account.deposit(50.0);
        System.out.println("Balance after deposit: " + account.getBalance());

        // Step 9: Withdrawing money
        account.withdraw(30.0);
        System.out.println("Balance after withdrawal: " + account.getBalance());

        // Step 10: Trying an invalid withdrawal
        account.withdraw(200.0);  // Should print an error message
    }
}

