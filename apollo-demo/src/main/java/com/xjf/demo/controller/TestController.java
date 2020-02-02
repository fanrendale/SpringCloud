package com.xjf.demo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import com.xjf.demo.config.RedisConfig;
import com.xjf.demo.config.UserConfig;
import com.xjf.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xjf
 * @date 2020/2/2 15:31
 */
@RestController
public class TestController {

    /**
     * 1. 使用 Placeholder 注入配置
     */
    @Value("${name:盖伦}")
    private String name;

    /**
     * 2. Java Config 方式
     */
    @Autowired
    private UserConfig userConfig;

    /**
     * 3. ConfigurationProperties 方式注入值。配置中心配置 redis.cache.host。（不推荐）
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 4. @ApolloConfig 注解方式
     */
    @ApolloConfig
    private Config config;

    /**
     * 注入 json 值
     */
    @ApolloJsonValue("${stus:[]}")
    private List<Student> stus;

    /**
     * 注解方式添加值修改的监听。
     */
    @ApolloConfigChangeListener
    private void someOnChange(ConfigChangeEvent changeEvent){
        if (changeEvent.isChanged("addr")){
            ConfigChange addr = changeEvent.getChange("addr");

            System.out.println("addr 发生修改了: 旧值=" + addr.getOldValue() + " 新值=" + addr.getNewValue());
        }
    }

    @GetMapping("/value")
    public String getValue(){
        return name;
    }

    @GetMapping("/config/value")
    public String getConfigValue(){
        return userConfig.getName();
    }

    @GetMapping("/redis/value")
    public String getRedisValue(){
        return redisConfig.getHost();
    }

    @GetMapping("/annotation/value")
    public String getAnnotationValue(){
        return config.getProperty("addr","成都");
    }

    @GetMapping("/json/value")
    public String getJsonValue(){
        stus.forEach(System.out::println);

        return stus.toString();
    }
}
