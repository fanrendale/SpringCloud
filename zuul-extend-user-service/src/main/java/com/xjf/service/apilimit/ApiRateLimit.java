package com.xjf.service.apilimit;

import java.lang.annotation.*;

/**
 * 该注解用来表示要指定限流的方法，
 * 其中配置的速度属性值对应 Apollo 的配置值
 *
 * @author xjf
 * @date 2020/2/8 11:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiRateLimit {

    /**
     * Apollo 配置中的 key
     * @return
     */
    String confKey();
}
