package com.jty.db.strategy.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.MultipleKeysTableShardingAlgorithm;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

/**
 * order_goods分表策略
 *
 */
public final class OrderGoodsShardingAlgorithm implements MultipleKeysTableShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue<?>> shardingValues) {
        Set<Integer> orderIdValueSet = getShardingValue(shardingValues, "order_id");
        Set<Integer> userIdValueSet = getShardingValue(shardingValues, "user_id");
    
        List<String> result = new ArrayList<>();
        /*
        userIdValueSet[10,11] + orderIdValueSet[101,102] => valueResult[[10,101],[10,102],[11,101],[11,102]]
         */
        Set<List<Integer>> valueResult = Sets.cartesianProduct(userIdValueSet, orderIdValueSet);
        for (List<Integer> value : valueResult) {
//            String suffix = Joiner.on("").join(value.get(0) % 2, value.get(1) % 2);
            String suffix = null;
            switch (value.get(0) % 2) {
                case 0:
                    switch (value.get(1) % 2) {
                        case 0:
                            suffix = "0";
                            break;
                        case 1:
                            suffix = "1";
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    switch (value.get(1) % 2) {
                        case 0:
                            suffix = "2";
                            break;
                        case 1:
                            suffix = "3";
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            for (String tableName : availableTargetNames) {
                if (tableName.endsWith(suffix)) {
                    result.add(tableName);
                }
            }
        
        }
        return result;
    }

    private Set<Integer> getShardingValue(final Collection<ShardingValue<?>> shardingValues, final String shardingKey) {
        Set<Integer> valueSet = new HashSet<>();
        ShardingValue<Integer> shardingValue = null;
        for (ShardingValue<?> each : shardingValues) {
            if (each.getColumnName().equals(shardingKey)) {
                shardingValue = (ShardingValue<Integer>) each;
                break;
            }
        }
        if (null == shardingValue) {
            return valueSet;
        }
        switch (shardingValue.getType()) {
            case SINGLE:
                valueSet.add(shardingValue.getValue());
                break;
            case LIST:
                valueSet.addAll(shardingValue.getValues());
                break;
            case RANGE:
                for (Integer i = shardingValue.getValueRange().lowerEndpoint(); i <= shardingValue.getValueRange().upperEndpoint(); i++) {
                    valueSet.add(i);
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return valueSet;
    }
}
