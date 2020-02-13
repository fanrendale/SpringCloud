package com.xjf.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Document : 指定索引的名称 indexName 及 索引的类型 type
 * Filed : 指定了数据的类型，是否使用分词器，及是否需要存储等信息。
 *
 * @author xjf
 * @date 2020/2/13 16:06
 */
@Data
@Document(indexName = "xjf", type = "article")
public class Article {

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String sid;

    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Keyword)
    private String content;
}
