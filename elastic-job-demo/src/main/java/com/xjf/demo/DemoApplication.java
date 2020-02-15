package com.xjf.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * 使用 xml 配置方式
 */
@ImportResource(locations = {"classpath:applicationContext.xml"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

//        SpringApplication.run(DemoApplication.class);

        // 以非 web 方式启动
        new SpringApplicationBuilder().sources(DemoApplication.class)
                .web(false).run(args);

        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
