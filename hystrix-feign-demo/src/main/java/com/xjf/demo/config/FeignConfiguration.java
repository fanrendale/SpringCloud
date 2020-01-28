package com.xjf.demo.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author xjf
 * @date 2020/1/28 16:19
 */
@Configuration
public class FeignConfiguration {

    /**
     * 禁用Hystrix，
     * 还有一种方式在properties中配置：feign.hystrix.enabled=false
     *
     * @return
     */
    /*@Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }*/
}
