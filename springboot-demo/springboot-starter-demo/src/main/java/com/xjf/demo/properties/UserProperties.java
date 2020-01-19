package com.xjf.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户属性
 *
 * @Author: xjf
 * @Date: 2020/1/19 10:59
 */
@Data
@ConfigurationProperties(prefix = "spring.user")
public class UserProperties {

    private String name;
}
