package com.xjf.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xjf
 * @date 2020/2/4 17:11
 */
@Service
public class TestServiceImpl implements TestService {

    @Async
    @Override
    public void saveLog(String log) {
        System.out.println("异步线程执行方法");
    }
}
