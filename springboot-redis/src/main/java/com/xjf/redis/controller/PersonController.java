package com.xjf.redis.controller;

import com.xjf.redis.entity.Person;
import com.xjf.redis.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试用 Repository 操作 Redis
 *
 * @author xjf
 * @date 2020/2/9 22:15
 */
@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping("/repo")
    public String basicCrudOperations() throws InterruptedException {
        Person person = new Person("xjf","123456");
        Person person1 = new Person("盖伦","888888");

        repository.save(person);
        repository.save(person1);
        repository.findById(person.getId()).ifPresent(p -> System.out.println("查询得到值：" + p));

        System.out.println("Person 在 Redis 中的数量：" + repository.count());

        // 睡眠一分钟
        TimeUnit.MINUTES.sleep(1);

        // 删除
        repository.delete(person);

        return "success";
    }
}
