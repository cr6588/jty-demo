package com.jty.db.strategy.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import com.jty.db.DynamicDataSource;
import com.jty.db.strategy.ComDbCache;
import com.jty.sys.bean.CompanyDb;
import com.jty.sys.service.SysSer;
import com.jty.web.bean.Constant;

//分片键值与action的入口参数一定要统一，主键参数一定使用Long,Map<String, Object> params = new HashMap<>();params.put("id", 7l);
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    SysSer sysSer;
    
    public ModuloDatabaseShardingAlgorithm() {
        super();
    }

    public ModuloDatabaseShardingAlgorithm(SysSer sysSer) {
        super();
        this.sysSer = sysSer;
    }

    public String doEqualSharding(final Collection<String> dataBaseName, final ShardingValue<Long> shardingValue) {
        Long value = shardingValue.getValue();
        if(ComDbCache.companyIdInsCache.get(value) == null) {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("comId", value);
                map.put("module", Constant.Module.Order.index);
                CompanyDb comDb = sysSer.getCompanyDb(map);
                DynamicDataSource.loadComDb(comDb, sysSer);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
        String daName = ComDbCache.companyIdInsCache.get(value);
        if (daName != null) {
            return daName;
        }
        throw new IllegalArgumentException();
    }

    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
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
