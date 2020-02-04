package com.xjf.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Apollo 客户端集成 Spring 原理
 *
 * @author xjf
 * @date 2020/2/4 11:05
 */
@RestController
public class ConfigController {

    @Value("${myName:咖啡}")
    private String name;

    @Value("${myUrl}")
    private String myUrl;

    @GetMapping("/get")
    public String get(){
        return name + "---" + myUrl;
    }
}
