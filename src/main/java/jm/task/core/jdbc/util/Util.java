package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL = "jdbc:mysql://localhost:3306/midbtest";
    private final String PASSWORD = "root";
    private final String USERNAME = "root";

    public Connection getConnection() {
        Connection connection = null;
        System.out.println("Connection OK");
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
}
