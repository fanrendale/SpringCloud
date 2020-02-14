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
        return false;
    }

    @Override
    public boolean sendMessage(List<TransactionMessage> messages) {
        return false;
    }

    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        return false;
    }

    @Override
    public List<TransactionMessage> findByWatingMessage(int limit) {
        System.err.println("消息服务的 findByWatingMessage 方法调用失败");
        return null;
    }

    @Override
    public boolean confirmDieMessage(Long messageId) {
        return false;
    }

    @Override
    public boolean incrSendCount(Long messageId, String sendDate) {
        return false;
    }

    @Override
    public boolean retrySendDieMessage() {
        return false;
    }

    @Override
    public List<TransactionMessage> findMessageByPage(int pageNum, int pageSize, int status) {
        return null;
    }
}
