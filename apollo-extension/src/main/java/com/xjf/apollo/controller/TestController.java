package com.xjf.apollo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.xjf.apollo.api.client.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/2/18 10:22
 */
@RestController
public class TestController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    /**
     * Apollo 存储的加密值。 会自动解密
     */
    @Value("${test.input}")
    private String input;

    /**
     * 不会自动解密
     */
    @ApolloConfig
    private Config config;

    @GetMapping("/test")
    public String test(){
        String hello = userRemoteClient.hello();
        System.out.println("调用结果：" + hello);

        return hello;
    }

    @GetMapping("/get")
    public String getInput(){
        System.err.println("获取解密后的值：" + input);

        return input;
    }

    @GetMapping("/config")
    public String getByConfig(){
        String result = config.getProperty("test.input", "默认值");
        System.err.println("使用 Config 获取加密的值：" + result);

        return result;
    }
}
