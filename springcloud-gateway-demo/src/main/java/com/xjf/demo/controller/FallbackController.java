package com.xjf.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 熔断回退控制器
 *
 * @author xjf
 * @date 2020/1/31 21:04
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback(){
        System.err.println("进入了熔断回退的方法");

        return "fallback";
    }
}
