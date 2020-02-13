package com.xjf.es;

import com.xjf.es.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * ElasticsearchTemplate 方式操作 Elasticsearch
 *
 * @author xjf
 * @date 2020/2/13 18:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 模糊查询
     */
    @Test
    public void queryByTitle(){
        String title = "着";

        Article article = elasticsearchTemplate.queryForObject(
                new CriteriaQuery(Criteria.where("title").contains(title)),
                Article.class
        );

        System.err.println("查询结果：" + article);
    }

    /**
     * 多字段查询，类似：select * from article where title like '%keyword%' and sid=sid
     */
    @Test
    public void queryByAnd(){
        String title = "活";
        String sid = "stu123456";

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withIndices("xjf");
        queryBuilder.withTypes("article");
        // 给高亮字段添加前缀和后缀
        queryBuilder.withHighlightFields(
                new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>")
        );
        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", title))
                        .must(QueryBuilders.matchQuery("sid", sid))
        );

        buildResult(queryBuilder).forEach(System.err::println);
    }

    /**
     * 多字段查询，类似：select * from article where title like '%keyword%' or sid=sid
     */
    @Test
    public void queryByOr(){
        String title = "活";
        String sid = "stu12";

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withIndices("xjf");
        queryBuilder.withTypes("article");
        // 给高亮字段添加前缀和后缀
        queryBuilder.withHighlightFields(
                new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>")
        );
        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", title))
                        .should(QueryBuilders.matchQuery("sid", sid))
        );

        buildResult(queryBuilder).forEach(System.err::println);
    }

    /**
     * 根据标题模糊全文检索，高亮显示分词结果
     */
    @Test
    public void query(){
        NativeSearchQueryBuilder queryBuilder = builderQuery("活");

        buildResult(queryBuilder).forEach(System.err::println);
    }

    /**
     * 根据标题模糊全文检索，高亮显示分词结果,分页查询
     */
    @Test
    public void query1(){
        NativeSearchQueryBuilder queryBuilder = builderQuery("活");
        queryBuilder.withPageable(PageRequest.of(1,1));

        buildResult(queryBuilder).forEach(System.err::println);
    }

    /**
     * 根据标题模糊全文检索，查询总数量
     */
    @Test
    public void query2(){
        NativeSearchQueryBuilder queryBuilder = builderQuery("活着");

        System.err.println("匹配数量：" + elasticsearchTemplate.count(queryBuilder.build()));
    }

    /*=========================================下面为内部方法=========================================*/

    /**
     * 构造查询条件
     */
    private NativeSearchQueryBuilder builderQuery(String title){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withIndices("xjf");
        queryBuilder.withTypes("article");
        // 给高亮字段添加前缀和后缀
        queryBuilder.withHighlightFields(
                new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>")
        );
        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("title", title))
        );

        return queryBuilder;
    }

    /**
     * 构造结果
     */
    private List<Article> buildResult(NativeSearchQueryBuilder queryBuilder){
        return elasticsearchTemplate.query(queryBuilder.build(), new ResultsExtractor<List<Article>>() {
            @Override
            public List<Article> extract(SearchResponse searchResponse) {
                List<Article> list = new ArrayList<>();
                for (SearchHit hit : searchResponse.getHits()) {
                    Article article = new Article();
                    article.setId(Integer.parseInt(hit.getId()));
                    article.setTitle(hit.getHighlightFields().get("title").fragments()[0].toString());
                    article.setUrl(hit.getSource().get("url").toString());
                    article.setContent(hit.getSource().get("content").toString());
                    article.setSid(hit.getSource().get("sid").toString());
                    list.add(article);
                }

                return list;
            }
        });
    }
}
