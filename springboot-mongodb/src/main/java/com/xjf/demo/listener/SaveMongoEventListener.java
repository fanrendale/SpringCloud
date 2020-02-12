package com.xjf.demo.listener;

import com.xjf.demo.annotation.GeneratedValue;
import com.xjf.demo.entity.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 实现自增 ID 的逻辑
 *
 * @author xjf
 * @date 2020/2/12 15:43
 */
public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();

        if (source != null){
            ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
                @Override
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
                    if (field.isAnnotationPresent(GeneratedValue.class)){
                        // 此处获取的集合名称是在序列集合存储的
                        System.err.println("集合名称：" + source.getClass().getSimpleName());
                        // 设置自增 ID
                        field.set(source, getNextId(source.getClass().getSimpleName()));
                    }
                }
            });
        }
    }

    /**
     * 获取下一个自增 ID
     *
     * @param collName
     * @return
     */
    private Long getNextId(String collName){
        // 此处获取的集合名称是在序列集合存储的
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);

        SequenceId sequenceId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);

        System.out.println("当前集合名称：" + sequenceId.getCollName() + "     新增的 ID: " + sequenceId.getSeqId());
        return sequenceId.getSeqId();
    }
}
