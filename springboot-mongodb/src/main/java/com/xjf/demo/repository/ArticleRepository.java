package com.xjf.demo.repository;

import com.xjf.demo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xjf
 * @date 2020/2/12 14:09
 */
@Repository("articleRepository")
public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Override
    Page<Article> findAll(Pageable pageable);

    /**
     * 根据作者查询
     *
     * @param author
     * @return
     */
    List<Article> findByAuthor(String author);

    /**
     * 根据作者和标题查询
     *
     * @param author
     * @param title
     * @return
     */
    List<Article> findByAuthorAndTitle(String author, String title);

    /**
     * 忽略参数大小写
     *
     * @param author
     * @return
     */
    List<Article> findByAuthorIgnoreCase(String author);

    /**
     * 忽略所有参数大小写
     *
     * @param author
     * @param title
     * @return
     */
    List<Article> findByAuthorAndTitleAllIgnoreCase(String author, String title);

    /**
     * 排序：降序
     *
     * @param author
     * @return
     */
    List<Article> findByAuthorOrderByVisitCountDesc(String author);

    /**
     * 排序：升序
     *
     * @param author
     * @return
     */
    List<Article> findByAuthorOrderByVisitCountAsc(String author);

    /**
     *  自带排序条件
     *
     * @param author
     * @param sort
     * @return
     */
    List<Article> findByAuthor(String author, Sort sort);
}
