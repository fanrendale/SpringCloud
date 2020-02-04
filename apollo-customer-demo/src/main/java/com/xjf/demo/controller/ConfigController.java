package com.xjf.demo.controller;

import com.xjf.demo.annotation.SpringValueProcessor;
import com.xjf.demo.annotation.property.SpringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * 测试 Apollo 客户端集成 Spring 原理
 *
 * @author xjf
 * @date 2020/2/4 11:05
 */
@RestController
public class ConfigController {

    @Value("${myName:咖啡}")
    private String name;

    @Value("${myUrl}")
    private String myUrl;

    @Autowired
    private SpringValueProcessor springValueProcessor;

    @Autowired
    private ConfigurableBeanFactory beanFactory;

    @GetMapping("/get")
    public String get(){
        return name + "---" + myUrl;
    }

    @GetMapping("/update")
    public String update(String value) {
        Collection<SpringValue> targetValues = springValueProcessor.springValueRegistry.get(beanFactory,
                "myName");
        for (SpringValue val : targetValues) {
            try {
                val.update(value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return name;
    }
}
