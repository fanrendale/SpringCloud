package com.xjf.demo.core;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.Set;

/**
 * 配置类转换成 PropertySource。将 Config 封装成 PropertySource
 *
 * @author xjf
 * @date 2020/2/4 11:12
 */
public class ConfigPropertySource extends EnumerablePropertySource<Config> {

    private static final String[] EMPTY_ARRAY = new String[0];

    public ConfigPropertySource(String name, Config source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        Set<String> propertyNames = this.source.getPropertyNames();
        if (propertyNames.isEmpty()){
            return EMPTY_ARRAY;
        }

        return propertyNames.toArray(new String[propertyNames.size()]);
    }

    @Override
    public Object getProperty(String name) {
        return this.source.getProperty(name, null);
    }
}
