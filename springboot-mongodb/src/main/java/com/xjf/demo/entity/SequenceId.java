package com.xjf.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 自定义集合的自增序列实体
 *
 * @author xjf
 * @date 2020/2/12 15:37
 */
@Document(collection = "sequence")
@Data
public class SequenceId {

    @Id
    private String id;

    /**
     * 自增 ID 值（序列值）
     */
    @Field("seq_id")
    private long seqId;

    /**
     * 集合名称
     */
    @Field("coll_name")
    private String collName;
}
