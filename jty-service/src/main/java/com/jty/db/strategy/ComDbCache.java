package com.jty.db.strategy;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class ComDbCache {
    public static Map<Long, String> companyIdMarkCache = new Hashtable<>();//comId, databaseTable.mark;
    public static Map<Long, String> companyIdInsCache = new Hashtable<>();//comId, dataSourceCache.key;
    public static List<String> orderActualTables = new ArrayList<>();
    public static List<String> orderGoodsActualTables = new ArrayList<>();
    public static List<String> goodsActualTables = new ArrayList<>();
    public static Map<String, DataSource> dataSourceCache = new Hashtable<>(); //key:eg:localhost:3306/order, c3p0
}
