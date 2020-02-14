package com.xjf.demo.entity;

import com.xjf.demo.enums.TransactionMessageStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author xjf
 * @date 2020/2/14 14:20
 */
@Data
public class TransactionMessage {

    /**
     * 消息 ID
     */
    private Long id;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 队列名称
     */
    private String queue;

    /**
     * 发送消息的系统
     */
    private String sendSystem;

    /**
     * 重复发送消息次数
     */
    private int sendCount;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最近发送消息时间
     */
    private Date SendDate;

    /**
     * 状态：0-等待消费 1-已消费 2-已死亡
     */
    private int status = TransactionMessageStatusEnum.WATING.getStatus();

    /**
     * 死亡次数条件，由使用方决定，默认发送 10 次还没被消费则标记死亡，人工介入
     */
    private int dieCount = 10;

    /**
     * 消费时间
     */
    private Date customerDate;

    /**
     * 消费系统
     */
    private String customerSystem;

    /**
     * 死亡时间
     */
    private Date dieDate;
}
