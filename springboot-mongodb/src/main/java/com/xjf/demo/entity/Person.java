package com.xjf.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 测试索引
 *
 * 注解实现组合索引： name 表示索引的名称， def 表示组合索引的字段和索引存储升序（1）或者降序（-1）
 *
 * @author xjf
 * @date 2020/2/11 16:18
 */
@Document(collection = "person")
@CompoundIndexes({
        @CompoundIndex(name = "city_region_idx", def = "{'city':1, 'region':1}")
})
@Data
public class Person {

    @Id
    private String id;

    /**
     * 唯一索引
     */
    @Indexed(unique = true)
    private String name;

    /**
     * 后台方式创建索引
     */
    @Indexed(background = true)
    private int age;

    private String city;

    private String region;

}
