package com.xjf.demo;

import com.xjf.demo.dao.TransactionMessageDao;
import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.enums.TransactionMessageStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionMessageDao transactionMessageDao;

    @Test
    public void add(){
        TransactionMessage message = new TransactionMessage();
        message.setId(1L);
        message.setMessage("测试消息");
        message.setQueue("test");
        message.setSendSystem("sendService");
        message.setSendCount(0);
        message.setCreateDate(new Date());
        message.setSendDate(new Date());
        message.setStatus(TransactionMessageStatusEnum.WATING.getStatus());
        message.setDieCount(0);
        message.setCustomerDate(new Date());
        message.setCustomerSystem("customerService");
        message.setDieDate(new Date());

        System.out.println("插入条数：" + insert(message));

    }

    private int insert(TransactionMessage message){
        System.out.println(message);
        String sql = "insert into transaction_message(id, message, queue, send_system, send_count, c_date" +
                ", send_date, status, die_count, customer_date, customer_system, die_date) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try {
            return jdbcTemplate.update(sql, message.getId(), message.getMessage(), message.getQueue(), message.getSendSystem(), message.getSendCount(),
                    message.getCreateDate(), message.getSendDate(), message.getStatus(), message.getDieCount(),
                    message.getCustomerDate(), message.getCustomerSystem(), message.getDieDate());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Test
    public void find(){
        TransactionMessage message = transactionMessageDao.findById(1L);
        System.err.println(message);
    }
}
