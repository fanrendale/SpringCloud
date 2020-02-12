package com.xjf.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 批量更新封装的实体
 *
 * @author xjf
 * @date 2020/2/12 17:02
 */
@Data
public class BatchUpdateOptions {

    private Query query;

    private Update update;

    private boolean upsert = false;

    private boolean multi = false;

    public BatchUpdateOptions(Query query, Update update, boolean upsert, boolean multi) {
        this.query = query;
        this.update = update;
        this.upsert = upsert;
        this.multi = multi;
    }

    public BatchUpdateOptions() {
    }
}
