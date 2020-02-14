/*
package com.xjf.mq.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

*/
/**
 * 消息生产者
 *
 * @author xjf
 * @date 2020/2/14 12:02
 *//*

@Component
@EnableScheduling
public class MyProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    */
/**
     * 每 2s 执行一次
     *//*

    @Scheduled(fixedDelay = 2000)
    public void send(){
        this.jmsMessagingTemplate.convertAndSend(this.queue,"hello, activeMQ");
    }
}
*/
