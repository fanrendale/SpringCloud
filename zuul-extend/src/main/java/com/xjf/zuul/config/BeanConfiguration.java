package com.xjf.zuul.config;

import com.xjf.zuul.apollo.LimitConf;
import com.xjf.zuul.filter.AuthFilter;
import com.xjf.zuul.filter.DownFilter;
import com.xjf.zuul.filter.LimitFilter;
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

    @Bean
    public LimitConf limitConf(){
        return new LimitConf();
    }

    @Bean
    public LimitFilter limitFilter(){
        return new LimitFilter();
    }

    @Bean
    public DownFilter downFilter(){
        return new DownFilter();
    }
}
