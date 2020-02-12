package com.xjf.demo;

import com.xjf.demo.entity.Article;
import com.xjf.demo.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Repository 的测试
 *
 * @author xjf
 * @date 2020/2/12 14:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 查询所有
     */
    @Test
    public void repo1(){
        Iterable<Article> articleIterable = articleRepository.findAll();
        articleIterable.forEach(System.err::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void repo2(){
        Page<Article> page = articleRepository.findAll(PageRequest.of(0, 3));
        System.out.println("总数量：" + page.getTotalElements());
        System.out.println("总页数：" + page.getTotalPages());
        page.forEach(System.err::println);
    }

    /**
     * 根据作者查询
     */
    @Test
    public void repo3(){
        List<Article> articleList = articleRepository.findByAuthor("咖啡");
        articleList.forEach(System.err::println);
    }

    /**
     * 根据作者和标题查询
     */
    @Test
    public void repo4(){
        List<Article> articleList = articleRepository.findByAuthorAndTitle("盖伦", "补兵与守塔4");
        articleList.forEach(System.err::println);
    }

    /**
     * 忽略参数大小写
     */
    @Test
    public void repo5(){
        List<Article> articleList = articleRepository.findByAuthorIgnoreCase("XjF");
        articleList.forEach(System.err::println);
    }

    /**
     * 忽略所有参数大小写
     */
    @Test
    public void repo6(){
        List<Article> articleList = articleRepository.findByAuthorAndTitleAllIgnoreCase("XjF", "补兵与守塔11");
        articleList.forEach(System.err::println);
    }

    /**
     * 排序：降序
     */
    @Test
    public void repo7(){
        List<Article> articleList = articleRepository.findByAuthorOrderByVisitCountDesc("盖伦");
        articleList.forEach(System.err::println);
    }

    /**
     * 排序：升序
     */
    @Test
    public void repo8(){
        List<Article> articleList = articleRepository.findByAuthorOrderByVisitCountAsc("盖伦");
        articleList.forEach(System.err::println);
    }

    /**
     * 自带排序条件
     */
    @Test
    public void repo9(){
        List<Article> articleList = articleRepository.findByAuthor("盖伦", new Sort(Sort.Direction.DESC, "visitCount"));
        articleList.forEach(System.err::println);
    }
}
