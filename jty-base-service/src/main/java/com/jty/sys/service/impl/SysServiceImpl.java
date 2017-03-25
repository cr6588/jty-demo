package com.jty.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.Database;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.sys.bean.DatabaseTable;
import com.jty.sys.dao.SysDao;
import com.jty.sys.service.SysSer;
import com.jty.web.bean.PagerInfo;

@Service
public class SysServiceImpl implements SysSer {

    private static Logger logger = LoggerFactory.getLogger(SysServiceImpl.class);
    @Autowired
    SysDao sysDao;

    @Override
    public List<CompanyDb> getCompanyDbList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return sysDao.getCompanyDbList(param, pager);
    }

    @Override
    public CompanyDb getCompanyDb(Map<String, Object> param) throws Exception {
        return sysDao.getCompanyDb(param);
    }

    @Override
    public void addCompanyDb(CompanyDb companyDb) throws Exception {
        sysDao.addCompanyDb(companyDb);
    }

    @Override
    public void updateCompanyDb(CompanyDb companyDb) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCompanyDb(Long id) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getCompanyDbListCnt(Map<String, Object> params) throws Exception {
        return sysDao.getCompanyDbListCnt(params);
    }

    @Override
    public List<Database> getDatabaseList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return sysDao.getDatabaseList(param, pager);
    }

    @Override
    public Database getDatabase(Map<String, Object> param) throws Exception {
        return sysDao.getDatabase(param);
    }

    @Override
    public Database getDatabaseByCompanyDbId(Long companyDbId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", companyDbId);
        DatabaseTable dbTab = sysDao.getDatabaseTable(param);
        param.put("id", dbTab.getDbInsId());
        DatabaseInstance ins = sysDao.getDatabaseInstance(param);
        param.put("id", ins.getDbId());
        Database db = sysDao.getDatabase(param);
        return db;
    }

    @Override
    public void addDatabase(Database database) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateDatabase(Database database) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteDatabase(Long id) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getDatabaseListCnt(Map<String, Object> params) throws Exception {
        return sysDao.getDatabaseListCnt(params);
    }

    @Override
    public List<DatabaseInstance> getDatabaseInstanceList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return sysDao.getDatabaseInstanceList(param, pager);
    }

    @Override
    public DatabaseInstance getDatabaseInstance(Map<String, Object> param) throws Exception {
        DatabaseInstance ins = sysDao.getDatabaseInstance(param);
        param.clear();
        param.put("id", ins.getDbId());
        ins.setDatabase(sysDao.getDatabase(param));
        return ins;
    }

    @Override
    public void addDatabaseInstance(DatabaseInstance databaseInstance) throws Exception {
        sysDao.addDatabaseInstance(databaseInstance);
    }

    @Override
    public void updateDatabaseInstance(DatabaseInstance databaseInstance) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteDatabaseInstance(Long id) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getDatabaseInstanceListCnt(Map<String, Object> params) throws Exception {
        return sysDao.getDatabaseInstanceListCnt(params);
    }

    @Override
    public List<DatabaseTable> getDatabaseTableList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return sysDao.getDatabaseTableList(param, pager);
    }

    @Override
    public DatabaseTable getDatabaseTable(Map<String, Object> param) throws Exception {
        return sysDao.getDatabaseTable(param);
    }

    @Override
    public void addDatabaseTable(DatabaseTable databaseTable) throws Exception {
        sysDao.addDatabaseTable(databaseTable);
    }

    @Override
    public void updateDatabaseTable(DatabaseTable databaseTable) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteDatabaseTable(Long id) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getDatabaseTableListCnt(Map<String, Object> params) throws Exception {
        return sysDao.getDatabaseTableListCnt(params);
    }

}
