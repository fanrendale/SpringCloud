package com.xjf.demo.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用user服务的接口声明
 *
 * @author xjf
 * @date 2020/1/26 11:48
 */
@FeignClient(value = "eureka-client-user-service")
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
