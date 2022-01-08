package com.shchur.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class BankingApplication {

    private static final Scanner scanner = new Scanner(System.in);

    private static final InsertApp insertApp = new InsertApp();
    private static final SelectApp selectApp = new SelectApp();
    private static final UpdateApp updateApp = new UpdateApp();
    private static final CreateApp createApp = new CreateApp();

    private static final Random cardNum = new Random();
    private static final Random pinNum = new Random();

    private static boolean outerLoop = true;
    private static boolean innerLoop = true;

    private static final Card number = new Card();
    private static final Card pin = new Card();

    private static void exitApplication(ConfigurableApplicationContext ctx) {
        int exitCode = SpringApplication.exit(ctx, () -> 0);
        System.exit(exitCode);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BankingApplication.class, args);

        createApp.create();

        while (outerLoop) {
            System.out.printf(
                    "1. Create an account%n" +
                    "2. Log into account%n" +
                    "0. Exit%n"
            );
            int menuChoice = scanner.nextInt();
            switch (menuChoice) {
                case 1 -> {

                    insertApp.insert(number.createNumber(cardNum), pin.createPinCode(pinNum), 0);
                    System.out.printf(
                            "Your card has been created%n" +
                            "Your card number: %n%s%n" +
                            "Your card PIN: %n%s%n",
                            selectApp.selectNumber(), selectApp.selectPin()
                    );
                    System.out.println();
                }
                case 2 -> {
                    System.out.println("Enter your card number:");
                    String numberCheck = scanner.next();
                    System.out.println("Enter your PIN:");
                    String pinCheck = scanner.next();

                    List<String> listOfNumbers = selectApp.selectAllNumbers();
                    List<String> listOfPins = selectApp.selectAllPins();
                    if (listOfNumbers.contains(numberCheck) && listOfPins.contains(pinCheck)) {
                        System.out.println("You have successfully logged in!");
                        while (innerLoop) {
                            System.out.printf(
                                    "1. Balance%n" +
                                    "2. Add income%n" +
                                    "3. Do transfer%n" +
                                    "4. Close account%n" +
                                    "5. Log out%n" +
                                    "0. Exit%n"
                            );
                            int accountMenuChoice = scanner.nextInt();
                            switch (accountMenuChoice) {
                                case 1 -> System.out.printf("Your balance is %n%d%n", selectApp.selectBalance(numberCheck));
                                case 2 -> {
                                    System.out.println("Enter income");
                                    long income = scanner.nextLong();
                                    updateApp.addIncome(income, numberCheck);
                                    System.out.println("Income was added!");
                                }
                                case 3 -> {
                                    System.out.println("Enter card number:");
                                    String cardToTransfer = scanner.next();
                                    System.out.println("Enter how much money you want to transfer:");
                                    long moneyToTransfer = scanner.nextLong();
                                    updateApp.doTransfer(moneyToTransfer, numberCheck, cardToTransfer);

                                }
                                case 4 -> System.out.println("close account");
                                case 5 -> {
                                    innerLoop = false;
                                    System.out.println("You have successfully logged out!");
                                }
                                case 0 -> {
                                    innerLoop = false;
                                    outerLoop = false;
                                    System.out.println("Buy!");
                                    exitApplication(ctx);
                                }
                                default -> System.out.println("Wrong variant");
                            }
                        }
                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                    innerLoop = true;
                }
                case 0 -> {
                    outerLoop = false;
                    System.out.println("Buy!");
                    exitApplication(ctx);
                }
                default -> System.out.println("Wrong variant");
            }
        }
    }
}
