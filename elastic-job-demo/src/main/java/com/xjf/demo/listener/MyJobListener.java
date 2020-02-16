package com.xjf.demo.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义任务监听器
 *
 * @author xjf
 * @date 2020/2/16 11:52
 */
public class MyJobListener implements ElasticJobListener {

    /**
     *  任务开始前
     *
     * @param shardingContexts
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.err.println("====================" + time + " : 开始执行任务：" + shardingContexts.getJobName());
    }

    /**
     * 任务结束后
     *
     * @param shardingContexts
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.err.println("====================" + time + " : 任务执行结束：" + shardingContexts.getJobName());
    }
}
