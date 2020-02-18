package com.xjf.apollo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 使用 Apollo 实现动态修改日志级别
 *
 * @author xjf
 * @date 2020/2/18 10:29
 */
@Service
public class LoggerLevelRefresher implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @ApolloConfig
    private Config config;

    /**
     * PostConstruct 该注解被用来修饰一个非静态的void（）方法。被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
     * 并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。
     */
    @PostConstruct
    private void initialize(){
        // 获取 Apollo 中的所有配置键
        config.getPropertyNames().forEach(System.err::println);
        refreshLoggingLevels(config.getPropertyNames());
    }

    /**
     * Apollo 值修改的监听器，如果有值修改，则获取所有的修改 key
     */
    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent changeEvent){
        changeEvent.changedKeys().forEach(System.err::println);
        refreshLoggingLevels(changeEvent.changedKeys());
    }

    private void refreshLoggingLevels(Set<String> changeKeys){
        boolean loggingLevelChanged = false;

        // 过滤判断日志级别配置是否修改
        for (String changeKey : changeKeys) {
            if (changeKey.startsWith("logging.level.")){
                loggingLevelChanged = true;
                break;
            }
        }

        if (loggingLevelChanged){
            System.err.println("Refreshing logging levels");
            // 修改项目的日志级别
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeKeys));
            System.err.println("Logging levels refreshed");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
