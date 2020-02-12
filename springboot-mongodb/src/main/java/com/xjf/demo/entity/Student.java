package com.xjf.demo.entity;

import com.xjf.demo.annotation.GeneratedValue;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 学生实体，用来测试自增 ID
 *
 * @author xjf
 * @date 2020/2/12 15:41
 */
@Document(collection = "stu")
@Data
public class Student {

    /**
     * 自增 ID 的类型不能定义成 Long 这种包装类。MongoTemplate 的源码里面对主键 ID 有限制
     */
    @GeneratedValue
    @Id
    private long id;

    private String name;
}
