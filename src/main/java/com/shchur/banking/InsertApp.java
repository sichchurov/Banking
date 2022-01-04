package com.shchur.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class InsertApp {

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

    public void insert(String cardNu, String pinNu, int balance) {
        String sql = "INSERT INTO CARD (number, pin, balance) VALUES (?,?,?)";

        try (Connection con = this.connect()) {
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setString(1, cardNu);
                pstm.setString(2, pinNu);
                pstm.setInt(3, balance);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
