package com.jty.db;

public class DynamicDataSourceHolder {
    public static final String MASTER = "master";

    // 使用ThreadLocal记录当前线程的数据源key
    private static ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     * @param key
     */
    public static void setDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 标记主库
     */
    public static void markMaster() {
        setDataSourceKey(MASTER);
    }

    public static void markDb(String dataSource) {
        holder.set(dataSource);
    }
}
