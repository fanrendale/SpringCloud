package com.xjf.demo.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xjf
 * @date 2020/1/21 17:08
 */
@RestController
public class ArticleController {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 注意此处的是:org.springframework.cloud.client.discovery.DiscoveryClient;
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 测试直接调用用户服务（未经过Eureka注册中心）,使用的是IP地址
     *
     * @return
     */
    @GetMapping("/article/callHello")
    public String callHello(){
        return restTemplate.getForObject("http://localhost:8081/user/hello", String.class);
    }

    /**
     * 测试使用Eureka注册中心调用服务，使用的地址时服务的名称来调用
     *
     * @return
     */
    @GetMapping("/article/callHello2")
    public String callHello2(){
        return restTemplate.getForObject("http://eureka-client-user-service/user/hello", String.class);
    }

    /**
     * 获取用户服务实例信息，当前是article服务，可以获取user服务的信息
     * @return
     */
    @GetMapping("/article/infos")
    public Object getInfos(){
        return eurekaClient.getInstancesByVipAddress("eureka-client-user-service", false);
    }

    /**
     * 使用discoveryClient获取实例信息
     * @return
     */
    @GetMapping("/article/infos2")
    public Object getInfo2(){
        return discoveryClient.getInstances("eureka-client-user-service");
    }
}
