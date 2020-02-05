package com.xjf.zuul.config;

import com.xjf.zuul.filter.AuthHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjf
 * @date 2020/2/5 21:25
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public AuthHeaderFilter authHeaderFilter(){
        return new AuthHeaderFilter();
    }
}
