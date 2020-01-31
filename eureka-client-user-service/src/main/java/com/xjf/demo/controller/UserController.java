package com.xjf.demo.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjf
 * @date 2020/1/21 16:59
 */
@RestController
public class UserController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/user/hello")
    public String hello(HttpServletRequest request){
        System.out.println("调用了 hello 方法");
        System.err.println("过滤器添加的请求头：" + request.getHeader("X-Request-Foo"));
        System.err.println("请求头：Cache-Control=" + request.getHeader("Cache-Control"));
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

    @GetMapping("/user/name")
    public String name(@RequestParam("name") String name){
        System.out.println("调用了name方法");
        return name;
    }
}
