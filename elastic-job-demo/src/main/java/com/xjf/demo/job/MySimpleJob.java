package com.xjf.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义实现任务
 *
 * @author xjf
 * @date 2020/2/15 17:05
 */
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.err.println("开始执行简单任务：" + time);
    }
}
