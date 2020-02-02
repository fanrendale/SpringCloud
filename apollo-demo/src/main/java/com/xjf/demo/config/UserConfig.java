package com.xjf.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 2. Java Config 方式
 *
 * @author xjf
 * @date 2020/2/2 20:26
 */
@Data
@Configuration
public class UserConfig {

    @Value("${name:嘉文四世}")
    private String name;
}
