package com.xjf.demo.config;

import com.xjf.demo.annotation.MyLoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置Bean
 *
 * @author xjf
 * @date 2020/1/23 11:15
 */
@Configuration
public class BeanConfiguration {

    /**
     * 开启RestTemplate的负载均衡
     * @return
     */
    @Bean
//    @LoadBalanced
    @MyLoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
