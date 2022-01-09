package com.shchur.banking;

import java.sql.*;


public class DeleteApp {

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

    public void deleteAccount(String numberCheck) {
        String sql = "DELETE FROM card WHERE number = " + numberCheck;

        try (Connection con = this.connect()) {
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.executeUpdate();
                System.out.println("The account has been closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
