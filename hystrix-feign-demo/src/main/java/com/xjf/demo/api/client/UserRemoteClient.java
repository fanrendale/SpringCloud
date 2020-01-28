package com.xjf.demo.api.client;

import com.xjf.demo.api.factory.UserRemoteClientFallbackFactory;
import com.xjf.demo.api.fallback.UserRemoteClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户服务的客户端
 *
 * Fallback 和 FallbackFactory 区别：使用 FallbackFactory 方式可以获取失败的具体信息
 *
 * @author xjf
 * @date 2020/1/28 15:43
 */
//使用Fallback类方式
//@FeignClient(value = "eureka-client-user-service", fallback = UserRemoteClientFallback.class)
//使用FallbackFactory方式
@FeignClient(value = "eureka-client-user-service", fallbackFactory = UserRemoteClientFallbackFactory.class)
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
