package com.jty.sys.dao;

import java.util.List;
import java.util.Map;

import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.Database;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.sys.bean.DatabaseTable;
import com.jty.web.bean.PagerInfo;

public interface SysDao {

    //CompanyDb
    List<CompanyDb> getCompanyDbList(Map<String, Object> param, PagerInfo pager) throws Exception;

    CompanyDb getCompanyDb(Map<String, Object> param) throws Exception;

    void addCompanyDb(CompanyDb companyDb) throws Exception;
//
//    void updateCompanyDb(CompanyDb companyDb) throws Exception;
//
//    void deleteCompanyDb(Long id) throws Exception;
//
    Integer getCompanyDbListCnt(Map<String, Object> params) throws Exception;

    //Database
    List<Database> getDatabaseList(Map<String, Object> param, PagerInfo pager) throws Exception;

    Database getDatabase(Map<String, Object> param) throws Exception;

//    void addDatabase(Database database) throws Exception;
//
//    void updateDatabase(Database database) throws Exception;
//
//    void deleteDatabase(Long id) throws Exception;
//
    Integer getDatabaseListCnt(Map<String, Object> params) throws Exception;

    //DatabaseInstance
    List<DatabaseInstance> getDatabaseInstanceList(Map<String, Object> param, PagerInfo pager) throws Exception;

    DatabaseInstance getDatabaseInstance(Map<String, Object> param) throws Exception;
//
    void addDatabaseInstance(DatabaseInstance databaseInstance) throws Exception;
//
//    void updateDatabaseInstance(DatabaseInstance databaseInstance) throws Exception;
//
//    void deleteDatabaseInstance(Long id) throws Exception;
//
    Integer getDatabaseInstanceListCnt(Map<String, Object> params) throws Exception;

    //DatabaseTable
    List<DatabaseTable> getDatabaseTableList(Map<String, Object> param, PagerInfo pager) throws Exception;

    DatabaseTable getDatabaseTable(Map<String, Object> param) throws Exception;

    DatabaseTable getDatabaseTableById(Long id) throws Exception;

    void addDatabaseTable(DatabaseTable databaseTable) throws Exception;
//
//    void updateDatabaseTable(DatabaseTable databaseTable) throws Exception;
//
//    void deleteDatabaseTable(Long id) throws Exception;
//
    Integer getDatabaseTableListCnt(Map<String, Object> params) throws Exception;

}
