package com.xjf.demo.api.fallback;

import com.xjf.demo.api.client.UserRemoteClient;
import org.springframework.stereotype.Component;

/**
 * 用户服务的降级调用类
 *
 * @author xjf
 * @date 2020/1/28 15:45
 */
@Component
public class UserRemoteClientFallback implements UserRemoteClient {
    @Override
    public String hello() {
        return "fail";
    }
}
