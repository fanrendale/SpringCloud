package com.xjf.demo;

import com.xjf.demo.entity.Article;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author xjf
 * @date 2020/2/12 9:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询符合条件的所有数据
     */
    @Test
    public void query1(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));

        List<Article> list = mongoTemplate.find(query, Article.class);
        list.forEach(System.err::println);
    }

    /**
     * 查询符合条件的第一条数据
     */
    @Test
    public void query2(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));

        Article article = mongoTemplate.findOne(query, Article.class);
        System.err.println(article);
    }

    /**
     * 查询集合中所有数据，不加条件
     */
    @Test
    public void query3(){
        List<Article> list = mongoTemplate.findAll(Article.class);
        list.forEach(System.err::println);
    }

    /**
     * 查询符合条件的数据条数
     */
    @Test
    public void query4(){
        Query query = Query.query(Criteria.where("author").is("盖伦"));

        long count = mongoTemplate.count(query, Article.class);
        System.err.println("匹配条数：" + count);
    }

    /**
     * 根据主键 id 查询
     */
    @Test
    public void query5(){
        Article article = mongoTemplate.findById(new ObjectId("5e42b08798b55f384823d966"), Article.class);
        System.err.println(article);
    }

    /**
     * in 查询
     */
    @Test
    public void query6(){
        List<String> list = Arrays.asList("盖伦", "咖啡");
        Query query = Query.query(Criteria.where("author").in(list));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * ne(!=) 查询
     */
    @Test
    public void query7(){
        Query query = Query.query(Criteria.where("author").ne("盖伦"));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * lt(<) 小于的使用
     */
    @Test
    public void query8(){
        Query query = Query.query(Criteria.where("visit_count").lt(13));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * gt：大于。 lt：小于。范围查询
     */
    @Test
    public void query9(){
        Query query = Query.query(Criteria.where("visit_count").gt(11).lt(17));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * 模糊查询，author 中包含 "咖"
     */
    @Test
    public void query10(){
        Query query = Query.query(Criteria.where("author").regex("咖"));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * 数组查询，查询 tags 里数量为 3 的数据
     */
    @Test
    public void query11(){
        Query query = Query.query(Criteria.where("tags").size(3));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }

    /**
     * or 查询
     */
    @Test
    public void query12(){
        // 查询作者为 盖伦 ，或者访问量为 0 的数据。
        // 在 orOperator 的前面条件会先进行过滤，然后再执行 orOperator 里面的过滤条件
        Query query = Query.query(Criteria.where("author").is("盖伦").orOperator(
                Criteria.where("visitCount").is(0)
        ));

        List<Article> articleList = mongoTemplate.find(query, Article.class);
        articleList.forEach(System.err::println);
    }
}
