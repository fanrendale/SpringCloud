package com.xjf.demo.api.factory;

import com.xjf.demo.api.client.UserRemoteClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 使用fallbackFactory方式实现feign整合hystrix服务容错，
 * 该方式可以得到具体的出错信息
 *
 * @author xjf
 * @date 2020/1/28 16:06
 */
@Component
public class UserRemoteClientFallbackFactory implements FallbackFactory<UserRemoteClient> {

    private Logger logger = LoggerFactory.getLogger(UserRemoteClientFallbackFactory.class);

    @Override
    public UserRemoteClient create(Throwable throwable) {
        logger.error("UserRemoteClient回退：" , throwable);

        return new UserRemoteClient() {
            @Override
            public String hello() {
                return "fail";
            }
        };
    }
}
