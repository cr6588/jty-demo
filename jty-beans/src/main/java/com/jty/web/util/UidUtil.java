package com.jty.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UidUtil implements Runnable {

    public static final String DATABASENAME = "DATABASENAME";
    private static final String URL = "URL";
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://URL/DATABASENAME?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
    private String url;
    private String username;
    private String password;
    private String databaseName;
    private String tableName;

    public UidUtil() {
        
    }

    public UidUtil(String url, String username, String password, String databaseName, String tableName) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getUid() throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
            con = DriverManager.getConnection(JDBC_URL.replace(URL, url).replace(DATABASENAME, databaseName), username, password);
            String sql = "REPLACE INTO " + tableName + " (stub) VALUES ('a');";
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
            UidUtil uidUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "order_id_sequence");
            uid = getUid();
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
