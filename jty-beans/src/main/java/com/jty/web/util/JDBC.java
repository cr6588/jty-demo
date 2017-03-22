package com.jty.web.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {

    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String driverName = MYSQL_JDBC_DRIVER;
    private String url;
    private String port;
    private String username;
    private String password;
    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private CallableStatement cstmt = null;
    private ResultSet rs = null;
    private String sql;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public JDBC(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public JDBC(String url, String port, String username, String password) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public Connection getCon() throws Exception {
        if (con == null || con.isClosed()) {
            this.loadDriver();
            if( password == null ){
                con = DriverManager.getConnection(url);
            } else {
                con = DriverManager.getConnection(url, username, password);
            }
            con.setAutoCommit(false);
        }
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void loadDriver() throws Exception {
        Class.forName(driverName);
    }

    public Statement getStmt() throws Exception {
        if (stmt == null || stmt.isClosed()) {
            stmt = this.getCon().createStatement();
        }
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public PreparedStatement getPstmt() throws Exception {
        if (pstmt == null || pstmt.isClosed()) {
            pstmt = this.getCon().prepareStatement(sql);
        }
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public CallableStatement getCstmt() throws Exception {
        if (cstmt == null || cstmt.isClosed()) {
            cstmt = this.getCon().prepareCall(sql);
        }
        return cstmt;
    }

    public void setCstmt(CallableStatement cstmt) {
        this.cstmt = cstmt;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void close() throws Exception {
        if (this.rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (cstmt != null) {
            cstmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    /**
     * 初始化用户数据
     * 设置root密码为newPassword，删除任意@localhost用户，增加用户名密码为dev的用户，刷新权限
     * @param url mysql地址 eg:31.56.145.45 默认localhost
     * @param port 端口 默认3306
     * @param oldPassword 旧密码 默认null
     * @param newPassword 新密码
     * @throws Exception 
     */
    public void initUser(String newPassword) throws Exception {
        if(url == null || url.equals("")) {
            url = "localhost";
        }
        if(password == null || password.equals("")) {
            url = url = "jdbc:mysql://" + url + ":" + (port == null || port.equals("") ? "3306" : port) + "/mysql?user=root&password=&allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
        } else {
            url = "jdbc:mysql://" + url + ":" + (port == null || port.equals("") ? "3306" : port) + "/mysql?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
        }
        StringBuilder sql = new StringBuilder("UPDATE user SET password=PASSWORD('" + newPassword + "') WHERE user='root';");
        try {
            sql.append("DELETE FROM user Where User='' and Host='localhost';"); //删除 @localhost用户
            sql.append(getAddDevUserSql());
            sql.append("FLUSH PRIVILEGES");
            setSql(sql.toString());
            getPstmt().execute(getSql());
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("init user error！");
        } finally {
            try {
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getAddDevUserSql(){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE USER 'dev'@'%' IDENTIFIED BY 'dev';");
        sb.append("GRANT GRANT OPTION ON *.* TO 'dev'@'%';");
        sb.append("GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, RELOAD, SHUTDOWN, PROCESS, FILE, REFERENCES, INDEX, ALTER, SHOW DATABASES, SUPER, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, REPLICATION SLAVE, REPLICATION CLIENT, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, CREATE USER, EVENT, TRIGGER ON *.* TO 'dev'@'%';");
        return sb.toString();
    }

    public boolean createDb(String dbName, String sql) {
        try {
            getStmt().execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existDb(dbName);
    }

    public boolean existDb(String userDbName) {
        // JDBC jdbc = new
        // JDBC("jdbc:mysql://118.123.8.140:3306?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8",
        // "root", "363505e8956fad91");
        String sql = "show databases;";
        try {
            ResultSet rs = getStmt().executeQuery(sql);
            while (rs.next()) {
                String existDbName = rs.getString(1);
                if (existDbName.equals(userDbName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existTable(String tableName) {
        // TODO Auto-generated method stub
        return false;
    }

}
