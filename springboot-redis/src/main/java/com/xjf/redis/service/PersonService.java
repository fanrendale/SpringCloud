package com.xjf.redis.service;

import com.xjf.redis.entity.Person;

/**
 * @author xjf
 * @date 2020/2/10 11:25
 */
public interface PersonService {

    Person get(String id);
}
