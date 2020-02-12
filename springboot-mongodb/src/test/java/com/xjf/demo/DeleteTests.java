package com.xjf.demo;

import com.mongodb.client.result.DeleteResult;
import com.xjf.demo.entity.Article;
import com.xjf.demo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xjf
 * @date 2020/2/11 21:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static int i = 1;

    /**
     * 删除指定条件下的数据
     */
    @Test
    public void remove1(){
        Query query = Query.query(Criteria.where("title").is("辅助的职责11"));

        DeleteResult remove = mongoTemplate.remove(query, Article.class);
        System.out.println("删除了条数：" + remove.getDeletedCount());
    }

    /**
     * 如果在删除的时候，实体类没有指明集合名称，可以在删除的时候写死指定
     */
    @Test
    public void remove2(){
        Query query = Query.query(Criteria.where("title").is("辅助的职责12"));

        DeleteResult remove = mongoTemplate.remove(query, "article");
        System.out.println("删除了条数：" + remove.getDeletedCount());
    }

    /**
     * 根据条件查询，即使符合条件数据有多个，也是只会删除第一个
     */
    @Test
    public void remove3(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));

        Article removeArticle = mongoTemplate.findAndRemove(query, Article.class);
        System.out.println("删除的数据：" + removeArticle);
    }

    /**
     * 删除符合条件的所有数据，并将删除数据返回
     */
    @Test
    public void remove4() throws InterruptedException {
        Query query = Query.query(Criteria.where("author").is("盖伦"));

        List<Article> removeArticles = mongoTemplate.findAllAndRemove(query, Article.class);
        System.out.println("删除的数据：" );
        removeArticles.forEach(System.out::println);

        // 睡眠 30s 再添加上
        TimeUnit.SECONDS.sleep(30);

        mongoTemplate.insert(removeArticles, Article.class);
    }

    /**
     * 删除集合：可以传实体，也可以传名称
     */
    @Test
    public void remove5()  {
//        mongoTemplate.dropCollection(Person.class);
        mongoTemplate.dropCollection("person");
    }

    /**
     * 删除数据库
     */
    @Test
    public void remove6(){
        mongoTemplate.getDb().drop();
    }
}
