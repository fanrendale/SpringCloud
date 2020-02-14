package com.xjf.demo.service;

import com.xjf.demo.dao.TransactionMessageDao;
import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.enums.TransactionMessageStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xjf
 * @date 2020/2/14 16:44
 */
@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {

    @Autowired
    private TransactionMessageDao transactionMessageDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendMessage(TransactionMessage message) {
        int count = 0;

        if (check(message)){
            count = transactionMessageDao.insert(message);
        }

        return count > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendMessage(List<TransactionMessage> messages) {
        for (TransactionMessage message : messages) {
            if (!check(message)){
                return false;
            }
        }

        int[] ints = transactionMessageDao.batchInsert(messages);
        System.out.println("批量插入：" + ints.toString());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        TransactionMessage message = transactionMessageDao.findById(messageId);
        if (Objects.isNull(message)){
            return false;
        }

        message.setCustomerDate(new Date());
        message.setStatus(TransactionMessageStatusEnum.OVER.getStatus());
        message.setCustomerSystem(customerSystem);
        return transactionMessageDao.update(message) > 0;
    }

    @Override
    public List<TransactionMessage> findByWaitingMessage(int limit) {
        if (limit > 1000){
            limit = 1000;
        }

        return transactionMessageDao.findByWaitingMessage(limit);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean confirmDieMessage(Long messageId) {
        TransactionMessage message = transactionMessageDao.findById(messageId);
        if (Objects.isNull(message)){
            return false;
        }

        message.setStatus(TransactionMessageStatusEnum.DIE.getStatus());
        message.setDieDate(new Date());
        return transactionMessageDao.update(message) > 0;
    }

    @Override
    public boolean incrSendCount(Long messageId, Date sendDate) {
        TransactionMessage message = transactionMessageDao.findById(messageId);
        if (Objects.isNull(message)){
            return false;
        }

        message.setSendDate(sendDate);
        message.setSendCount(message.getSendCount() + 1);

        return transactionMessageDao.update(message) > 0;
    }

    @Override
    public boolean retrySendDieMessage() {
        // 将死亡的消息的发送状态改为等待，发送次数修改为 0
        return transactionMessageDao.updateStatusAndSendCount();
    }

    @Override
    public List<TransactionMessage> findMessageByPage(int pageNum, int pageSize, TransactionMessageStatusEnum status) {
        return transactionMessageDao.findMessageByPage(pageNum, pageSize, status);
    }

    /*=============================================以下为私有方法=======================================*/

    /**
     * 检查消息内容不为空
     */
    private boolean check(TransactionMessage message) {
        if (StringUtils.isBlank(message.getMessage()) || StringUtils.isBlank(message.getQueue()) ||
                StringUtils.isBlank(message.getSendSystem())) {
            return false;
        }

        if (Objects.isNull(message.getCreateDate())){
            return false;
        }

        return true;
    }
}
