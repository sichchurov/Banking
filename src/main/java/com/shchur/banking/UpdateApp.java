package com.shchur.banking;

import java.sql.*;


public class UpdateApp {

    private final SelectApp selectApp = new SelectApp();
    private final Card card = new Card();

    private Connection connect() {
        String url = "jdbc:sqlite:banking";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void addIncome(long money, String cardIn) {
        String income = "UPDATE card SET balance = balance + " + money + " WHERE number = " + cardIn;

        try (Connection con = this.connect()) {
            try (PreparedStatement pstm = con.prepareStatement(income)) {
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOutcome(long money, String cardOut, String cardTo) {
        String outcome = "UPDATE card SET balance = balance - " + money + " WHERE number = " + cardOut;

        String transfer = "UPDATE card SET balance = balance + " + money + " WHERE number = " + cardTo;

        try (Connection con = this.connect()) {

            con.setAutoCommit(false);

            Savepoint savepoint = con.setSavepoint();

            try (PreparedStatement pstm = con.prepareStatement(outcome)) {
                pstm.executeUpdate();
            }

            try (PreparedStatement pstm = con.prepareStatement(transfer)) {
                pstm.executeUpdate();
            }

            if ((selectApp.selectBalance(cardOut) - money) < 0) {
                con.rollback(savepoint);
                System.out.println("Not enough money!");
            } else {
                System.out.println("Success");
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean doTransfer(String cardOut, String cardTo) {
        if (cardOut.equals(cardTo)) {
            System.out.println("You can't transfer money to the same account!");
            return false;
        }
        if (!card.checkCard(cardTo)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return false;
        }
        if (!selectApp.selectAllNumbers().contains(cardTo)) {
            System.out.println("Such a card does not exist.");
            return false;
        }
        return true;
    }
}
