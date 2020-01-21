package com.xjf.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Bean配置类
 *
 * @author xjf
 * @date 2020/1/21 17:07
 */
@Configuration
public class BeanConfiguration {

    /**
     * 简单的RestTemplate
     * @return
     */
    /*@Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }*/

    /**
     * 使用Eureka注册中心时，需要添加注解LoadBalanced
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
