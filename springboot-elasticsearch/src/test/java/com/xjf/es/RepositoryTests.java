package com.xjf.es;

import com.xjf.es.entity.Article;
import com.xjf.es.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * repository 方式操作 Elasticsearch 的
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 新增
     */
    @Test
    public void testAdd(){
        Article article = new Article();
        article.setId(2);
        article.setSid("stu456789");
        article.setTitle("活得开心");
        article.setUrl("http://www.baidu.com");
        article.setContent("hello world 福贵要坚强");

        Article result = articleRepository.save(article);
        System.err.println("插入数据：" + result);
    }

    /**
     * 查询
     */
    @Test
    public void testList(){
        Iterable<Article> iterable = articleRepository.findAll();
        iterable.forEach(System.err::println);
    }

    /**
     * 模糊查询
     */
    @Test
    public void testQuery(){

        // 两个模糊查询效果一样
        List<Article> list = articleRepository.findByTitleLike("活");
        list.forEach(System.err::println);

        System.out.println("===========================================");

        List<Article> articleList = articleRepository.findByTitleContaining("着");
        articleList.forEach(System.err::println);
    }
}
