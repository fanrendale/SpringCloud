package com.xjf.demo.controller;

import com.xjf.demo.api.client.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Feign + Hystrix 进行服务降级
 *
 * @author xjf
 * @date 2020/1/28 15:48
 */
@RestController
public class TestController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/test/callHello")
    public String callHello(){
        String result = userRemoteClient.hello();
        System.out.println("调用结果: " + result);

        return result;
    }
}
