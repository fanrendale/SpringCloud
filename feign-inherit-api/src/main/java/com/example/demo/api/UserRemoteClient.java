package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xjf
 * @date 2020/1/26 21:29
 */
@FeignClient("feign-inherit-provide")
public interface UserRemoteClient {

    @GetMapping("/user/name")
    String getName();
}
