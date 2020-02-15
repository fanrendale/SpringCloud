package com.xjf.demo.jms;

import com.xjf.demo.client.TransactionMqServiceRemoteClient;
import com.xjf.demo.dto.MessageDto;
import com.xjf.demo.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 此处模拟为另一个服务（非学生服务），接收到 MQ 消息然后进行对应修改
 *
 * @author xjf
 * @date 2020/2/15 14:08
 */
@Component
public class ActiveMqCustomer {

    @Autowired
    private TransactionMqServiceRemoteClient transactionMqServiceRemoteClient;

    /**
     * 接收消息，然后进行修改
     * @param text
     */
    @JmsListener(destination = "student_queue")
    public void receiveQueue(TextMessage text){
        try {
            System.err.println("消费者，可靠消息服务消费消息：" + text.getText());

            MessageDto dto = JsonUtils.toBean(MessageDto.class, text.getText());
            System.err.println("执行了修改：" + dto);

            // 执行业务修改后，确认该消息已被消费
            boolean result = transactionMqServiceRemoteClient.confirmCustomerMessage("customer-service", dto.getMessageId());

            // 手动确认 ACK
            if (result){
                System.err.println("消息消费成功！！！");
                text.acknowledge();
            }

        } catch (JMSException e) {
            // 异常时会触发重试机制，重试次数完成之后还是错误，消息会进入 DLQ 队列中
            throw new RuntimeException(e);
        }
    }
}
