package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: xjf
 * @Date: 2020/1/18 10:12
 */
@ConfigurationProperties(prefix = "com.xjf")
@Component
public class MyConfig {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
