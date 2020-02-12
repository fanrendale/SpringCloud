package com.xjf.demo;

import com.mongodb.client.result.UpdateResult;
import com.xjf.demo.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MongoDB 的修改测试
 *
 * @author xjf
 * @date 2020/2/11 18:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 对匹配条件的第一条数据进行修改
     */
    @Test
    public void updateFirst(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));
        Update update = Update.update("title", "喷子的基本素养111");

        UpdateResult result = mongoTemplate.updateFirst(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * 对匹配条件的所有数据进行修改
     */
    @Test
    public void updateMulti(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));
        // 此处的属性下划线写法和驼峰法都可以，比如：visitCount 和 visit_count 效果一样
        Update update = Update.update("title", "活着").set("visit_count", 11);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * 特殊更新：以条件进行更新，如果能查询到则直接更新，如果条件没有查到则直接新增一条数据
     */
    @Test
    public void upsert(){
        Query query = Query.query(Criteria.where("author").is("莫言"));
        Update update = Update.update("title", "丰乳肥臀").set("visit_count", 12);

        UpdateResult result = mongoTemplate.upsert(query, update, Article.class);
        // 如果是新增，则修改数为 0
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * 查询数据能查到，但是修改的属性如果是没有的，则会在文档中新增这个属性
     */
    @Test
    public void updateInsertKey(){
        Query query = Query.query(Criteria.where("author").is("莫言"));
        Update update = Update.update("ISBN", "KJ456789").set("price", 56);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * update 的 inc 方法用于对指定属性累加对应的数值。如果 inc 的值不是数值型，则会报错
     */
    @Test
    public void inc(){
        Query query = Query.query(Criteria.where("author").is("莫言"));
        Update update = Update.update("ISBN", "KJ456789").inc("title", 44);

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * update 的 rename 方法对属性 key 进行修改
     */
    @Test
    public void rename(){
        Query query = Query.query(Criteria.where("author").is("莫言"));
        Update update = Update.update("ISBN", "KJ456789").rename("price", "money");

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * update 的 unset 方法用于对属性进行删除。如果属性不存在，则删除 key 数量为 0
     */
    @Test
    public void unset(){
        Query query = Query.query(Criteria.where("author").is("莫言"));
        Update update = Update.update("ISBN", "KJ456789").unset("money");

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }

    /**
     * update 中的 pull 方法删除数组的具体某个值。如果属性不存在，会删除空气，不报错
     */
    @Test
    public void pull(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));
        Update update = Update.update("title", "辅助的职责").pull("tags", "英雄联盟");

        UpdateResult result = mongoTemplate.updateMulti(query, update, Article.class);
        System.out.println("修改条数: " + result.getModifiedCount());
    }
}
