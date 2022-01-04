package com.shchur.banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectApp {

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

    public String selectNumber() {
        String sql = "SELECT number FROM card WHERE number is number ORDER BY ROWID DESC LIMIT 1";

        String res = "";
        try (Connection con = this.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            res = rs.getString("number");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String selectPin() {
        String sql = "SELECT pin FROM card WHERE pin is pin ORDER BY ROWID DESC LIMIT 1";
        String res = "";
        try (Connection con = this.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            res = rs.getString("pin");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<String> selectAllNumbers() {
        String sql = "SELECT number FROM card";

        List<String> allNumbers = new ArrayList<>();
        try (Connection con = this.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                allNumbers.add(rs.getString("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allNumbers;
    }

    public List<String> selectAllPins() {
        String sql = "SELECT pin FROM card";
        List<String> allPins = new ArrayList<>();
        try (Connection con = this.connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                allPins.add(rs.getString("pin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPins;
    }
}
