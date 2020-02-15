package com.xjf.demo.api.client;

import com.xjf.demo.entity.TransactionMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 降级服务的实现
 *
 * @author xjf
 * @date 2020/2/14 22:04
 */
@Component
public class TransactionMqServiceRemoteClientHystrix implements TransactionMqServiceRemoteClient{


    @Override
    public boolean sendMessage(TransactionMessage message) {
        System.err.println("消息服务的 sendMessage 方法调用失败");

        return false;
    }

    @Override
    public boolean sendMessage(List<TransactionMessage> messages) {
        System.err.println("消息服务的 sendMessage 方法调用失败");

        return false;
    }

    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        System.err.println("消息服务的 confirmCustomerMessage 方法调用失败");

        return false;
    }

    @Override
    public List<TransactionMessage> findByWatingMessage(int limit) {
        System.err.println("消息服务的 findByWatingMessage 方法调用失败");
        return null;
    }

    @Override
    public boolean confirmDieMessage(Long messageId) {
        System.err.println("消息服务的 confirmDieMessage 方法调用失败");

        return false;
    }

    @Override
    public boolean incrSendCount(Long messageId, String sendDate) {
        System.err.println("消息服务的 incrSendCount 方法调用失败");

        return false;
    }

    @Override
    public boolean retrySendDieMessage() {
        System.err.println("消息服务的 retrySendDieMessage 方法调用失败");

        return false;
    }

    @Override
    public List<TransactionMessage> findMessageByPage(int pageNum, int pageSize, int status) {
        System.err.println("消息服务的 findMessageByPage 方法调用失败");

        return null;
    }
}
