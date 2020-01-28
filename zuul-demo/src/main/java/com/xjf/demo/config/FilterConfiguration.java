package com.xjf.demo.config;

import com.xjf.demo.filter.IpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author xjf
 * @date 2020/1/28 22:30
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public IpFilter ipFilter(){
        return new IpFilter();
    }
}
