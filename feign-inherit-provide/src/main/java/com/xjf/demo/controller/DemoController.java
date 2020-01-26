package com.xjf.demo.controller;

import com.example.demo.api.UserRemoteClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供者，实现公共接口
 * @author xjf
 * @date 2020/1/26 21:38
 */
@RestController
public class DemoController implements UserRemoteClient {
    @GetMapping("/call")
    @Override
    public String getName() {
        return "dale";
    }
}
