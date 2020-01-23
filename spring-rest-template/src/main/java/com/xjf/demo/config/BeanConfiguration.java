package com.xjf.demo.config;

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

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
