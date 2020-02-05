package com.xjf.customer.controller;

import com.xjf.auth.common.ResponseData;
import com.xjf.customer.api.client.AuthRemoteClient;
import com.xjf.customer.api.client.UserRemoteClient;
import com.xjf.customer.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xjf
 * @date 2020/2/5 15:52
 */
@RestController
public class CustomerController {

    @Autowired
    private AuthRemoteClient authRemoteClient;

    @Autowired
    private UserRemoteClient userRemoteClient;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/callToken")
    public String getToken(){
        //从本地配置中取
        return System.getProperty("customer.auth.token");
    }

    /**
     * 1. Feign 方式调用服务提供者接口
     * @return
     */
    @GetMapping("/callHello")
    public String callHello(){
        String result = userRemoteClient.hello();

        return result;
    }

    /**
     * 2. 通过 RestTemplate 调用服务提供者的接口
     * @return
     */
    @GetMapping("/callHello2")
    public String callHelloByRestTemplate(){
        String result = restTemplate.getForObject("http://auth-user-service/user/hello", String.class);
        return result;
    }
}
