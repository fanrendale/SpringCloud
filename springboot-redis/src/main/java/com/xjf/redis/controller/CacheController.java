package com.xjf.redis.controller;

import com.xjf.redis.entity.Person;
import com.xjf.redis.service.CacheService;
import com.xjf.redis.service.Closure;
import com.xjf.redis.service.PersonService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存工具的使用
 *
 * @author xjf
 * @date 2020/2/11 10:34
 */
@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PersonService personService;

    /**
     * RedissonClient 客户端
     */
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 测试 Redis 的分布式锁
     * @return
     */
    @GetMapping("/redisson")
    public String redisson() throws InterruptedException {
        // 获取指定名称的锁
        RLock lock = redissonClient.getLock("anyLock");

        //支持过期解锁。 10s 后自动解锁。 无需调用 unlock 方法手动解锁
        lock.lock(30, TimeUnit.SECONDS);
        System.out.println("AAAAAAA");

//        TimeUnit.MINUTES.sleep(1);
//        lock.unlock();

        return "AAAAAAA";
    }

    @GetMapping("/redisson2")
    public String redisson2(){
        // 获取指定名称的锁
        RLock lock = redissonClient.getLock("anyLock");

        //支持过期解锁。 10s 后自动解锁。 无需调用 unlock 方法手动解锁
        lock.lock(30, TimeUnit.SECONDS);
        System.out.println("BBBBBBBBB");
//        lock.unlock();

        return "BBBBBBBBB";
    }


    @GetMapping("/cache/get")
    public String get(){
        String cacheKey = "1006";

        return cacheService.getCache(cacheKey, input -> {
            System.err.println("从数据库中取数据");
            return personService.findById(cacheKey);
        });
    }
}
