package com.xjf.customer.config;

import com.xjf.customer.api.interceptor.FeignBasicAuthRequestInterceptor;
import com.xjf.customer.filter.HttpHeaderParamFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjf
 * @date 2020/2/7 15:19
 */
@Configuration
public class BeanConfiguration {

    /**
     * 请求头参数过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpHeaderParamFilter httpHeaderParamFilter = new HttpHeaderParamFilter();

        // 注册请求头参数过滤器
        registrationBean.setFilter(httpHeaderParamFilter);
        List<String> urlPatterns = new ArrayList<>(1);
        urlPatterns.add("/*");
        // 对所有请求进行过滤，在请求处理之前
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

    /**
     * Feign 调用接口调用前的拦截器
     * @return
     */
    @Bean
    public FeignBasicAuthRequestInterceptor feignBasicAuthRequestInterceptor(){
        return new FeignBasicAuthRequestInterceptor();
    }
}
