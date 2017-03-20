package com.jty.db.strategy.table;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import com.jty.web.bean.ShardingParam;

//分片键值与action的入口参数一定要统一，主键参数一定使用Long,Map<String, Object> params = new HashMap<>();params.put("id", 7l);
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<ShardingParam> {

    public String doEqualSharding(final Collection<String> dataBaseName, final ShardingValue<ShardingParam> shardingValue) {
        for (String each : dataBaseName) {
            
            if (each.endsWith(shardingValue.getValue().getParam() % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<ShardingParam> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (ShardingParam value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<ShardingParam> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<ShardingParam> range = (Range<ShardingParam>) shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

}
