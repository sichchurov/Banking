package com.shchur.banking;

import java.util.Scanner;

public class AccountActions {

    private final Scanner scanner = new Scanner(System.in);

    private final UpdateApp updateApp = new UpdateApp();

    public void makeIncome(String numberCard) {
        System.out.println("Enter income");
        long income = scanner.nextLong();
        updateApp.addIncome(income, numberCard);
        System.out.println("Income was added!");
    }

    public void makeTransfer(String numberCard) {
        System.out.println("Enter card number:");
        String cardToTransfer = scanner.next();
        if (updateApp.doTransfer(numberCard, cardToTransfer)) {
            System.out.println("Enter how much money you want to transfer:");
            long moneyToTransfer = scanner.nextLong();
            updateApp.addOutcome(moneyToTransfer, numberCard, cardToTransfer);
        }
    }
}
