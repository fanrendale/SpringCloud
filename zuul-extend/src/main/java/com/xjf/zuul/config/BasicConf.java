package com.xjf.zuul.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Apollo 配置类（获取配置属性）
 *
 * @author xjf
 * @date 2020/2/7 11:30
 */
@Data
@Configuration
public class BasicConf {

    /**
     * API 接口白名单，多个用逗号分开
     */
    @Value("${apiWhiteStr:/zuul-extend-user-service/user/login}")
    private String apiWhiteStr;
}
