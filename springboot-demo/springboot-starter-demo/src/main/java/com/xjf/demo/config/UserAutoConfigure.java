package com.xjf.demo.config;

import com.xjf.demo.client.UserClient;
import com.xjf.demo.properties.UserProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户类自动配置
 *
 * @Author: xjf
 * @Date: 2020/1/19 11:02
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfigure {

    /**
     * 开启自动配置的条件：配置文件有：spring.user.enabled=true
     * @param userProperties
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.user", value = "enabled", havingValue = "true")
    public UserClient userClient(UserProperties userProperties){
        return new UserClient(userProperties);
    }
}
