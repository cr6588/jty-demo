package com.jty.db.service.impl;

import java.beans.PropertyVetoException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jty.db.service.ComDbCacheSer;
import com.jty.db.strategy.ComDbCache;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.web.bean.Constant;
import com.jty.web.util.C3p0Util;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class OrderCacheServiceImpl implements ComDbCacheSer {

    static Lock lock = new  ReentrantLock();

    @Override
    public void addComDbCache(String mark,Long comId, DatabaseInstance ins) throws Exception {
        lock.lock();
        try {
            ComDbCache.orderActualTables.add(Constant.Module.Order.logicTable[0] + "_" + mark);
            ComDbCache.orderGoodsActualTables.add(Constant.Module.Order.logicTable[1] + "_" + mark);
            ComDbCache.goodsActualTables.add(Constant.Module.Order.logicTable[2] + "_" + mark);
            //构建企业id，表规则缓存
            ComDbCache.companyIdMarkCache.put(comId, mark);
            String url = ins.getDatabase().getIpAddress() + ":" +ins.getDatabase().getPort();
            String c3p0Key = url + "/" + ins.getDbName();
            //构建数据源缓存
            addDsPool2Cache(ins, url, c3p0Key);
            //构建企业id，数据源缓存
            ComDbCache.companyIdInsCache.put(comId, c3p0Key);
        } finally {
            lock.unlock();
        }
    }

    public static void addDsPool2Cache(DatabaseInstance ins, String url, String c3p0Key) throws PropertyVetoException {
        if(ComDbCache.dataSourceCache.get(c3p0Key) == null) {
            ComboPooledDataSource c3p0 = C3p0Util.initC3p0DataSource(url, ins.getDbName(), ins.getUsername(), ins.getPassword()); //初始化数据库连接池， throws PropertyVetoException
            ComDbCache.dataSourceCache.put(c3p0Key, c3p0);
        }
    }

}
