package com.xjf.demo;

import com.xjf.demo.task.ProcessMessageTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

@EnableFeignClients(basePackages = "com.xjf.demo.api.client")
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class DemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);

        SpringApplication application = new SpringApplication(DemoApplication.class);
        ConfigurableApplicationContext context = application.run(args);

        // 项目启动时开启发送消息任务
        try {
            ProcessMessageTask task = context.getBean(ProcessMessageTask.class);
            task.start();
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            log.error("项目启动异常：" , e);
        }
    }

}
