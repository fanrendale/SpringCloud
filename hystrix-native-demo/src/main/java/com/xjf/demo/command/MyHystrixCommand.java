package com.xjf.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

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
//        super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));

        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                //隔离策略配置:有线程隔离和信号量隔离：值为 THREAD 时输出的是线程名称，值为 SEMAPHORE 时输出的是方法名
                //默认是线程隔离策略
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
                //配置线程池的参数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaxQueueSize(100)
                        .withMaximumSize(100))
            );

        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        //睡眠10s，模拟调用超时失败
//        TimeUnit.SECONDS.sleep(10);

        System.err.println("非缓存获取数据");
        return this.name + ":" + Thread.currentThread().getName();
    }

    /**
     * 该方法是调用失败后的回调方法
     *
     * @return
     */
    @Override
    protected String getFallback() {
        return "失败了";
    }

    /**
     * 返回缓存key(可以继承三方，如Redis等做缓存）,只是键
     * 使用缓存在调用时需要初始化HystrixRequestContext
     * @return
     */
    @Override
    protected String getCacheKey() {
        return String.valueOf(this.name);
    }
}
