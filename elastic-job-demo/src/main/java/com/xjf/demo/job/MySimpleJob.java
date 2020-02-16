package com.xjf.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 自定义实现任务
 *
 * @author xjf
 * @date 2020/2/15 17:05
 */
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        /*String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.err.println("开始执行简单任务：" + time);*/

        // 测试数据分片处理
        String shardingParameter = shardingContext.getShardingParameter();
        System.err.println("分片参数：" + shardingParameter);
        Integer value = Integer.valueOf(shardingParameter);

        // 异常模拟
//        int a = 2 / 0;

        for (int i = 1; i <= 20; i++) {
            if (i % 2 == value){
                System.err.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " : 开始执行简单任务 " + i);

                // 睡眠 5s
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
