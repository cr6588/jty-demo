package com.jty.db;
import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jty.db.strategy.ComDbCache;
import com.jty.db.strategy.table.ModuloDatabaseShardingAlgorithm;
import com.jty.db.strategy.table.ModuloTableShardingAlgorithm;
import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.sys.bean.DatabaseTable;
import com.jty.sys.service.SysSer;
import com.jty.web.bean.Constant;
import com.jty.web.util.ReflectUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceAspect {

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

    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
    @Autowired
    SysSer sysSer;
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
        if (slaveMethodPattern.isEmpty()) {
            // 当前Spring容器中没有配置事务策略，采用方法名匹配方式
            // isSlave = isSlave(methodName);
        } else {
            // 使用策略规则匹配
            for (String mappedName : slaveMethodPattern) {
                if (isMatch(methodName, mappedName)) {
                    Object[] args = point.getArgs();
                    if (methodName.contains("Order") || methodName.contains("Goods")) {
                        Map<String, Object> params = (Map<String, Object>) args[0];
                        Long comId = (Long) params.get("userId");
                        String module = null;
                        if(methodName.contains("Order") || methodName.contains("Goods")) {
                            module = Constant.Module.Order.name;
                        }
                    }
                    break;
                }
            }
        }
    }

    public String initDataSource(Long comId, String module, Map<Object, Object> targetDataSources, List<DatabaseInstance> insList) throws Exception, PropertyVetoException {
        String dataSourceKey;
//        JDBC jdbc = new JDBC("jdbc:mysql://" + db.getIpAddress() + ":" + db.getPort() + "?allowMultiQueries=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", db.getUsername(), db.getPassword());
//        String databaseName = db.getdb
//        String mark = comId + "";
//        if(!jdbc.existDb(databaseName)) {
//            String sqlPathPrefix = this.getClass().getResource("/").getPath() + "sql/";
//            String sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order_0.sql").replace("COMPANY_MARK", mark);
//            boolean createResult = jdbc.createDb(databaseName, sql); //创建数据库，验证数据库是否创建成功
//            if(!createResult) {
//                throw new Exception("create " + databaseName + " fail!");
//            }
//        }
//        if(!jdbc.existTable(databaseName)) {
//            String sqlPathPrefix = this.getClass().getResource("/").getPath() + "sql/";
//            String sql = FileUtil.readTxtFile2StrByStringBuilder(sqlPathPrefix + "jty_order_0.sql").replace("COMPANY_MARK", mark);
//            boolean createResult = jdbc.createDb(databaseName, sql); //创建数据库，验证数据库是否创建成功
//            if(!createResult) {
//                throw new Exception("create " + databaseName + " fail!");
//            }
//        }
        //创建数据库创建表完成
//        DatabaseInstance databaseInstance = new DatabaseInstance(db.getId(), databaseName, db.getUsername(), db.getPassword(), 1, 0, ORDER_MODULE_INDEX, "");
//        sysSer.addDatabaseInstance(databaseInstance);
//        DatabaseTable dbTable = new DatabaseTable(databaseInstance.getId(), 1, 0, ORDER_MODULE_INDEX, mark, "");
//        sysSer.addDatabaseTable(dbTable);
//        CompanyDb comDb = new CompanyDb(comId, dbTable.getId(), ORDER_MODULE_INDEX, "");
//        sysSer.addCompanyDb(comDb);
//        DataSource moduleDataSource = initC3p0DataSource(db.getIpAddress() + ":" + db.getPort(), databaseName, db.getUsername(), db.getPassword());
        DataSource moduleDataSource = initShardingDataSource(insList);
        dataSourceKey = "orderSer";
        if(moduleDataSource != null) {
            targetDataSources.put(dataSourceKey, moduleDataSource);
            dataSource.afterPropertiesSet();// 通知spring更改了targetDataSources。此方法允许bean实例仅在所有bean属性都已设置时执行初始化，并在配置不正确的情况下抛出异常。
        } else {
            throw new Exception("init data source error");
        }
        return dataSourceKey;
    }

    private DataSource initShardingDataSource(List<DatabaseInstance> insList) {
        DataSource dataSource = null;
        try {
            if(insList != null) {
                for (DatabaseInstance ins : insList) {
                    String url = ins.getDatabase().getIpAddress() + ":" + ins.getDatabase().getPort();
                    ComboPooledDataSource ds = initC3p0DataSource(url, ins.getDbName(), ins.getUsername(), ins.getPassword()); //初始化数据库连接池， throws PropertyVetoException
                    String key = url + "/" + ins.getDbName();
                    ComDbCache.dataSourceCache.put(key, ds);
                }
            }
            DataSourceRule dataSourceRule = new DataSourceRule(ComDbCache.dataSourceCache);
            TableRule orderTableRule = TableRule.builder("t_order")
                .actualTables(ComDbCache.orderActualTables)
                .dataSourceRule(dataSourceRule)
                .build();
            TableRule orderGoodsTableRule = TableRule.builder("order_goods")
                .actualTables(ComDbCache.orderGoodsActualTables)
                .dataSourceRule(dataSourceRule)
                .build();
            
            TableRule goodsTableRule = TableRule.builder("goods")
                .actualTables(ComDbCache.goodsActualTables)
                .dataSourceRule(dataSourceRule)
                .build();

            ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule, orderGoodsTableRule, goodsTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm()))
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
