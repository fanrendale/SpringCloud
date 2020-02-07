package com.xjf.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 此控制器的方法主要用来测试 Zuul 网关中的白名单过滤拦截
 *
 * @author xjf
 * @date 2020/2/7 12:18
 */
@RestController
public class TestController {

    @GetMapping("/user/hello")
    public String hello(){
        return "我是 hello 方法";
    }

    @GetMapping("/user/logout/{id}")
    public String logout(@PathVariable("id") String id){
        return "用户：" + id + " 注销成功";
    }

    @GetMapping("/user/aaa")
    public String login(){
        return "aaa方法";
    }
}
