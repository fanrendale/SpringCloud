package com.xjf.redis.controller;

import com.xjf.redis.entity.Person;
import com.xjf.redis.service.CacheService;
import com.xjf.redis.service.Closure;
import com.xjf.redis.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public PersonService personService;


    @GetMapping("/cache/get")
    public String get(){
        String cacheKey = "1006";

        return cacheService.getCache(cacheKey, input -> {
            System.err.println("从数据库中取数据");
            return personService.findById(cacheKey);
        });
    }
}
