package com.xjf.demo;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xjf.demo.command.ClearCacheHystrixCommand;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 清除缓存测试
 *
 * @author xjf
 * @date 2020/1/28 11:28
 */
public class Main2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String result = new ClearCacheHystrixCommand("dale").execute();
        System.out.println(result);

        //清除缓存
        ClearCacheHystrixCommand.flushCache("dale");

        //再次调用
        Future<String> future = new ClearCacheHystrixCommand("dale").queue();
        System.out.println(future.get());

        context.shutdown();
    }
}
