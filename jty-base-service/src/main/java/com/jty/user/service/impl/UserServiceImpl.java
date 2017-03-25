package com.jty.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jty.db.service.ComDbCacheSer;
import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.Database;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.sys.bean.DatabaseTable;
import com.jty.sys.dao.SysDao;
import com.jty.user.bean.User;
import com.jty.user.dao.UserDao;
import com.jty.user.service.UserSer;
import com.jty.web.bean.Constant;
import com.jty.web.bean.PagerInfo;
import com.jty.web.util.FileUtil;
import com.jty.web.util.JDBC;
import com.jty.web.util.UidUtil;

public class UserServiceImpl implements UserSer {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UidUtil uidUtil = new UidUtil("localhost:3306", "dev", "dev", "jty_uid_sequence", "user_id_sequence");;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysDao sysDao;

    public List<User> getUserList(Map<String, Object> param, PagerInfo pager) throws Exception {
        return userDao.getUserList(param, pager);
    }

    public User getUser(Map<String, Object> param) throws Exception {
        return userDao.getUser(param);
    }

    public String getDatabaseNameByUser(User user) {
        return "jty_order_x";
    }

    public String getTableMarkByUser(User user) {
        return user.getId() + "";
    }
    
    public void addUser(User user) throws Exception {
        user.setId(uidUtil.getUid());
        userDao.addUser(user);

        Map<String, Object> param = new HashMap<>();
        param.put("dbStatus", Constant.Db.Status.normal);
        param.put("module", Constant.Module.Order.index);
        PagerInfo pager = new PagerInfo(1,1); //查询第一页第一条ins,sql中by db_level排序 
        Database db = null;
        String dbName = null;
        DatabaseInstance ins = null;
        List<DatabaseInstance> insList = sysDao.getDatabaseInstanceList(param, pager);
        if(insList != null && insList.size() !=0) {
            ins = insList.get(0);
            if(ins != null) {
                dbName = ins.getDbName();
                param.clear();
                param.put("id", ins.getDbId());
                db = sysDao.getDatabase(param);
            } else {
                db = getMaxLevelDb();
            }
        } else {
            db = getMaxLevelDb();
        }
        if(db == null) {
            throw new Exception("jty_pub_database中没有可用数据库");
        }
        JDBC jdbc = new JDBC("jdbc:mysql://" + db.getIpAddress() + ":" + db.getPort() + "?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", db.getUsername(), db.getPassword());
        if(dbName == null) {
            dbName = getDatabaseNameByUser(user);
            jdbc.execute("create DATABASE " + dbName);
            ins = new DatabaseInstance(db.getId(), dbName, db.getUsername(), db.getPassword(), 1, 0, Constant.Module.Order.index, "");
            sysDao.addDatabaseInstance(ins);
        }
        String mark = getTableMarkByUser(user);
        String sqlPathPrefix = this.getClass().getResource("/") == null ? null : this.getClass().getResource("/").getPath() + "sql/";
        String sql = null;
        if(sqlPathPrefix == null) {
            sql = FileUtil.readTxtFile2StrByStringBuilder(this.getClass(), "/sql/jty_order_0.sql").replace("COMPANY_DATABASE", dbName).replace("COMPANY_MARK", mark);
        } else {
            sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order_0.sql").replace("COMPANY_DATABASE", dbName).replace("COMPANY_MARK", mark);
        }
        jdbc.execute(sql);
        DatabaseTable dbTable = new DatabaseTable(ins.getId(), 1, 0, Constant.Module.Order.index, mark, "");
        sysDao.addDatabaseTable(dbTable);
        CompanyDb comDb = new CompanyDb(user.getId(), dbTable.getId(), Constant.Module.Order.index, "");
        sysDao.addCompanyDb(comDb);
    }

    public Database getMaxLevelDb() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("dbStatus", Constant.Db.Status.normal);
        param.put("module", Constant.Module.Order.index);
        param.put("dbType", Constant.Db.Type.mysql);
        PagerInfo pager = new PagerInfo(1,1);
        List<Database> dbs = sysDao.getDatabaseList(param, pager);
        if(dbs != null && !dbs.isEmpty()) {
            return dbs.get(0);
        }
        return null;
    }

    public void updateUser(User user) throws Exception {
        userDao.updateUser(user);
    }

    public void deleteUser(Long id) throws Exception {
        userDao.deleteUser(id);
    }

    public Integer getUserListCnt(Map<String, Object> params) throws Exception {
        return userDao.getUserListCnt(params);
    }
}
