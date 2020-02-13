package com.xjf.es.repository;

import com.xjf.es.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xjf
 * @date 2020/2/13 16:11
 */
@Repository
public interface ArticleRepository  extends CrudRepository<Article, Long> {

    /**
     * 模糊查询，两个效果一样
     *
     * @param title
     * @return
     */
    List<Article> findByTitleLike(String title);

    /**
     * 模糊查询，两个效果一样
     * @param title
     * @return
     */
    List<Article> findByTitleContaining(String title);
}
