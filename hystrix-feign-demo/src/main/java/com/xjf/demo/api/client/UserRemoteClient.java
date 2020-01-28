package com.xjf.demo.api.client;

import com.xjf.demo.api.fallback.UserRemoteClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户服务的客户端
 *
 * @author xjf
 * @date 2020/1/28 15:43
 */
@FeignClient(value = "eureka-client-user-service", fallback = UserRemoteClientFallback.class)
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
