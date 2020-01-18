package com.example.demo.endpoint;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 扩展健康端点(类名会被展示)
 *
 * @Author: xjf
 * @Date: 2020/1/18 11:04
 */
@Component
public class UserHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        //此处应用可以添加自己的判断来确定返回的信息是健康还是不健康，以及可以添加自定义的属性

        //指定应用的状态为健康，添加详情
        builder.up().withDetail("name", "xjf");
        //指定应用的状态为不健康，及添加详情
//        builder.down().withDetail("reason", "不够帅");
    }
}
