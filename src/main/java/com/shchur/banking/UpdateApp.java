package com.shchur.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateApp {

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

    public void update(int income, String numberCheck) {
        String sql = "UPDATE card SET balance = balance + " + income + " WHERE number = " + numberCheck;

        try (Connection con = this.connect()) {
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
