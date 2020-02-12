package com.xjf.demo.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.xjf.demo.entity.BatchUpdateOptions;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB 批量更新的实现
 *
 * @author xjf
 * @date 2020/2/12 17:04
 */
public class MongoBaseDao {

    /**
     * 批量更新的实现
     *
     * @param mongoTemplate
     * @param collName 批量更新的集合名称
     * @param options 更新操作的集合
     * @param ordered
     * @return
     */
    public static int batchUpdate(MongoTemplate mongoTemplate, String collName,
                                  List<BatchUpdateOptions> options, boolean ordered){
        BasicDBObject command = new BasicDBObject();

        command.put("update", collName);
        List<BasicDBObject> updateList = new ArrayList<>();
        for (BatchUpdateOptions option : options) {
            BasicDBObject update = new BasicDBObject();
            update.put("q", option.getQuery().getQueryObject());
            update.put("u", option.getUpdate().getUpdateObject());
            update.put("upsert", option.isUpsert());
            update.put("multi", option.isMulti());
            updateList.add(update);
        }
        command.put("updates", updateList);
        command.put("ordered", ordered);

        Document commandResult = mongoTemplate.getDb().runCommand(command);
        return Integer.parseInt(commandResult.get("n").toString());
    }
}
