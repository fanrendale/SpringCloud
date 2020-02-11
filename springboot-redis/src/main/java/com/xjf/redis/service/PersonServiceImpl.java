package com.xjf.redis.service;

import com.xjf.redis.entity.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author xjf
 * @date 2020/2/10 11:26
 */
@Service
public class PersonServiceImpl implements PersonService {


    /**
     * 注解：value --> 缓存的名字， key --> 缓存 map 中的 key
     * @param id
     * @return
     */
    @Override
//    @Cacheable(value = "get", key = "#id")
    // 使用自定义 key 生成策略
    @Cacheable(value = "get", keyGenerator = "keyGenerator")
    public Person get(String id) {
        Person person = new Person(id,"xjf","123456");

        System.err.println("从数据库中取值！！！");

        return person;
    }

    @Override
    public Person findById(String id) {
        return new Person(id, "亚索", "666666");
    }
}
