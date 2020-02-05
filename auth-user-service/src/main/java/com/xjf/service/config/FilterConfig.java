package com.xjf.service.config;

import com.xjf.auth.filter.HttpBasicAuthorizeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器配置，添加对 token 的验证
 *
 * @author xjf
 * @date 2020/2/5 15:23
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpBasicAuthorizeFilter httpBasicAuthorizeFilter = new HttpBasicAuthorizeFilter();

        // 过滤器
        registrationBean.setFilter(httpBasicAuthorizeFilter);

        // 路径匹配规则
        List<String> urlPatterns = new ArrayList<>(1);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }
}
