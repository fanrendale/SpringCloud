package com.xjf.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

    public static void main(String[] args) {
        // 指定环境（开发演示用，不能用于生产环境）
        System.setProperty("env", "DEV");

        // 整合 Archaius, 将 Apollo 的服务端给 Archaius 使用
        // 指定 archaius 获取配置的 URL
        String apolloConfigServiceUrl = "http://localhost:8080";
        String appId = "SampleApp";
        System.setProperty("archaius.configurationSource.additionalUrls",
                apolloConfigServiceUrl + "/configfiles/" + appId + "/default/application");

        SpringApplication.run(ZuulApplication.class, args);
    }
}
