package com.xjf.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置SpringSecurity，为Eureka注册中心开启权限认证
 *
 * @author xjf
 * @date 2020/1/21 17:40
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable();
        //支持httpBasic
        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }
}
