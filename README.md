# OOPBank

## Bank Application

This Java program is a simple bank application that allows users to create accounts, log in, and perform various banking operations such as depositing, withdrawing, and checking the account balance. The application supports two types of accounts: Checking Account and Credit Card.

## Account Hierarchy

The program uses an abstract Account class as the base class for the two account types. The Account class contains common attributes like balance and accountNumber along with abstract methods generateAccountNumber(), deposit(double amount), and withdraw(double amount) that need to be implemented by its subclasses.

The CheckingAccount class represents a checking account and extends the Account class. It includes an overdraftLimit attribute to handle overdraft scenarios. This class implements the abstract methods to handle account-specific operations.

The CreditCard class represents a credit card account and also extends the Account class. It includes attributes such as creditLimit and withdrawalFee. The abstract methods are implemented to handle credit card account operations.

## Main Class

The BankApp class serves as the entry point of the program. It allows users to create accounts, log in, and perform banking operations. The program maintains a list of accounts in the accounts list.

The main method presents a menu to the user, offering options to create an account, log in, or exit the application. Depending on the user's choice, appropriate methods are called to perform the desired actions.

The createAccount() method prompts the user to select the type of account they want to create (Checking Account or Credit Card) and generates a unique account number. The new account is then added to the accounts list.

The login() method prompts the user to enter their account number and verifies it against the existing accounts. If a match is found, the displayUserOptions() method is called, which provides a set of operations the user can perform on their account.

The displayUserOptions() method displays a menu specific to the user's account type (checking or credit card). Users can choose options such as depositing, withdrawing, checking the balance, or returning to the main menu. The selected operation is performed by calling the corresponding methods on the account object.

### Usage

Run the program.
Choose an option from the main menu.
Create Account: Select the account type (Checking Account or Credit Card) and follow the prompts to create a new account.
Login: Enter your account number and perform various operations on your account.
Exit: Terminate the application.
When logged in, choose an operation from the account-specific menu.
Deposit: Enter the amount to deposit.
Withdraw: Enter the amount to withdraw.
Get balance: Display the current balance of your account.
Return to main menu: Go back to the main menu.
Note: The program uses a simple console-based interface for demonstration purposes and doesn't include advanced error handling or persistence mechanisms.
