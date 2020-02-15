package com.xjf.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.Arrays;
import java.util.List;

/**
 * 数据流任务
 *
 * @author xjf
 * @date 2020/2/15 20:47
 */
public class MyDataFlowJob implements DataflowJob<String> {

    /**
     * 数据抓取
     *
     * @param shardingContext
     * @return
     */
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        return Arrays.asList("1", "2", "3");
    }

    /**
     * 数据处理
     *
     * @param shardingContext
     * @param list
     */
    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        System.out.println("处理数据：" + list.toString());
    }
}
