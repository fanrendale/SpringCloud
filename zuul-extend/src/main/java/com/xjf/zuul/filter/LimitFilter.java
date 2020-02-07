package com.xjf.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/**
 * 限流过滤器
 *
 * @author xjf
 * @date 2020/2/7 17:37
 */
public class LimitFilter extends ZuulFilter {
    // 15.918
    /**
     * 创建一个限流器，参数代表每秒生成的令牌数。体现了每秒处理的最大请求数
     */
    public static volatile RateLimiter rateLimiter = RateLimiter.create(10.0);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 该方法会阻塞线程，直到令牌桶中能取到令牌为止才继续向下执行，并返回等待的时间
        // 不带参数表示获取一个令牌.如果没有令牌则一直等待,返回等待的时间(单位为秒)
        // 总体限流
        double waitTime = rateLimiter.acquire();

        return null;
    }
}
