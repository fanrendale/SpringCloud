package com.xjf.demo.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/1/21 16:59
 */
@RestController
public class UserController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/user/hello")
    public String hello(){
        System.out.println("调用了 hello 方法");
        return "hello";
    }

    /**
     * 获取信息
     * @return
     */
    @GetMapping("/user/infos")
    public Object getInfo(){
        return eurekaClient.getInstancesByVipAddress("eureka-client-user-service",false);
    }
}
