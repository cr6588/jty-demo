package com.jty.db.service;

import com.jty.sys.bean.DatabaseInstance;

public interface ComDbCacheSer {
    public void addComDbCache(String mark,Long comId, DatabaseInstance ins) throws Exception;
}
