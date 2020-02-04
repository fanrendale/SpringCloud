package com.xjf.demo.service;

import brave.ScopedSpan;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xjf
 * @date 2020/2/4 17:11
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private Tracer tracer;

    @Async
    @Override
    public void saveLog(String log) {
        System.out.println("异步线程执行方法");
    }

    @NewSpan("saveLog22")
    @Override
    public void saveLog2(String log) {
        // 手动埋点,追踪本地方法的调用时间
        /*ScopedSpan span = tracer.startScopedSpan("saveLog2");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            span.error(e);
        }finally {
            span.finish();
        }*/

        try {
            System.out.println("注解埋点");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
