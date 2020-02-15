package com.xjf.demo.dto;

import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/15 10:52
 */
@Data
public class MessageDto {

    /**
     * 消息 ID
     */
    private Long messageId;

    /**
     *  消息内容
     */
    private String msg;
}
