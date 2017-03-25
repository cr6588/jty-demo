package com.jty.web.util;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Util {
    public static ComboPooledDataSource initC3p0DataSource(String url, String dbName, String username, String password) throws PropertyVetoException {
        ComboPooledDataSource userDb = new ComboPooledDataSource();
        userDb.setDriverClass("com.mysql.jdbc.Driver");
        userDb.setJdbcUrl("jdbc:mysql://" + url + "/" + dbName);
        userDb.setUser(username);
        userDb.setPassword(password);
        userDb.setInitialPoolSize(2);
        userDb.setMaxPoolSize(5);
        return userDb;
    }
}
