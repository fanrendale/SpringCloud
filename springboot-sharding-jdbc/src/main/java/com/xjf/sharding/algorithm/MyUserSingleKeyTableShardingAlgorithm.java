package com.xjf.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 自定义实现取模分片算法
 *
 * @author xjf
 * @date 2020/2/17 11:13
 */
public class MyUserSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {
    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
        System.err.println("运行方法： doEqualSharding");

        for (String each : collection) {
            System.err.println("表：" + each);
            System.err.println("shardingValue.getValue: " + shardingValue.getValue());

            if (each.endsWith(shardingValue.getValue() % 4 +"")){
                return each;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
        System.err.println("运行方法： doInSharding");

        Collection<String> result = new LinkedHashSet<>(collection.size());

        for (Long value : shardingValue.getValues()) {
            for (String tableName : collection) {
                if (tableName.endsWith(value % 4 + "")){
                    result.add(tableName);
                }
            }
        }

        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Long> shardingValue) {
        System.err.println("运行方法： doBetweenSharding");

        Collection<String> result = new LinkedHashSet<>(collection.size());

        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith( i % 4 + "")){
                    result.add(each);
                }
            }
        }

        return result;
    }
}
