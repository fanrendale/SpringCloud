package com.xjf.demo.service;

import com.xjf.demo.client.TransactionMqServiceRemoteClient;
import com.xjf.demo.entity.Student;
import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xjf
 * @date 2020/2/15 13:56
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private TransactionMqServiceRemoteClient transactionMqServiceRemoteClient;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(Student student) {
        // 假设进行了数据修改，然后将修改消息发给 MQ
        System.err.println("修改后的数据：" + student);

        TransactionMessage message = new TransactionMessage();
        // 此处 ID 只是模拟作用，实际不会这样
        message.setId(student.getId());
        message.setQueue("student_queue");
        message.setCreateDate(new Date());
        message.setSendSystem("student-service");
        message.setMessage(JsonUtils.toJson(student));

        boolean result = transactionMqServiceRemoteClient.sendMessage(message);

        if (!result) {
            throw new RuntimeException(" 回滚事务 ");
        }

        return result;
    }
}
