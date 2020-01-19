package com.example.demo.controller;

import com.example.demo.config.MyConfig;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置
 *
 * @Author: xjf
 * @Date: 2020/1/18 10:02
 */
@RestController
public class HelloController {

    /**
     * 方式一：使用Environment读取属性配置
     */
    @Autowired
    private Environment env;

    /**
     * 方式二：注入配置
     */
    @Value("${server.port}")
    private String port;

    /**
     * 方式三：自定义配置
     */
    @Autowired
    private MyConfig myConfig;

    /**
     * 测试异步执行
     */
    @Autowired
    private UserService userService;

    /**
     * 测试
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 读取配置
     * @return
     */
    @GetMapping("/read")
    public String readProperties(){

//        String property = env.getProperty("server.port");

        return myConfig.getName() + " good";
    }

    /**
     * 测试方法异步执行
     * @return
     */
    @GetMapping("/async")
    public String asyncTest(){
        System.out.println("主方法开始执行");

        userService.asyncMethod();

        System.out.println("主方法结束执行");

        return "success";
    }

}
