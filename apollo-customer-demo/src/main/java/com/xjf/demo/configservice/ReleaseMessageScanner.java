package com.xjf.demo.configservice;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 定时任务扫描消息
 *
 * @author xjf
 * @date 2020/2/3 11:47
 */
@Component
public class ReleaseMessageScanner implements InitializingBean {

    @Autowired
    private NotificationControllerV2 configController;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 定时任务从数据库扫描有没有新的配置发布
        new Thread(() -> {
            for (;;) {
                String result = NotificationControllerV2.queue.poll();
                if (Objects.nonNull(result)){
                    ReleaseMessage message = new ReleaseMessage();
                    message.setMessage(result);
                    configController.handleMessage(message);
                }
            }
        });
    }
}
