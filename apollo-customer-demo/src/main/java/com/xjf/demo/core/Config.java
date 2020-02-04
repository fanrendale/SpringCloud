package com.xjf.demo.core;

import java.util.HashSet;
import java.util.Set;

/**
 * 配置获取类
 *
 * @author xjf
 * @date 2020/2/4 11:09
 */
public class Config {

    public String getProperty(String key, String defaultValue){
        if ("myName".equals(key)){
            return "盖伦";
        }

        return defaultValue;
    }

    public Set<String> getPropertyNames(){
        Set<String> names = new HashSet<>();
        names.add("myName");
        return names;
    }
}
