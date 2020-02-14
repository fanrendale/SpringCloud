package com.xjf.mq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 *
 * @author xjf
 * @date 2020/2/14 12:05
 */
@Component
public class MyConsumer {

    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "test")
    public void receiveQueue(String consumer){
        System.err.println(consumer + " 消息已经消费了");
    }
}
