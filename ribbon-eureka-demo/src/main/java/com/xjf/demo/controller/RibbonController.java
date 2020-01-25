package com.xjf.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ribbon API的使用
 *
 * @author xjf
 * @date 2020/1/25 14:45
 */
@RestController
public class RibbonController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 使用 LoadBalancerClient 通过服务名称获取对应的服务信息
     * @return
     */
    @GetMapping("/choose")
    public Object chooseUrl(){
        ServiceInstance instance = loadBalancerClient.choose("ribbon-eureka-demo");

        return instance;
    }
}
