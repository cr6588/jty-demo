package com.jty.sys.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CompanyDb implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1724886238347308168L;

    private Long id;

    private Long comId;

    private Long tableId;

    private Integer module;

    private String remarks;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    List<DatabaseTable> databaseTables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public List<DatabaseTable> getDatabaseTables() {
        return databaseTables;
    }

    public void setDatabaseTables(List<DatabaseTable> databaseTables) {
        this.databaseTables = databaseTables;
    }

    public CompanyDb() {
        super();
    }

    public CompanyDb(Long comId, Long tableId, Integer module, String remarks) {
        super();
        this.comId = comId;
        this.tableId = tableId;
        this.module = module;
        this.remarks = remarks;
    }

    
}