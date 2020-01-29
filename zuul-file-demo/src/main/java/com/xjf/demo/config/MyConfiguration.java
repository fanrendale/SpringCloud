package com.xjf.demo.config;

import com.xjf.demo.filter.RequestInfoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjf
 * @date 2020/1/29 22:16
 */
@Configuration
public class MyConfiguration {

    @Bean
    public RequestInfoFilter requestInfoFilter(){
        return new RequestInfoFilter();
    }
}
