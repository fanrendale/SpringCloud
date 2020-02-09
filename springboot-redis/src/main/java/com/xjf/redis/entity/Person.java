package com.xjf.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 将实体与 Redis 的存储结合起来， RedisHash 注解的值是 Hash 的名称，相当于数据库的表名
 *
 * @author xjf
 * @date 2020/2/9 22:07
 */
@Data
@RedisHash("person")
public class Person {

    /**
     * 在 Redis 中使用该注解类似于主键,会自增（是一串字符），
     * 比如：id=fe15df52-830d-47f2-9b62-31021ae012c7
     */
    @Id
    private String id;

    private String username;

    private String password;

    public Person(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
