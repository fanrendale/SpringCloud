package com.example.demo.controller;

import com.xjf.demo.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制类
 *
 * @Author: xjf
 * @Date: 2020/1/19 11:11
 */
@RestController
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/user/name")
    public String getUserName(){
        return userClient.getName();
    }
}
