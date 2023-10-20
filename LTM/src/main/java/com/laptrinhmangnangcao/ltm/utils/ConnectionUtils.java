package com.laptrinhmangnangcao.ltm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    private static String DB_URL = "jdbc:mysql://localhost:3306" +
            "/laptrinhmangnangcao";
    private static String USER = "root";
    private static String PASS = "trongnghia2k1";;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
