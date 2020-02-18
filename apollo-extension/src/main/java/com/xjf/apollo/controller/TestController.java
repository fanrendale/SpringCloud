package com.xjf.apollo.controller;

import com.xjf.apollo.api.client.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/2/18 10:22
 */
@RestController
public class TestController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/test")
    public String test(){
        String hello = userRemoteClient.hello();
        System.out.println("调用结果：" + hello);

        return hello;
    }
}
