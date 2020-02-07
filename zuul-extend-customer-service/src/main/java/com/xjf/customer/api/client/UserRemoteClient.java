package com.xjf.customer.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xjf
 * @date 2020/2/7 14:42
 */
@FeignClient(value = "zuul-extend-user-service")
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
