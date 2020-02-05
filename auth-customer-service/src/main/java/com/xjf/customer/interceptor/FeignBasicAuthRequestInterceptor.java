package com.xjf.customer.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign 请求拦截器。在调用服务之前先进行拦截，添加我们自定义信息，比如在请求头中添加 token
 *
 * @author xjf
 * @date 2020/2/5 18:00
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 请求头添加 token
        requestTemplate.header("Authorization", System.getProperty("customer.auth.token"));
    }
}
