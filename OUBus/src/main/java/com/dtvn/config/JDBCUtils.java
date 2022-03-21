package com.dtvn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    private static Connection conn;

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost;database=OUBus1;", "sa", "123456");
    }
}