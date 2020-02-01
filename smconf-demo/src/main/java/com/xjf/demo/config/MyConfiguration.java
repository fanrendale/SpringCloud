package com.xjf.demo.config;

import org.cxytiandi.conf.client.init.ConfInit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xjf
 * @date 2020/2/1 15:24
 */
@Configuration
public class MyConfiguration {

    /**
     *  启动 Smconf 配置客户端
     *
     * @return
     */
    @Bean
    public ConfInit confInit(){
        System.out.println("启动 Smconf 配置客户端");

        return new ConfInit();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
