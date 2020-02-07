package com.xjf.customer.controller;

import com.xjf.customer.api.client.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjf
 * @date 2020/2/7 14:37
 */
@RestController
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/customer/callHello")
    public String callHello(){
        System.err.println("当前登录用户名：" + request.getHeader("username"));
        //调用其他服务
        return userRemoteClient.hello();
    }
}
