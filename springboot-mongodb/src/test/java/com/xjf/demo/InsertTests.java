package com.xjf.demo;

import com.mongodb.client.ListIndexesIterable;
import com.xjf.demo.entity.Article;
import com.xjf.demo.entity.Person;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 测试 MongoDB 添加数据一条一条插入
     */
    @Test
    public void insertOne(){
        for (int i = 0; i < 5; i++) {
            Article article = new Article();
            article.setTitle("补兵与守塔" + i);
            article.setAuthor("盖伦");
            article.setUrl("http://www.xjf666.xyz");
            article.setAddTime(LocalDateTime.now());
            article.setTags(Arrays.asList("LOL", "英雄联盟", "基础操作"));
            article.setVisitCount((long) (i * 2 + 10));
            // 一条一条插入
            mongoTemplate.save(article);
        }
    }

    /**
     * 测试 MongoDB 添加数据批量
     */
    @Test
    public void insertBatch(){
        List<Article> list = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            Article article = new Article();
            article.setTitle("补兵与守塔" + i);
            article.setAuthor("盖伦");
            article.setUrl("http://www.xjf666.xyz");
            article.setAddTime(LocalDateTime.now());
            article.setTags(Arrays.asList("LOL", "英雄联盟", "基础操作"));
            article.setVisitCount(0L);
            list.add(article);
        }
        // 批量插入
        mongoTemplate.insert(list, Article.class);
    }

    /**
     * 测试索引的生成
     */
    @Test
    public void indexCreate(){
        Person person = new Person();
        person.setName("布隆");
        person.setAge(24);
        person.setCity("成都市");
        person.setRegion("高新区");

        mongoTemplate.save(person);

        // 查询索引信息
        ListIndexesIterable<Document> indexes = mongoTemplate.getCollection("person").listIndexes();

        for (Document document : indexes) {
            System.out.println(document.toJson());
        }
    }
}
