package com.xjf.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xjf
 * @date 2020/1/28 14:49
 */
@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用接口，设置降级方法为 defaultCallHello
     * @return
     */
    @GetMapping("/callHello")
    @HystrixCommand(fallbackMethod = "defaultCallHello")
    public String callHello(){
        String result = restTemplate.getForObject("http://localhost:8081/user/hello",String.class);
        System.out.println("调用结果：" + result);

        return result;
    }

    /**
     * 降级方法
     * @return
     */
    public String defaultCallHello(){
        System.out.println("服务调用失败执行方法");
        return "fail";
    }
}
