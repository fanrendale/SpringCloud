package com.example.demo;

import com.xjf.demo.annotation.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 开启异步执行
 *
 * 使用注解方式开启自动注入UserClient
 * @author XJF
 */
@EnableUserClient
@EnableAsync
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
