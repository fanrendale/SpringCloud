package com.xjf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // 使用代码设置环境
        System.setProperty("env", "DEV");

        SpringApplication.run(DemoApplication.class, args);
    }

}
