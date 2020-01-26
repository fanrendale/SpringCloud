package com.xjf.demo.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign日志配置
 *
 * @author xjf
 * @date 2020/1/26 14:09
 */
@Configuration
public class FeignConfiguration {

    /**
     * 日志级别：
     * NONE：不输出日志
     * BASIC：只输出请求方法的URL和响应的状态码以及接口执行的时间
     * HEADERS：将BASIC信息和请求头部信息输出
     * FULL：输出完整的请求信息
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
