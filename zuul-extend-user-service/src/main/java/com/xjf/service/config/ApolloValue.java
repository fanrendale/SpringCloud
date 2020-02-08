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
 * Apollo 的取值
 *
 * @author xjf
 * @date 2020/2/8 18:12
 */
@Configuration
public class ApolloValue {

    /**
     * 从 Apollo 中取值的变量
     */
    @ApolloConfig
    private Config config;

    /**
     * Apollo 值更改的回调方法。修改限流值需要新建信号量来更新 SemaphoreMap
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent){
        // 所有的修改 key
        Set<String> keys = changeEvent.changedKeys();

        for (String key : keys) {
            // 如果 Apollo 中修改的值有在 semaphoreMap 中，则新 put 来覆盖旧值
            if (ApiLimitAspect.semaphoreMap.containsKey(key)){
                ApiLimitAspect.semaphoreMap.put(key, new Semaphore(Integer.valueOf(changeEvent.getChange(key).getNewValue())));
                System.out.println("信号量值更新成功，key: " + key);
            }
        }
    }

    /**
     * 初始化注解使用的信号量，从 Apollo 中获取当前配置值。
     * 此时不是配置 Bean ，只是想初始化运行
     * 默认限流值变量为 defaultLimit
     */
    @Bean
    public String initSemaphoreMap(){
        System.err.println("初始化 Apollo 中配置的值");
        Set<String> keys = ApiLimitAspect.semaphoreMap.keySet();

        for (String key : keys) {
            // Apollo 中没有值的话默认为 100
            Integer limit = config.getIntProperty(key, 100);
            System.out.println(key + ":       原值：" + ApiLimitAspect.semaphoreMap.get(key) + "=====新值：" + limit);
            // 更新值
            ApiLimitAspect.semaphoreMap.put(key, new Semaphore(limit));
        }

        return "initSuccess";
    }
}
