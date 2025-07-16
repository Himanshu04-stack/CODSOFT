package Task3;

import java.util.*;

interface AccountOperations {
    void checkBalance();
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientBalanceException;
}

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class BankAccount implements AccountOperations {
    private final String accountNumber;
    private final String pin;
    private final String holderName;
    private double balance;

    public BankAccount(String accountNumber, String pin, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public String getHolderName() {
        return holderName;
    }

    @Override
    public void checkBalance() {
        System.out.printf("\n💰 Current balance: ₹%.2f\n", balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("✅ ₹%.2f deposited successfully.\n", amount);
        } else {
            System.out.println("❌ Deposit amount must be greater than 0.");
        }
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new InsufficientBalanceException("❌ Withdrawal amount must be greater than 0.");
        } else if (amount > balance) {
            throw new InsufficientBalanceException("❌ Insufficient balance. Transaction declined.");
        } else {
            balance -= amount;
            System.out.printf("✅ ₹%.2f withdrawn successfully.\n", amount);
        }
    }
}

class ATMSystem {
    private final Map<String, BankAccount> accountMap;
    private final Scanner scanner;

    public ATMSystem(List<BankAccount> accounts) {
        this.accountMap = new HashMap<>();
        for (BankAccount acc : accounts) {
            accountMap.put(acc.getAccountNumber(), acc);
        }
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n🏧 Welcome to CODSOFT ATM Interface");

        BankAccount currentAccount = authenticateUser();

        if (currentAccount == null) {
            System.out.println("❌ Authentication failed. Exiting.");
            return;
        }

        System.out.println("\n👋 Hello, " + currentAccount.getHolderName() + "!");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> currentAccount.checkBalance();
                    case 2 -> {
                        System.out.print("Enter amount to deposit: ₹");
                        double deposit = scanner.nextDouble();
                        currentAccount.deposit(deposit);
                    }
                    case 3 -> {
                        System.out.print("Enter amount to withdraw: ₹");
                        double withdraw = scanner.nextDouble();
                        currentAccount.withdraw(withdraw);
                    }
                    case 4 -> {
                        System.out.println("\n✅ Session ended. Please take your card. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private BankAccount authenticateUser() {
        System.out.print("Enter your account number: ");
        String accNum = scanner.nextLine();

        if (!accountMap.containsKey(accNum)) {
            System.out.println("❌ Account not found.");
            return null;
        }

        System.out.print("Enter your PIN: ");
        String enteredPin = scanner.nextLine();

        BankAccount account = accountMap.get(accNum);
        if (account.getPin().equals(enteredPin)) {
            return account;
        } else {
            System.out.println("❌ Incorrect PIN.");
            return null;
        }
    }

    private void showMenu() {
        System.out.println("\n===== ATM MENU =====");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Select an option (1-4): ");
    }

    private int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("❌ Invalid input. Enter a number (1-4): ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}

public class ATMInterfaceApp {
    public static void main(String[] args) {
        List<BankAccount> demoAccounts = List.of(
                new BankAccount("123456", "1111", "Himanshu", 8000.0),
                new BankAccount("654321", "2222", "Taru", 5000.0),
                new BankAccount("789012", "3333", "Pratham", 10000.0)
        );

        ATMSystem atm = new ATMSystem(demoAccounts);
        atm.start();
    }
}