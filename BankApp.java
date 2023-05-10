import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

abstract class Account {
    private double balance;
    private String accountNumber;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    protected double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    protected abstract void generateAccountNumber();

    protected abstract void deposit(double amount);

    protected abstract void withdraw(double amount);

}

class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount() {
        this.setBalance(1000);
        this.overdraftLimit = -100;
    }

    public void generateAccountNumber() {
        Random RANDOM = new Random();
        int n1 = Math.abs(RANDOM.nextInt(90000000)) + 10000000;
        setAccountNumber(String.format("%08d", n1));
    }

    @Override
    public void deposit(double amount) {
        double newBalance = getBalance() + amount;
        setBalance(newBalance);
    }

    @Override
    public void withdraw(double amount) {
        double newBalance = getBalance() - amount;
        if (newBalance < overdraftLimit) {
            System.out.println("Withdrawal not allowed. Exceeds overdraft limit.");
        } else {
            setBalance(newBalance);
        }
    }
}

class CreditCard extends Account {
    private double creditLimit;
    private double withdrawalFee = 0.05;

    public CreditCard() {
        this.setBalance(0);
        this.creditLimit = 1000;
    }

    public void generateAccountNumber() {
        Random RANDOM = new Random();
        int n1 = Math.abs(RANDOM.nextInt(9000)) + 1000;
        int n2 = Math.abs(RANDOM.nextInt(10000));
        int n3 = Math.abs(RANDOM.nextInt(10000));
        int n4 = Math.abs(RANDOM.nextInt(10000));
        setAccountNumber(String.format("%04d %04d %04d %04d", n1, n2, n3, n4));
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() - amount);
        System.out.println("You made a payment of $" + amount + ". Your new balance is $" + getBalance() + ".");
    }

    @Override
    public void withdraw(double amount) {
        double fee = amount * withdrawalFee;
        if (getBalance() + amount + fee <= creditLimit) {
            setBalance(getBalance() + amount + fee);
            System.out.println("Cash advance fee: $" + fee);
        } else {
            System.out.println("Error: transaction exceeds credit limit.");
        }
    }
}

public class BankApp {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Account> accounts = new ArrayList<Account>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            String choiceString = scanner.nextLine();

            try {
                int choice = Integer.parseInt(choiceString);
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Exiting Bank App...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
            }
        }
    }

    public static void createAccount() {
        System.out.println("Please select the type of account you want to create:");
        System.out.println("1. Checking Account");
        System.out.println("2. Credit Card");

        String choiceString = scanner.nextLine();

        try {
            int choice = Integer.parseInt(choiceString);
            switch (choice) {
                case 1:
                    CheckingAccount checkingAccount = new CheckingAccount();
                    checkingAccount.generateAccountNumber();
                    accounts.add(checkingAccount);
                    System.out.println("Your account has been created. Your account number is "
                            + checkingAccount.getAccountNumber());
                    break;
                case 2:
                    CreditCard creditCard = new CreditCard();
                    creditCard.generateAccountNumber();
                    accounts.add(creditCard);
                    System.out.println(
                            "Your account has been created. Your account number is " + creditCard.getAccountNumber());
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer choice.");
        }
    }

    public static void login() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }

        System.out.println("Please enter your account number:");
        String accountNumber = scanner.nextLine();

        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                displayUserOptions(account);
                return;
            }
        }
        System.out.println("Account not found. Please try again.");
    }

    public static void displayUserOptions(Account account) {
        if (account instanceof CreditCard) {
            System.out.println("Welcome to your credit card account!");
        } else if (account instanceof CheckingAccount) {
            System.out.println("Welcome to your checking account!");
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Please select an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Get balance");
            System.out.println("4. Return to main menu");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    double balance = account.getBalance();
                    System.out.printf("Your current balance is $%.2f.\n", balance);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}