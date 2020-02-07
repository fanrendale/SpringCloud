package com.xjf.zuul.config;

import com.xjf.zuul.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 配置
 *
 * @author xjf
 * @date 2020/2/7 12:09
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
}
