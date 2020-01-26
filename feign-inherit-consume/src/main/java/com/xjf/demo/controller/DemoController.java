package com.xjf.demo.controller;

import com.example.demo.api.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务消费者
 *
 * @author xjf
 * @date 2020/1/26 21:46
 */
@RestController
public class DemoController {
    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/call")
    public String callName(){
        String name = userRemoteClient.getName();
        System.out.println("调用结果：" + name);

        return name;
    }
}
