package com.xjf.demo;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xjf.demo.command.MyHystrixCollapser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 合并请求测试(此处是编码实现，还可以使用注解实现）
 *
 * @author xjf
 * @date 2020/1/28 11:56
 */
public class Main3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        //必须是异步请求，不然请求不会合并
        Future<String> f1 = new MyHystrixCollapser("xjf").queue();
        Future<String> f2 = new MyHystrixCollapser("dale").queue();
        System.out.println(f1.get() + " ======= " + f2.get());

        /*String r1 = new MyHystrixCollapser("xjf").execute();
        String r2 = new MyHystrixCollapser("dale").execute();
        System.out.println(r1 + "============" + r2);*/

        context.shutdown();
    }
}
