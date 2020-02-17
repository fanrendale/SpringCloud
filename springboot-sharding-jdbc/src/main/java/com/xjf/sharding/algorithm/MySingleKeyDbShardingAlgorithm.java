package com.xjf.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义实现单分片键数据源分片算法: 根据 city 分片
 *
 * @author xjf
 * @date 2020/2/17 14:00
 */
public class MySingleKeyDbShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {
    private static Map<String, List<String>> shardingMap = new ConcurrentHashMap<>();

    static {
        shardingMap.put("ds_0", Arrays.asList("上海"));
        shardingMap.put("ds_1", Arrays.asList("杭州"));
    }

    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        for (String each : collection) {
            System.err.println("数据库：" + each);
            System.err.println("添加数据的城市：" + shardingValue.getValue());
            if (shardingMap.get(each).contains(shardingValue.getValue())){
                return each;
            }
        }

        // 默认保存在数据库 "ds_0" 中
        return "ds_0";
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());

        for (String each : collection) {
            if (shardingMap.get(each).contains(shardingValue.getValue())){
                result.add(each);
            }else {
                result.add("ds_0");
            }
        }

        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());

        for (String each : collection) {
            if (shardingMap.get(each).contains(shardingValue.getValue())){
                result.add(each);
            }else {
                result.add("ds_0");
            }
        }

        return result;
    }
}
