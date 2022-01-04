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

    private static final Random cardNum = new Random();
    private static final Random pinNum = new Random();

    private static void exitApplication(ConfigurableApplicationContext ctx) {
        int exitCode = SpringApplication.exit(ctx, () -> 0);
        System.exit(exitCode);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BankingApplication.class, args);

        int outerVariant;
        do {
            Greeting hello = new Greeting();
            System.out.println(hello.setLine1("1. Create an account"));
            System.out.println(hello.setLine2("2. Log into account"));
            System.out.println(hello.setLine2("0. Exit"));
            outerVariant = scanner.nextInt();
            System.out.println();
            switch (outerVariant) {
                case 1 -> {
                    Card card = new Card();
                    Card pin = new Card();
                    String cardNu = card.createNumber(cardNum);
                    String pinNu = pin.createPinCode(pinNum);

                    insertApp.insert(cardNu, pinNu, 0);
                    System.out.println("Your card has been created");
                    System.out.printf("Your card number: %n%s%n", selectApp.selectNumber());
                    System.out.printf("Your card PIN: %n%s%n", selectApp.selectPin());
                }
                case 2 -> {
                    System.out.println("Enter your card number:");
                    String cardCheck = scanner.next();
                    System.out.println("Enter your PIN:");
                    String pinCheck = scanner.next();
                    int innerVariant;
                    List<String> cardNu = selectApp.selectAllNumbers();
                    List<String> pinNu = selectApp.selectAllPins();
                    if (cardNu.contains(cardCheck) && pinNu.contains(pinCheck)) {
                        System.out.println("You have successfully logged in!");
                        do {
                            Greeting login = new Greeting();
                            System.out.println(login.setLine1("1. Balance"));
                            System.out.println(login.setLine2("2. Log out"));
                            System.out.println(login.setLine2("0. Exit"));
                            innerVariant = scanner.nextInt();
                            switch (innerVariant) {
                                case 1 -> System.out.println("Balance: 0");
                                case 2 -> System.out.println("You have successfully logged out!");
                                case 0 -> {
                                    System.out.println("Buy!");
                                    exitApplication(ctx);
                                }
                                default -> System.out.println("Wrong variant");
                            }
                        } while (innerVariant == 1);

                    } else {
                        System.out.println();
                        System.out.println("Wrong card number or PIN!");
                    }
                }
                case 0 -> {
                    System.out.println("Buy!");
                    exitApplication(ctx);
                }
                default -> System.out.println("Wrong variant");
            }
        } while (outerVariant != 0);
    }
}
