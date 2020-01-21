package com.xjf.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/1/21 16:59
 */
@RestController
public class UserController {

    @GetMapping("/user/hello")
    public String hello(){
        return "hello";
    }
}
