package com.xjf.demo.config;

import com.xjf.demo.interceptor.FeignBasicAuthRequestInterceptor;
import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
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

    /**
     * SpringCloud中默认的SpringMVCContract将替换为feign.Contract.Default
     * 此时之前定义的api client将不能使用，因为其注解是SpringMVC注解
     * @return
     */
    /*@Bean
    public Contract feignContract(){
        return new feign.Contract.Default();
    }*/

    /**
     * 配置Basic认证
     * @return
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("user", "password");
    }

    /**
     * 使用自定义认证
     * @return
     */
    @Bean
    public FeignBasicAuthRequestInterceptor feignBasicAuthRequestInterceptor(){
        return new FeignBasicAuthRequestInterceptor();
    }

    /**
     * 超时时间配置
     * @return
     */
    @Bean
    public Request.Options options(){
        //参数：第一个为连接超时时间（ms），默认10*1000；第二个为取超时时间（ms），默认是60*1000
        return new Request.Options(5000, 10000);
    }
}
