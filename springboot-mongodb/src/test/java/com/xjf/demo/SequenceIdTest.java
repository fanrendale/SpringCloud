package com.xjf.demo;

import com.xjf.demo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xjf
 * @date 2020/2/12 15:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SequenceIdTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 测试自增 ID
     */
    @Test
    public void test(){
        Student student = new Student();
        student.setName("盖伦");

        mongoTemplate.save(student, "stu");

        Student resultStu = mongoTemplate.findOne(Query.query(Criteria.where("id").is(student.getId())), Student.class, "stu");
        System.err.println("查询结果" + resultStu);
    }

    @Test
    public void test2(){
        Student student = mongoTemplate.findOne(Query.query(Criteria.where("name").is("咖啡")), Student.class, "stu");
        System.out.println(student);

        mongoTemplate.findAll(Student.class).forEach(System.err::println);
        System.out.println(mongoTemplate.count(Query.query(Criteria.where("name").is("咖啡")), Student.class));
    }
}
