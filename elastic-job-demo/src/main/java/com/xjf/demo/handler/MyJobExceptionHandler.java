package com.xjf.demo.handler;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;

/**
 * 自定义任务异常处理
 *
 * @author xjf
 * @date 2020/2/16 12:09
 */
public class MyJobExceptionHandler implements JobExceptionHandler {
    @Override
    public void handleException(String s, Throwable throwable) {
        System.err.println(s + " 任务出现异常：" + throwable );
    }
}
