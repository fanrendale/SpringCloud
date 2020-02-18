package com.xjf.zuul.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Zuul 结合 Apollo 实现动态路由
 *
 * 实现 ApplicationContextAware 接口是为了获取 ApplicationContext 来了获取实例
 *
 * @author xjf
 * @date 2020/2/18 15:22
 */
@Slf4j
@Component
public class ZuulPropertiesRefresher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private RouteLocator routeLocator;

    /**
     * Apollo 修改监听
     *
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent){
        boolean zuulPropertiesChanged = false;

        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("zuul.")){
                zuulPropertiesChanged = true;
                break;
            }
        }

        if (zuulPropertiesChanged){
            refreshZuulProperties(changeEvent);
        }
    }

    private void refreshZuulProperties(ConfigChangeEvent changeEvent) {
        log.info("开始刷新 Zuul 的属性！！！");

        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        this.applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));

        log.info("结束刷新 Zuul 的属性！！！");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
