package com.xjf.customer.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用 auth-user-service 服务
 *
 * @author xjf
 * @date 2020/2/5 15:49
 */
@FeignClient(value = "auth-user-service")
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
