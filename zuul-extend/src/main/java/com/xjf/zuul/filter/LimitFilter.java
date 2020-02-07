package com.xjf.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.auth.common.ResponseCode;
import com.xjf.auth.common.ResponseData;
import com.xjf.auth.util.JsonUtils;
import com.xjf.zuul.apollo.BasicConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 限流过滤器
 *
 * @author xjf
 * @date 2020/2/7 17:37
 */
@Slf4j
public class LimitFilter extends ZuulFilter {
    // 15.918
    /**
     * 创建一个限流器，参数代表每秒生成的令牌数。体现了每秒处理的最大请求数
     */
    public static volatile RateLimiter rateLimiter = RateLimiter.create(10.0);

    @Autowired
    @Qualifier("longRedisTemplate")
    private RedisTemplate<String, Long>  redisTemplate;

    @Autowired
    private BasicConf basicConf;

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
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取当前时间的秒数，做 Redis 的key
        long currentSecond = System.currentTimeMillis() / 1000;
        String key = "zuul-rate-limit-" + currentSecond;

        try {
            // 集群限流
            if (!redisTemplate.hasKey(key)){
                // 设置值，后面两个参数为超时时间和单位，过期时间
                redisTemplate.opsForValue().set(key, 0L, 100, TimeUnit.SECONDS);
            }

            int rate = basicConf.getClusterLimitRate();

            // 如果同一个 key （即一秒时间内），增长的数字大于了配置的限定数，则请求不处理
            // 注意集群中的网关与所在服务器的时间必须一致
            if (redisTemplate.opsForValue().increment(key, 1) > rate){
                System.out.println("限流了，rate: " + rate);
                ctx.setSendZuulResponse(false);
                ctx.set("isSuccess", false);
                ResponseData data = ResponseData.fail("当前负载太高，请稍后重试", ResponseCode.LIMIT_ERROR_CODE.getCode());
                ctx.setResponseBody(JsonUtils.toJson(data));
                ctx.getResponse().setContentType("application/json; charset=utf-8");

                return null;
            }
        } catch (Exception e) {
            log.error("集群限流异常：" + e);

            // 集群限流异常，则使用单点限流
            // 该方法会阻塞线程，直到令牌桶中能取到令牌为止才继续向下执行，并返回等待的时间
            // 不带参数表示获取一个令牌.如果没有令牌则一直等待,返回等待的时间(单位为秒)
            // 总体限流
            double waitTime = rateLimiter.acquire();
        }



        return null;
    }
}
