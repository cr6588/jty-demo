package com.jty.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UidUtil implements Runnable {

    private static final String URL = "URL";
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://URL/jty_order?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Long getUid(String url, String username, String password) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            con = DriverManager.getConnection(JDBC_URL.replace(URL, url), username, password);
            String sql = "REPLACE INTO uid_sequence (stub) VALUES ('a');";
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            Long uid = null;
            while (rs.next()) {
                uid = rs.getLong(1);
            }
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        // System.out.println("ss");
        Long uid;
        try {
            uid = getUid("localhost:3306", "dev", "dev");
            System.out.println("uid : " + uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new UidUtil());
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
