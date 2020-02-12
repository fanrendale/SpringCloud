package com.xjf.demo.config;

import com.xjf.demo.listener.SaveMongoEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xjf
 * @date 2020/2/12 15:53
 */
@Configuration
public class BeanConfig {

    @Bean
    public SaveMongoEventListener saveMongoEventListener(){
        return new SaveMongoEventListener();
    }
}
