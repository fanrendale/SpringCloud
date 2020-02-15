package com.xjf.demo.task;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xjf
 * @date 2020/2/15 10:57
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(String queue, String msg){
        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(queue), msg);
        System.err.println("发送到 MQ 中成功: " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
