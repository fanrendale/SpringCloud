package com.xjf.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MqApplication {

    @Bean
    public ActiveMQQueue queue(){
        return new ActiveMQQueue("test");
    }

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }

}
