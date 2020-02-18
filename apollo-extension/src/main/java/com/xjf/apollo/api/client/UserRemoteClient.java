package com.xjf.apollo.api.client;

import com.xjf.apollo.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xjf
 * @date 2020/2/18 10:51
 */
@FeignClient(value = "eureka-client-user-service", configuration = FeignConfig.class)
public interface UserRemoteClient {

    @GetMapping("/user/hello")
    String hello();
}
