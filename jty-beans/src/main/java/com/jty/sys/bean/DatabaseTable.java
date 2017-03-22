package com.jty.sys.bean;

import java.io.Serializable;
import java.util.Date;

public class DatabaseTable implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5350393471579044593L;

    private Long id;

    private Long dbInsId;

    private Integer dbStatus;

    private Integer dbLevel;

    private Integer module;

    private String mark;

    private String remarks;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDbInsId() {
        return dbInsId;
    }

    public void setDbInsId(Long dbInsId) {
        this.dbInsId = dbInsId;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public DatabaseTable() {
    }

    public DatabaseTable(Long dbInsId, Integer dbStatus, Integer dbLevel, Integer module, String mark, String remarks) {
        this.dbInsId = dbInsId;
        this.dbStatus = dbStatus;
        this.dbLevel = dbLevel;
        this.module = module;
        this.mark = mark;
        this.remarks = remarks;
    }

}