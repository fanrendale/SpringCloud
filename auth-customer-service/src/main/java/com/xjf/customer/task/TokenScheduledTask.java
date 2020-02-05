package com.xjf.customer.task;

import com.xjf.auth.common.ResponseData;
import com.xjf.customer.api.client.AuthRemoteClient;
import com.xjf.customer.api.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时刷新 token
 *
 * @author xjf
 * @date 2020/2/5 16:53
 */
@Slf4j
@Component
public class TokenScheduledTask {

    /**
     * 默认每20小时刷新一次（ token 的有效期默认为24小时）
     */
    public final static long TWENTY_HOURS = 60 * 1000 * 60 * 20;

    @Autowired
    private AuthRemoteClient authRemoteClient;

    /**
     * 刷新 Token
     */
    @Scheduled(fixedDelay = TWENTY_HOURS)
    public void reloadApiToken(){
        String token = this.getToken();

        // 如果没获取到 token 值，则循环调用
        while(StringUtils.isBlank(token)){
            try {
                Thread.sleep(1000);
                token = this.getToken();
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }

        // 将 token 值存储到本地的环境变量中
        System.setProperty("customer.auth.token", token);
    }

    /**
     * 获取 Token
     * @return
     */
    public String getToken(){
        System.err.println("正在调用 authService 获取 token");

        // 此处写死账号密码。如果实际项目可以从 Redis 中获取，或者 Shiro 等框架中获取。
        User user = new User();
        user.setUsername("xjf");
        user.setPassword("123456");

        ResponseData responseData = authRemoteClient.auth(user);
        return responseData.getData() == null ? "" : responseData.getData().toString();
    }
}
