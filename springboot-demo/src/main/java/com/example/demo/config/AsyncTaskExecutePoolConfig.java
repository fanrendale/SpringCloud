package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池配置
 *
 * @Author: xjf
 * @Date: 2020/1/19 9:43
 */
@Configuration
public class AsyncTaskExecutePoolConfig implements AsyncConfigurer {

    private Logger logger = LoggerFactory.getLogger(AsyncTaskExecutePoolConfig.class);

    @Autowired
    private TaskThreadPoolConfig config;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        System.out.println("maxPoolSize:" + config.getMaxPoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setThreadNamePrefix(config.getThreadNamePrefix());
        //线程池的拒绝策略，主要两种：
        //AbortPolicy:直接抛出 java.util.concurrent.RejectedExecutionException异常
        //CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，
        //                 这样可以有效降低向线程池内添加任务的速度。
        //
        //总结：推荐使用CallerRunsPolicy策略，因为当队列中的任务满了之后，如果直接抛出异常，那么这个任务就会被抛弃。
        //     如果是CallerRunsPolicy，则会用主线程去执行，也就是同步执行，这样任务至少不会被丢弃。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        //异步任务中的异常处理
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                logger.error("============================"
                + throwable.getMessage() + "=========================", throwable);
                logger.error("exception method: " + method.getName());
            }
        };
    }
}
