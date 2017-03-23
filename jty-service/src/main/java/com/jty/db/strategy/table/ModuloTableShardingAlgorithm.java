package com.jty.db.strategy.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.jty.db.DataSourceAspect;
import com.jty.db.DynamicDataSourceHolder;
import com.jty.db.strategy.ComDbCache;
import com.jty.sys.bean.CompanyDb;
import com.jty.sys.bean.DatabaseTable;
import com.jty.sys.service.SysSer;

public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {
    private static Logger logger = LoggerFactory.getLogger(ModuloTableShardingAlgorithm.class);
    /**
    *  select * from t_order from t_order where order_id = 11 
    *          └── SELECT *  FROM t_order_1 WHERE order_id = 11
    *  select * from t_order from t_order where order_id = 44
    *          └── SELECT *  FROM t_order_0 WHERE order_id = 44
    */
    public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        if(shardingValue.getValue() != null) {
            Long value = shardingValue.getValue();
            for (String each : tableNames) {
                if (each.endsWith(ComDbCache.companyIdMarkCache.get(value))) { //comId,module相同，mark不同的数据多条非法。即一个用户在一个库里的订单表只有1张
                  return each;
                }
            }
        }
        throw new IllegalArgumentException();
    }
    
    /**
    *  select * from t_order from t_order where order_id in (11,44)  
    *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44) 
    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44) 
    *  select * from t_order from t_order where order_id in (11,13,15)  
    *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)  
    *  select * from t_order from t_order where order_id in (22,24,26)  
    *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26) 
    */
    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 4 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }
    
    /**
    *  select * from t_order from t_order where order_id between 10 and 20 
    *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20 
    *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20 
    */
    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 4 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

}
