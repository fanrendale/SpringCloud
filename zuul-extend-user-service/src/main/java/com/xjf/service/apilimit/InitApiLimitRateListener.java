package com.xjf.service.apilimit;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 启动时初始化限速 API 信息
 *
 * @author xjf
 * @date 2020/2/8 12:00
 */
@Component
public class InitApiLimitRateListener implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // 获取配置的默认限流变量（此处的配置只是一种思路，后面 Apollo 会更新值，包括默认值）
        /*Environment environment = ctx.getEnvironment();
        String defaultLimit = environment.getProperty("open.api.defaultLimit");
        // 默认限流变量的默认值为 100
        Object rate = defaultLimit == null ? 100 : defaultLimit;*/
        // 初始化默认限流变量的信号量，此时写死，后面会从 Apollo 中取默认值
        ApiLimitAspect.semaphoreMap.put("defaultLimit", new Semaphore(100));

        // 获取所有有 RestController 注解的类
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(RestController.class);
        Set<String> keys = beanMap.keySet();
        // 变量所有类
        for (String key : keys) {
            Class<?> clz = beanMap.get(key).getClass();
            // 获取全类名，包含路径和 $$ 后面的信息
            String fullName = beanMap.get(key).getClass().getName();
            if (fullName.contains("EnhancerBySpringCGLIB") || fullName.contains("$$")){
                fullName = fullName.substring(0, fullName.indexOf("$$"));
                try {
                    // 根据类名（含相对路径）获取类
                    clz = Class.forName(fullName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            // 获取类下的所有方法
            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                // 获取所有有 ApiRateLimit 注解的方法
                if (method.isAnnotationPresent(ApiRateLimit.class)){
                    // 获取注解里面配置的限流变量名
                    String confKey = method.getAnnotation(ApiRateLimit.class).confKey();

                    // 设置信号量，key 为配置的注解上配置的变量。此处默认一个值，真正的值会在 Apollo 取值后初始化
                    ApiLimitAspect.semaphoreMap.put(confKey, new Semaphore(100));

                    // 从环境变量中取值
                    /*if (environment.getProperty(confKey) != null){
                        int limit = Integer.parseInt(environment.getProperty(confKey));
                        // 根据配置的限流值创建信号量，并用限流变量做 key
                        ApiLimitAspect.semaphoreMap.put(confKey, new Semaphore(limit));
                    }*/
                }
            }
        }
    }
}
