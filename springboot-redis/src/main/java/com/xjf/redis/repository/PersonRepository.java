package com.xjf.redis.repository;

import com.xjf.redis.entity.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * 参数泛型的第一个参数为实体类型，第二个参数为主键类型
 *
 * @author xjf
 * @date 2020/2/9 22:10
 */
public interface PersonRepository extends CrudRepository<Person, String> {
}
