package com.xjf.demo.controller;

import com.xjf.demo.entity.TransactionMessage;
import com.xjf.demo.enums.TransactionMessageStatusEnum;
import com.xjf.demo.service.TransactionMessageService;
import com.xjf.demo.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 可靠性消息接口
 *
 */
@RestController
@RequestMapping(value="/message")
public class TransactionMessageController {
	
	@Autowired
	private TransactionMessageService transactionMessageService;
	
	/**
	 * 发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
	 * @param message  消息内容
	 * @return true 成功 | false 失败
	 */
	@PostMapping("/send")
	public boolean sendMessage(@RequestBody TransactionMessage message) {
		return transactionMessageService.sendMessage(message);
	}

	/**
	 * 批量发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
	 * @param messages  消息内容
	 * @return true 成功 | false 失败
	 */
	@PostMapping("/sends")
	public boolean sendMessage(@RequestBody List<TransactionMessage> messages) {
		return transactionMessageService.sendMessage(messages);
	}

	/**
	 * 确认消息被消费
	 * @param customerSystem  消费的系统
	 * @param messageId	消息ID
	 * @return
	 */
	@PostMapping("/confirm/customer")
	public boolean confirmCustomerMessage(@RequestParam("customerSystem")String customerSystem, 
			@RequestParam("messageId")Long messageId) {
		return transactionMessageService.confirmCustomerMessage(customerSystem, messageId);
	}

	/**
	 * 查询最早没有被消费的消息
	 * @param limit	查询条数
	 * @return
	 */
	@GetMapping("/waiting")
	public List<TransactionMessage> findByWatingMessage(@RequestParam("limit")int limit) {
	    // 模拟睡眠 30s
        /*try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return transactionMessageService.findByWaitingMessage(limit);
	}

	/**
	 * 确认消息死亡
	 * @param messageId 消息ID
	 * @return
	 */
	@PostMapping("/confirm/die")
	public boolean confirmDieMessage(@RequestParam("messageId")Long messageId) {
		return transactionMessageService.confirmDieMessage(messageId);
	}

	/**
	 * 累加发送次数
	 * @param messageId 消息ID
	 * @param sendDate  发送时间（task服务中的时间，防止服务器之间时间不同步问题）
	 * @return
	 */
	@PostMapping("/incrSendCount")
	public boolean incrSendCount(@RequestParam("messageId")Long messageId, @RequestParam("sendDate")String sendDate) {
		try {
			if (StringUtils.isBlank(sendDate)) {
				return transactionMessageService.incrSendCount(messageId, new Date());
			} else {
				return transactionMessageService.incrSendCount(messageId, DateUtils.str2Date(sendDate));
			}
		} catch (ParseException e) {
			System.err.println("传入ID：" + messageId + "传入时间：" + sendDate);

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 重新发送当前已死亡的消息
	 * @return
	 */
	@GetMapping("/send/retry")
	public boolean retrySendDieMessage() {
		return transactionMessageService.retrySendDieMessage();
	}

	/**
	 * 分页查询具体状态的消息
	 * @param pageNum
	 * @param pageSize
	 * @param status
	 * @return
	 */
	@GetMapping("/query")
	public List<TransactionMessage> findMessageByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
													  @RequestParam("status") int status) {
		return transactionMessageService.findMessageByPage(pageNum, pageSize, TransactionMessageStatusEnum.parse(status));
	}
}
