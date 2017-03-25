package com.jty.sys.bean;

import java.io.Serializable;
import java.util.Date;

public class DatabaseInstance implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6123155303028925286L;

    private Long id;

    private Long dbId;

    private String dbName;

    private String username;

    private String password;

    private Integer dbStatus;

    private Integer dbLevel;

    private Integer module;

    private String remarks;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private Date updateUser;

    private Database database;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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

    public Integer getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(Integer dbStatus) {
        this.dbStatus = dbStatus;
    }

    public Integer getDbLevel() {
        return dbLevel;
    }

    public void setDbLevel(Integer dbLevel) {
        this.dbLevel = dbLevel;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Date updateUser) {
        this.updateUser = updateUser;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public DatabaseInstance() {
    }

    public DatabaseInstance(Long dbId, String dbName, String username, String password, Integer dbStatus, Integer dbLevel, Integer module, String remarks) {
        super();
        this.dbId = dbId;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.dbStatus = dbStatus;
        this.dbLevel = dbLevel;
        this.module = module;
        this.remarks = remarks;
    }

}