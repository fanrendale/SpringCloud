package com.xjf.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 过滤器，路由转发前获取 token，添加到请求的请求头中
 *
 * @author xjf
 * @date 2020/2/5 21:20
 */
public class AuthHeaderFilter extends ZuulFilter {

    public AuthHeaderFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Object isSuccess = currentContext.get("isSuccess");

        return isSuccess == null ? true : Boolean.parseBoolean(isSuccess.toString());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 给请求头添加 token
        System.out.println("token: " + System.getProperty("customer.auth.token"));
        currentContext.addZuulRequestHeader("Authorization", System.getProperty("customer.auth.token"));

        return null;
    }
}
