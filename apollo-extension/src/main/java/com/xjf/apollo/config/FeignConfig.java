package com.xjf.apollo.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjf
 * @date 2020/2/18 10:52
 */
@Configuration
public class FeignConfig {

    /**
     * 日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
