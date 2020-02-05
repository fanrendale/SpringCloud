package com.xjf.customer.config;

import com.xjf.customer.interceptor.RestTemplateTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author xjf
 * @date 2020/2/5 18:19
 */
@Configuration
public class BeanConfiguration {

    @Autowired
    private RestTemplateTokenInterceptor tokenInterceptor;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        // 给 RestTemplate 添加 token 拦截器
        restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));

        return restTemplate;
    }
}
