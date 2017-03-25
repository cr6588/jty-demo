package com.jty.db;

import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.jty.db.service.impl.OrderCacheServiceImpl;
import com.jty.db.strategy.ComDbCache;
import com.jty.db.strategy.table.ModuloDatabaseShardingAlgorithm;
import com.jty.db.strategy.table.ModuloTableShardingAlgorithm;
import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.DatabaseInstance;
import com.jty.sys.bean.DatabaseTable;
import com.jty.sys.service.SysSer;
import com.jty.web.bean.Constant;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public static Lock lock = new ReentrantLock();
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    SysSer sysSer;

    public void setSysSer(SysSer sysSer) {
        this.sysSer = sysSer;
    }

    @Override
    public Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceKey();
    }

    @PostConstruct
    public void init() throws Exception {
        Map<Object, Object> tar = new Hashtable<>();
        DataSource ds = null;
        try {
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("module", Constant.Module.Order.index);
            List<CompanyDb> comDbs = sysSer.getCompanyDbList(conditionMap, null);
            if(comDbs != null) {
                for (CompanyDb comDb : comDbs) {
                    loadComDb(comDb, sysSer);
                }
                ds = initShardingDataSource();
            }

            tar.put(DynamicDataSourceHolder.MASTER, ds);
            setTargetDataSources(tar);
            setDefaultTargetDataSource(ds);
            afterPropertiesSet();
            
        } catch (Exception e) {
            logger.error("初始化订单模块数据源失败!!!!!");
            e.printStackTrace();
            throw e;
        }
    }

    public static void loadComDb(CompanyDb comDb, SysSer sysSer) throws Exception, PropertyVetoException {
        lock.lock();
        try {
            Long comId = comDb.getComId();
            List<DatabaseTable> tabs = comDb.getDatabaseTables();
            Set<Long> insIds = new HashSet<>();
            if (tabs != null) {
                for (DatabaseTable tab : tabs) {
                    insIds.add(tab.getDbInsId());
                    // 构建逻辑表与物理表对应
//                    ComDbCache.orderActualTables.add(Constant.Module.Order.logicTable[0]);// + "_" + tab.getMark()
//                    ComDbCache.orderGoodsActualTables.add(Constant.Module.Order.logicTable[1]);
//                    ComDbCache.goodsActualTables.add(Constant.Module.Order.logicTable[2]);
                    // 构建企业id，表规则缓存
                    ComDbCache.companyIdMarkCache.put(comId, tab.getMark());
                }
            }
            Map<String, Object> conditionMap = new HashMap<>();
            for (Long insId : insIds) {
                conditionMap.put("id", insId);
                DatabaseInstance ins = sysSer.getDatabaseInstance(conditionMap);
                if (ins != null) {
                    String url = ins.getDatabase().getIpAddress() + ":" + ins.getDatabase().getPort();
                    String c3p0Key = url + "/" + ins.getDbName();
                    // 构建数据源缓存
                    OrderCacheServiceImpl.addDsPool2Cache(ins, url, c3p0Key);
                    // 构建企业id，数据源缓存
                    ComDbCache.companyIdInsCache.put(comId, c3p0Key);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private DataSource initShardingDataSource() throws PropertyVetoException {
        DataSource dataSource = null;
        DataSourceRule dataSourceRule = new DataSourceRule(ComDbCache.dataSourceCache);
        TableRule orderTableRule = TableRule.builder(Constant.Module.Order.logicTable[0])
//            .actualTables(ComDbCache.orderActualTables)
            .dynamic(true)
            .dataSourceRule(dataSourceRule)
            .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm(Constant.Module.Order.logicTable[0])))
            .build();
        TableRule orderGoodsTableRule = TableRule.builder(Constant.Module.Order.logicTable[1])
//            .actualTables(ComDbCache.orderGoodsActualTables)
            .dataSourceRule(dataSourceRule)
            .dynamic(true)
            .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm(Constant.Module.Order.logicTable[1])))
            .build();
        TableRule goodsTableRule = TableRule.builder(Constant.Module.Order.logicTable[2])
//            .actualTables(ComDbCache.goodsActualTables)
            .dataSourceRule(dataSourceRule)
            .dynamic(true)
            .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm(Constant.Module.Order.logicTable[2])))
            .build();
        ShardingRule shardingRule = ShardingRule.builder()
            .dataSourceRule(dataSourceRule)
            .tableRules(Arrays.asList(orderTableRule, orderGoodsTableRule, goodsTableRule))
            .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm(sysSer)))
            .build();
        dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }

}
