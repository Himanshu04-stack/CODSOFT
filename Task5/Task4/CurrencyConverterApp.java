package Task4;

import java.util.*;

class Currency {
    private final String code;
    private final String name;
    private final double rateToINR;

    public Currency(String code, String name, double rateToINR) {
        this.code = code;
        this.name = name;
        this.rateToINR = rateToINR;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getRateToINR() {
        return rateToINR;
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}

class CurrencyConverter {
    private final Map<String, Currency> currencies = new HashMap<>();

    public CurrencyConverter() {
        addCurrency(new Currency("INR", "Indian Rupee", 1.0));
        addCurrency(new Currency("USD", "US Dollar", 83.20));
        addCurrency(new Currency("EUR", "Euro", 89.75));
        addCurrency(new Currency("GBP", "British Pound", 105.10));
        addCurrency(new Currency("JPY", "Japanese Yen", 0.58));
        addCurrency(new Currency("CAD", "Canadian Dollar", 61.42));
    }

    private void addCurrency(Currency currency) {
        currencies.put(currency.getCode(), currency);
    }

    public boolean isSupported(String code) {
        return currencies.containsKey(code.toUpperCase());
    }

    public void printSupportedCurrencies() {
        System.out.println("Supported Currencies:");
        for (Currency currency : currencies.values()) {
            System.out.printf("• %-5s : %s%n", currency.getCode(), currency.getName());
        }
    }

    public double convert(String fromCode, String toCode, double amount) {
        Currency from = currencies.get(fromCode.toUpperCase());
        Currency to = currencies.get(toCode.toUpperCase());

        double amountInINR = amount * from.getRateToINR();
        return amountInINR / to.getRateToINR();
    }
}

public class CurrencyConverterApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyConverter converter = new CurrencyConverter();

    public static void main(String[] args) {
        System.out.println("\nWelcome to Smart Currency Converter");
        System.out.println("Developed by: Himanshu Garg (CODSOFT Intern)");
        boolean running = true;

        while (running) {
            printMainMenu();
            int option = readInt("Choose an option: ");

            switch (option) {
                case 1 -> handleConversion();
                case 2 -> converter.printSupportedCurrencies();
                case 3 -> {
                    System.out.println("\nThank you for using Smart Converter. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1–3.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=============================");
        System.out.println("        MAIN MENU           ");
        System.out.println("=============================");
        System.out.println("1. Convert Currency");
        System.out.println("2. View Supported Currencies");
        System.out.println("3. Exit");
    }

    private static void handleConversion() {
        System.out.print("\nEnter source currency code (e.g., USD): ");
        String from = scanner.next().toUpperCase();

        System.out.print("Enter target currency code (e.g., INR): ");
        String to = scanner.next().toUpperCase();

        if (!converter.isSupported(from) || !converter.isSupported(to)) {
            System.out.println("One or both currency codes are invalid.");
            return;
        }

        double amount = readDouble("Enter amount to convert: ");

        double result = converter.convert(from, to, amount);
        System.out.printf("\n%.2f %s = %.2f %s\n", amount, from, result, to);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Enter a valid amount: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}