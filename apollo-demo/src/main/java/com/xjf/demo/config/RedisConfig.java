package com.xjf.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 注入值,配置中心配置 redis.cache.host
 *
 * @author xjf
 * @date 2020/2/2 20:34
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis.cache")
public class RedisConfig {

    private String host;
}
