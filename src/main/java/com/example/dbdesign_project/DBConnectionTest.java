package com.example.dbdesign_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {
    // 데이터베이스 연결 정보
    private static final String URL = "jdbc:mysql://ls-f8dc441f16b317431a0c626ef5cd85578e5485cc.c9awuy0mqmj5.ap-northeast-2.rds.amazonaws.com:3306/list";
    private static final String USER = "dbmasteruser";
    private static final String PASSWORD = "98899889";

    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new SQLException("데이터베이스 자격 증명 또는 URL이 null입니다.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }
}
