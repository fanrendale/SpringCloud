package com.xjf.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Redis 的基本使用
 *
 * @author xjf
 * @date 2020/2/9 21:42
 */
@RestController
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        String key = "age";

        // 设置缓存, 有效期 30 秒
        stringRedisTemplate.opsForValue().set(key, "24", 30, TimeUnit.SECONDS);

        // 获取缓存
        String age = stringRedisTemplate.opsForValue().get(key);
        System.out.println("获取 key: " + age);

        // 判断 key 是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        System.out.println("是否有 key 为 age : " + hasKey);

        // 睡眠 40s ，等缓存过期
        TimeUnit.SECONDS.sleep(40);

        // 删除缓存
        Boolean delete = stringRedisTemplate.delete(key);
        System.out.println("删除缓存 " + key + " 是否成功：" + delete);

        // 判断 key 是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        System.out.println("是否有 key 为 age : " + hasKey);

        return "success";
    }
}
