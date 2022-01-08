package com.shchur.banking;

import java.sql.*;


public class DeleteApp {

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

    public void addIncome(long income, String numberCheck) {
        String sql = "UPDATE card SET balance = balance + " + income + " WHERE number = " + numberCheck;

        try (Connection con = this.connect()) {
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doTransfer(long money, String cardOut, String cardTo) {
        String outCome = "UPDATE card SET balance = balance - " + money + " WHERE number = " + cardOut;

        String inCome = "UPDATE card SET balance = balance + " + money + " WHERE number = " + cardTo;



        try (Connection con = this.connect()) {

            con.setAutoCommit(false);

            Savepoint savepoint1 = con.setSavepoint();

            try (PreparedStatement pstm = con.prepareStatement(outCome)) {
                pstm.executeUpdate();
            }

            try (PreparedStatement pstm = con.prepareStatement(inCome)) {
                pstm.executeUpdate();
            }

            if ((selectApp.selectBalance(cardOut) - money) < 0) {
                con.rollback(savepoint1);
                System.out.println("Not enough money!");
            } else if (cardOut.equals(cardTo)) {
                con.rollback(savepoint1);
                System.out.println("You can't transfer money to the same account!");
            } else if (!card.checkCard(cardTo)) {
                con.rollback(savepoint1);
                System.out.println("Probably you made a mistake in the card number. Please try again!");
            } else if (!selectApp.selectAllNumbers().contains(cardTo)) {
                con.rollback(savepoint1);
                System.out.println("Such a card does not exist.");
            } else {
                System.out.println("Success!");
            }

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
