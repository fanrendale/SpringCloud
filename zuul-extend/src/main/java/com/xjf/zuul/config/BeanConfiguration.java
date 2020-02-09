package com.xjf.zuul.config;

import com.xjf.zuul.apollo.LimitConf;
import com.xjf.zuul.filter.AuthFilter;
import com.xjf.zuul.filter.DownGradeFilter;
import com.xjf.zuul.filter.GrayPushFilter;
import com.xjf.zuul.filter.LimitFilter;
import com.xjf.zuul.rule.GrayPushRule;
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
    public DownGradeFilter downFilter(){
        return new DownGradeFilter();
    }

    @Bean
    public GrayPushFilter grayPushFilter(){
        return new GrayPushFilter();
    }

    /*@Bean
    public GrayPushRule grayPushRule(){
        return new GrayPushRule();
    }*/
}
