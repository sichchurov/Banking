package com.shchur.banking;

import java.util.Arrays;
import java.util.Random;

public class Card {
    private String cardNumber;
    private String pin;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String createNumber(Random cardNum) {
        String card = String.format("400000%d%03d%03d%02d",
                cardNum.nextInt(1),
                cardNum.nextInt(1000),
                cardNum.nextInt(1000),
                cardNum.nextInt(100));

        int sum = 0;
        String[] strings = card.split("");
        long[] digits = new long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            sum = getSum(sum, strings, digits, i);
        }
        int count = 0;
        if (sum % 10 != 0) {
            count = 10 - sum % 10;
        }
        return String.format("%s%d", card, count);
    }

    public String createPinCode(Random pinNum) {
        int lower = 1000;
        int upper = 9999;
        int intervalLength = upper - lower + 1;
        return String.valueOf(pinNum.nextInt(intervalLength) + lower);
    }

    public boolean checkCard(String newCard) {
        int sum = 0;
        String[] strings = newCard.substring(0, newCard.length() - 1).split("");
        long[] digits = new long[strings.length];
        for (int j = 0; j < strings.length; j++) {
            sum = getSum(sum, strings, digits, j);
        }
        int count = 0;
        if (sum % 10 != 0) {
            count = 10 - sum % 10;
        }
        return (Arrays.toString(strings).replaceAll("[\\[\\],\\s]", "") + count).equals(newCard);
    }

    private int getSum(int sum, String[] strings, long[] digits, int i) {
        digits[i] = Long.parseLong(strings[i]);
        if (i % 2 == 0) {
            digits[i] *= 2;
        }
        if (digits[i] > 9) {
            digits[i] -= 9;
        }
        sum += digits[i];
        return sum;
    }
}
