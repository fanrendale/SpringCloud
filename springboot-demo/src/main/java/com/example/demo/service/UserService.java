package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xjf
 * @Date: 2020/1/19 9:24
 */
@Service
public class UserService {

    @Async
    public void asyncMethod(){
        try {
            System.out.println("异步方法开始执行");

            //该方法首先睡眠10s
            TimeUnit.SECONDS.sleep(10);

            int a = 1 / 0;
            System.out.println("异步方法执行结束了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
