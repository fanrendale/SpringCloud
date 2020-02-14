package com.xjf.demo.api.client;

import com.xjf.demo.entity.TransactionMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 可靠性消息服务的 API 调用
 *
 * @author xjf
 * @date 2020/2/14 22:04
 */
@FeignClient(value = "transaction-mq-service", path = "/message", fallback = TransactionMqServiceRemoteClientHystrix.class)
public interface TransactionMqServiceRemoteClient {

    /**
     * 发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     *
     * @param message 消息内容
     * @return true 成功 | false 失败
     */
    @PostMapping("/send")
    boolean sendMessage(@RequestBody TransactionMessage message);

    /**
     * 批量发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     *
     * @param messages 消息内容
     * @return true 成功 | false 失败
     */
    @PostMapping("/sends")
    boolean sendMessage(@RequestBody List<TransactionMessage> messages);

    /**
     * 确认消息被消费
     *
     * @param customerSystem 消费的系统
     * @param messageId      消息ID
     * @return
     */
    @PostMapping("/confirm/customer")
    boolean confirmCustomerMessage(@RequestParam("customerSystem") String customerSystem,
                                   @RequestParam("messageId") Long messageId);

    /**
     * 查询最早没有被消费的消息
     *
     * @param limit 查询条数
     * @return
     */
    @GetMapping("/waiting")
    List<TransactionMessage> findByWatingMessage(@RequestParam("limit") int limit);

    /**
     * 确认消息死亡
     *
     * @param messageId 消息ID
     * @return
     */
    @PostMapping("/confirm/die")
    boolean confirmDieMessage(@RequestParam("messageId") Long messageId);

    /**
     * 累加发送次数
     *
     * @param messageId 消息ID
     * @param sendDate  发送时间（task服务中的时间，防止服务器之间时间不同步问题）
     * @return
     */
    @PostMapping("/incrSendCount")
    boolean incrSendCount(@RequestParam("messageId") Long messageId, @RequestParam("sendDate") String sendDate);

    /**
     * 重新发送当前已死亡的消息
     *
     * @return
     */
    @GetMapping("/send/retry")
    boolean retrySendDieMessage();

    /**
     * 分页查询具体状态的消息
     *
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/query")
    List<TransactionMessage> findMessageByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                                               @RequestParam("status") int status);
}
