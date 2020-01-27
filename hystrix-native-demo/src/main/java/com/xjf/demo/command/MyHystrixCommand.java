package com.xjf.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.TimeUnit;

/**
 * 自定义HystrixCommand
 *
 * @author xjf
 * @date 2020/1/27 21:36
 */
public class MyHystrixCommand extends HystrixCommand<String> {

    private String name;

    public MyHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        //睡眠10s，模拟调用超时失败
        TimeUnit.SECONDS.sleep(10);

        return this.name + ":" + Thread.currentThread().getName();
    }

    /**
     * 该方法是调用失败后的回调方法
     * @return
     */
    @Override
    protected String getFallback() {
        return "失败了";
    }
}
