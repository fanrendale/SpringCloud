package com.xjf.service.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.xjf.service.apilimit.ApiLimitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author xjf
 * @date 2020/2/8 14:13
 */
@Configuration
public class BeanConfig {

    /**
     * 并发控制的切面
     *
     * @return
     */
    @Bean
    public ApiLimitAspect apiLimitAspect(){
        return new ApiLimitAspect();
    }


}
