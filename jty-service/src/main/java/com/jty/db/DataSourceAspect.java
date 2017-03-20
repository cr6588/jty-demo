package com.jty.db;
import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.jty.db.strategy.table.ModuloDatabaseShardingAlgorithm;
import com.jty.db.strategy.table.ModuloTableShardingAlgorithm;
import com.jty.web.util.FileUtil;
import com.jty.web.util.JDBC;
import com.jty.web.util.ReflectUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceAspect {

    private static final String ORDER_DATASOURCE_KEY = "order";

    private List<String> slaveMethodPattern = new ArrayList<String>();

    private static final String[] defaultSlaveMethodStart = new String[] { "query", "find", "get" };

    private String[] slaveMethodStart;

    private DynamicDataSource dataSource;

    public DynamicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DynamicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 读取事务管理中的策略
     * @param txAdvice
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception {
        if (txAdvice == null) {
            // 没有配置事务管理策略
            return;
        }
        // 从txAdvice获取到策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource)) {
            return;
        }
        // 使用反射技术获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
        nameMapField.setAccessible(true); // 设置该字段可访问
        // 获取nameMap的值
        Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField.get(matchTransactionAttributeSource);

        // 遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet()) {
            if (!entry.getValue().isReadOnly()) {// 判断之后定义了ReadOnly的策略才加入到slaveMethodPattern
                continue;
            }
            slaveMethodPattern.add(entry.getKey());
        }
    }

    public String getDataSourceKeyByModuleName(String moduleName) {
        if (moduleName == null || moduleName.equals("")) {
            return "master";
        }
        return moduleName;
    }

    public ComboPooledDataSource initC3p0DataSource(String url, String dbName, String username, String password) throws PropertyVetoException {
        ComboPooledDataSource userDb = new ComboPooledDataSource();
        userDb.setDriverClass("com.mysql.jdbc.Driver");
        userDb.setJdbcUrl("jdbc:mysql://" + url + "/" + dbName);
        userDb.setUser(username);
        userDb.setPassword(password);
        userDb.setInitialPoolSize(2);
        userDb.setMaxPoolSize(5);
        return userDb;
    }

    /**
     * 在进入Service方法之前执行
     * @param point 切面对象
     * @throws Exception 
     */
    public void before(JoinPoint point) throws Exception {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();

        // boolean isSlave = false;

        if (slaveMethodPattern.isEmpty()) {
            // 当前Spring容器中没有配置事务策略，采用方法名匹配方式
            // isSlave = isSlave(methodName);
        } else {
            // 使用策略规则匹配
            for (String mappedName : slaveMethodPattern) {
                if (isMatch(methodName, mappedName)) {
                    // isSlave = true;
                    Object[] args = point.getArgs();
//                    Map<String, Object> params = (Map<String, Object>) args[0];
                    if (true) {
                        String module = null;
                        if(methodName.contains("Order") || methodName.contains("Goods")) {
                            module = ORDER_DATASOURCE_KEY;
                        }
                        String dataSourceKey = getDataSourceKeyByModuleName(module);
                        Map<Object, Object> targetDataSources = (Map<Object, Object>) ReflectUtil.getFieldValue(dataSource, "targetDataSources");
                        if (targetDataSources.get(dataSourceKey) == null) {
                            String databaseName = getModuleDatabaseName(module);
                            String url = "localhost:3308", dbUsername = "dev", password = "dev";
                            JDBC jdbc = new JDBC("jdbc:mysql://" + url + "?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", dbUsername, password);
                            if(!jdbc.existDb(databaseName)) {   //判读数据库是否存在
                                String sqlPathPrefix = this.getClass().getResource("/").getPath() + "sql/";
                                String sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_goods.sql");
                                sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order.sql");
                                sql += FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_uid_sequence.sql");
                                boolean createResult = jdbc.createDb(databaseName, sql); //创建数据库，验证数据库是否创建成功
                                if(!createResult) {
                                    throw new Exception("create " + databaseName + " fail!");
                                }
                            }
                            DataSource moduleDataSource = initShardingDataSource(url, dbUsername, password);
                            if(moduleDataSource != null) {
                                targetDataSources.put(dataSourceKey, moduleDataSource);
                            }
                            dataSource.afterPropertiesSet();            // 通知spring更改了targetDataSources。此方法允许bean实例仅在所有bean属性都已设置时执行初始化，并在配置不正确的情况下抛出异常。
                        }
                        DynamicDataSourceHolder.markDb(dataSourceKey);  // 更改数据库连接
                    } else {
                        DynamicDataSourceHolder.markMaster();
                    }
                    break;
                }
            }
        }
        // if (isSlave) {
        // // 标记为读库
        // DynamicDataSourceHolder.markMaster();
        // } else {
        // // 标记为写库
        // DynamicDataSourceHolder.markMaster();
        // }
    }

    public DataSource initShardingDataSourceSingleDb(String url, String dbUsername, String password) {
        DataSource dataSource = null;
        try {
            ComboPooledDataSource jty_order_0 = initC3p0DataSource(url, "jty_goods_order_single_db", dbUsername, password); //初始化数据库连接池， throws PropertyVetoException

            Map<String, DataSource> orderDataSourceMap = new HashMap<>();
            orderDataSourceMap.put("jty_order_0", jty_order_0);
            DataSourceRule orderDataSourceRule = new DataSourceRule(orderDataSourceMap);

            TableRule orderTableRule = TableRule.builder("t_order")
                .actualTables(Arrays.asList("t_order"))
                .dataSourceRule(orderDataSourceRule)
                .build();
            TableRule orderGoodsTableRule = TableRule.builder("order_goods")
                .actualTables(Arrays.asList("order_goods"))
                .dataSourceRule(orderDataSourceRule)
                .build();
            TableRule goodsTableRule = TableRule.builder("goods")
                .actualTables(Arrays.asList("goods"))
                .dataSourceRule(orderDataSourceRule)
                .build();

            ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(orderDataSourceRule)
                .tableRules(Arrays.asList(orderTableRule, orderGoodsTableRule, goodsTableRule))
                .build();

            dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public DataSource initShardingDataSource(String url, String dbUsername, String password) {
        DataSource dataSource = null;
        try {
            ComboPooledDataSource jty_order_0 = initC3p0DataSource(url, "jty_order_0", dbUsername, password); //初始化数据库连接池， throws PropertyVetoException
            ComboPooledDataSource jty_order_1 = initC3p0DataSource(url, "jty_order_1", dbUsername, password);
            ComboPooledDataSource jty_goods_0 = initC3p0DataSource(url, "jty_goods_0", dbUsername, password);
            ComboPooledDataSource jty_goods_1 = initC3p0DataSource(url, "jty_goods_1", dbUsername, password);

            Map<String, DataSource> orderDataSourceMap = new HashMap<>();
            orderDataSourceMap.put("jty_order_0", jty_order_0);
            orderDataSourceMap.put("jty_order_1", jty_order_1);
            orderDataSourceMap.put("jty_goods_0", jty_goods_0);
            orderDataSourceMap.put("jty_goods_1", jty_goods_1);
            DataSourceRule orderDataSourceRule = new DataSourceRule(orderDataSourceMap);

            TableRule orderTableRule = TableRule.builder("t_order")
                .actualTables(Arrays.asList("jty_order_0.t_order_0", "jty_order_0.t_order_1", "jty_order_0.t_order_2", "jty_order_0.t_order_3", "jty_order_1.t_order_0", "jty_order_1.t_order_1", "jty_order_1.t_order_2", "jty_order_1.t_order_3"))
                .dataSourceRule(orderDataSourceRule)
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))
                .build();
            TableRule orderGoodsTableRule = TableRule.builder("order_goods")
                .actualTables(Arrays.asList("jty_order_0.order_goods_0", "jty_order_0.order_goods_1", "jty_order_0.order_goods_2", "jty_order_0.order_goods_3", "jty_order_1.order_goods_0", "jty_order_1.order_goods_1", "jty_order_1.order_goods_2", "jty_order_1.order_goods_3"))
                .dataSourceRule(orderDataSourceRule)
                .databaseShardingStrategy(new DatabaseShardingStrategy("order_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("goods_id", new ModuloTableShardingAlgorithm()))
                .build();
            
//            Map<String, DataSource> goodsDataSourceMap = new HashMap<>();
//            goodsDataSourceMap.put("jty_goods_0", jty_goods_0);
//            goodsDataSourceMap.put("jty_goods_1", jty_goods_1);
//            DataSourceRule goodsDataSourceRule = new DataSourceRule(goodsDataSourceMap);
            TableRule goodsTableRule = TableRule.builder("goods")
                .actualTables(Arrays.asList("jty_goods_0.goods_0", "jty_goods_0.goods_1", "jty_goods_0.goods_2", "jty_goods_0.goods_3", "jty_goods_1.goods_0", "jty_goods_1.goods_1", "jty_goods_1.goods_2", "jty_goods_1.goods_3"))
                .dataSourceRule(orderDataSourceRule)
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))
                .build();

            ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(orderDataSourceRule)
                .tableRules(Arrays.asList(orderTableRule, orderGoodsTableRule, goodsTableRule))
                .build();

            dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    private String getModuleDatabaseName(String moduleName) {
        return "jty_" + moduleName + "_0";
    }

    /**
     * 判断是否为读库
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        // StringUtils引入类与其他不一样
        return StringUtils.startsWithAny(methodName, getSlaveMethodStart());
    }

    /**
     * 通配符匹配 Return if the given method name matches the mapped name.
     * <p>
     * The default implementation checks for "xxx*", "*xxx" and "*xxx*" matches,
     * as well as direct equality. Can be overridden in subclasses.
     * @param methodName the method name of the class
     * @param mappedName the name in the descriptor
     * @return if the names match
     * @see org.springframework.util.PatternMatchUtils#simpleMatch(String,
     *      String)
     */
    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    /**
     * 用户指定slave的方法名前缀
     * @param slaveMethodStart
     */
    public void setSlaveMethodStart(String[] slaveMethodStart) {
        this.slaveMethodStart = slaveMethodStart;
    }

    public String[] getSlaveMethodStart() {
        if (this.slaveMethodStart == null) {
            // 没有指定，使用默认
            return defaultSlaveMethodStart;
        }
        return slaveMethodStart;
    }
}
