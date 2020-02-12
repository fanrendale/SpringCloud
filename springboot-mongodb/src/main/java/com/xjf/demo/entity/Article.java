package com.xjf.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 注解说明：
 *  Document 注解标识是一个文档，等同于 MySQL 表， collection 值表示 MongoDB 中集合的名称，不写的话
 *  就默认为实体类名 article
 *  Id 注解为主键标识
 *  Field 注解为字段标识，指定值为字段名称。
 *
 * @author xjf
 * @date 2020/2/11 15:51
 */
@Data
@Document
public class Article {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("url")
    private String url;

    @Field("author")
    private String author;

    @Field("tags")
    private List<String> tags;

    @Field("visit_count")
    private Long visitCount;

    @Field("add_time")
    private LocalDateTime addTime;
}
