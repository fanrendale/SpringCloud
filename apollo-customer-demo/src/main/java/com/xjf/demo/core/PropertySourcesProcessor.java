package com.xjf.demo.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 配置初始化逻辑。
 * 实现 EnvironmentAware 接口是为了获取 Environment 对象。
 * 实现 BeanFactoryPostProcessor 接口，我们可以在容器实例化 bean 之前读取 bean 的信息并修改它
 *
 * @author xjf
 * @date 2020/2/4 11:18
 */
@Component
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware {
    String APOLLO_PROPERTY_SOURCE_NAME = "ApolloPropertySource";

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 启动时初始化配置到 Spring PropertySource
        Config config = new Config();
        ConfigPropertySource configPropertySource = new ConfigPropertySource("application", config);

        CompositePropertySource composite = new CompositePropertySource(APOLLO_PROPERTY_SOURCE_NAME);
        composite.addPropertySource(configPropertySource);

        environment.getPropertySources().addFirst(composite);

        System.err.println("将配置初始化到 Spring PropertySource 中成功！！！");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
