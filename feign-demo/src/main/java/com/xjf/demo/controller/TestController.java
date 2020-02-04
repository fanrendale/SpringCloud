package com.xjf.demo.controller;

import com.xjf.demo.api.client.UserRemoteClient;
import com.xjf.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试的controller
 *
 * @author xjf
 * @date 2020/1/26 11:50
 */
@RestController
public class TestController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    @Autowired
    private TestService testService;

    @GetMapping("/callHello")
    public String callHello(){
        String result = userRemoteClient.hello();
        System.out.println("调用结果：" + result);

        // 调用异步方法
        testService.saveLog("test");

        //调用本地方法
        testService.saveLog2("test");

        return result;
    }
}
