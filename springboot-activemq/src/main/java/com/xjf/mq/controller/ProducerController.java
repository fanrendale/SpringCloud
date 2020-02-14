package com.xjf.mq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

/**
 * @author xjf
 * @date 2020/2/14 12:13
 */
@RestController
public class ProducerController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    /**
     * 发送消息
     * @return
     */
    @GetMapping("/send/{message}")
    public String send(@PathVariable String message){
        jmsMessagingTemplate.convertAndSend(this.queue,message);

        return "消息发送成功";
    }
}
