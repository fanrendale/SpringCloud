package com.xjf.demo.task;

import com.xjf.demo.api.client.TransactionMqServiceRemoteClient;
import com.xjf.demo.dto.MessageDto;
import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author xjf
 * @date 2020/2/15 10:01
 */
@Service
@Slf4j
public class ProcessMessageTask {

    @Autowired
    private Producer producer;

    @Autowired
    private TransactionMqServiceRemoteClient transactionMqServiceRemoteClient;

    @Autowired
    private RedissonClient redisson;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    /**
     * 信号量控制并发数，最多 10 个线程同时执行
     */
    private Semaphore semaphore = new Semaphore(10);

    /**
     * 启动发送消息的线程，以及决定一批数据发送完成后的等待间隔时间。会一直重试
     */
    public  void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    // 获取 Redis 分布式锁。避免消息重复发送
                    final RLock lock = redisson.getLock("transaction-mq-task");
                    try {
                        lock.lock();
                        System.err.println("开始发送消息：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

                        // 发送完一次所有数据后，休息 10s
                        int sleepTime = process();
                        if (sleepTime > 0){
                            Thread.sleep(10000);
                        }
                    } catch (InterruptedException e) {
                        log.error("", e);
                    } finally {
                        // 释放锁
                        lock.unlock();
                    }
                }
            }
        });

        thread.start();
    }

    /**
     * 取等待发送的消息，以及线程控制和并发流量控制
     * @return
     * @throws InterruptedException
     */
    private int process() throws InterruptedException {
        // 默认执行完成后等待 10s
        int sleepTime = 10000;

        // 一次取出 5000 条等待发送的消息
        List<TransactionMessage> messageList = transactionMqServiceRemoteClient.findByWatingMessage(5000);
        System.err.println("取出消息条数：" + messageList.size());

        //如果取出的数量刚好等于 5000 ，则说明等待的消息还有。就设置休眠时间为 0，持续取消消息来发送
        if (messageList.size() == 5000){
            sleepTime = 0;
        }

        // 保证这一批数据处理完之后再处理后面的
        final CountDownLatch latch = new CountDownLatch(messageList.size());

        for (final TransactionMessage message : messageList) {
            // 获取信息量
            semaphore.acquire();

            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 执行发送消息
                        doProcess(message);
                    } catch (Exception e) {
                        log.error("", e);
                    } finally {
                        semaphore.release();
                        latch.countDown();
                    }
                }
            });
        }

        return sleepTime;
    }

    /**
     * 具体的消息发送实现
     * @param message
     */
    private void doProcess(TransactionMessage message){
        // 如果消息发送次数大于预定的死亡发送次数，则不再发送
        if (message.getSendCount() > message.getDieCount()){
            // 确定该消息死亡
            transactionMqServiceRemoteClient.confirmDieMessage(message.getId());
            return;
        }

        // 距离上次发送时间超过 30s 才能继续发送
        long currentTime = System.currentTimeMillis();
        long sendTime = 0;
        if (message.getSendDate() != null){
            sendTime = message.getSendDate().getTime();
        }

        if (currentTime - sendTime > 30 * 1000){
            System.err.println("发送具体消息: " + message.getId());

            // 向 MQ 发送消息
            MessageDto messageDto = new MessageDto();
            messageDto.setMessageId(message.getId());
            messageDto.setMsg(message.getMessage());
            producer.send(message.getQueue(), JsonUtils.toJson(messageDto));

            // 修改消息发送次数以及最近发送时间
            transactionMqServiceRemoteClient.incrSendCount(
                    message.getId(), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }
}
