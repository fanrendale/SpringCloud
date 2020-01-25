package com.xjf.demo.config;

import com.xjf.demo.rule.MyRule;
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
     *
     * 用了@LoadBalanced注解后，restTemplate就不能使用IP地址调用服务
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
    public MyRule myRule(){
        return new MyRule();
    }
}
