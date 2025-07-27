package Task1;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_NUMBER = 100;

    private static Scanner scanner = new Scanner(System.in);
    private static int score = 0;

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to the Number Guessing Game!");
        boolean playAgain;

        do {
            playGame();
            playAgain = askPlayAgain();
        } while (playAgain);

        System.out.println("👋 Thanks for playing! Your final score: " + score);
        scanner.close();
    }

    private static void playGame() {
        int numberToGuess = generateRandomNumber();
        int attemptsLeft = MAX_ATTEMPTS;

        System.out.println("\n🔢 I have selected a number between 1 and " + MAX_NUMBER + ".");
        System.out.println("💡 You have " + MAX_ATTEMPTS + " attempts to guess it!");

        while (attemptsLeft > 0) {
            int userGuess = getUserGuess();

            if (userGuess == numberToGuess) {
                System.out.println("✅ Correct! You've guessed the number! 🎉");
                score++;
                return;
            } else if (userGuess < numberToGuess) {
                System.out.println("⬆️ Too low!");
            } else {
                System.out.println("⬇️ Too high!");
            }

            attemptsLeft--;
            System.out.println("Attempts remaining: " + attemptsLeft);
        }

        System.out.println("❌ You've run out of attempts. The number was: " + numberToGuess);
    }

    private static int generateRandomNumber() {
        return new Random().nextInt(MAX_NUMBER) + 1;
    }

    private static int getUserGuess() {
        System.out.print("Enter your guess: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a number: ");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextInt();
    }

    private static boolean askPlayAgain() {
        System.out.print("🔁 Do you want to play again? (yes/no): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes");
    }
}