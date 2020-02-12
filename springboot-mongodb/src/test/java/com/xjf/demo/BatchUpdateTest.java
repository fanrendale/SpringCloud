package com.xjf.demo;

import com.xjf.demo.dao.MongoBaseDao;
import com.xjf.demo.entity.Article;
import com.xjf.demo.entity.BatchUpdateOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjf
 * @date 2020/2/12 17:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchUpdateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 批量更新使用
     */
    @Test
    public void test(){
        List<BatchUpdateOptions> list = new ArrayList<>();

        // 添加更新操作
        list.add(
                new BatchUpdateOptions(Query.query(Criteria.where("title").is("补兵与守塔10")),
                        Update.update("author","李白"), true, true)
        );
        list.add(
                new BatchUpdateOptions(Query.query(Criteria.where("title").is("补兵与守塔12")),
                        Update.update("author","杜甫"), true, true)
        );

        int count = MongoBaseDao.batchUpdate(mongoTemplate, "article", list, true);
        System.out.println("受影响的行数：" + count);

    }
}
