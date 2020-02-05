package com.xjf.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/2/5 15:11
 */
@RestController
public class UserController {

    @GetMapping("/user/hello")
    public String hello(){
        return "我的 /user/hello";
    }
}
