package com.xjf.demo.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 自定义拦截器实现认证
 *
 * @author xjf
 * @date 2020/1/26 19:59
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("执行业务逻辑");
    }
}
