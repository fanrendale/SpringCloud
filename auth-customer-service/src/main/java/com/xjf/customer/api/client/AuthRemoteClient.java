package com.xjf.customer.api.client;

import com.xjf.auth.common.ResponseData;
import com.xjf.customer.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 调用 auth-service 服务，登录认证获取 token
 *
 * @author xjf
 * @date 2020/2/5 15:53
 */
@FeignClient(value = "auth-service")
public interface AuthRemoteClient {

    /**
     * 调用认证，获取 token
     *
     * @param user
     * @return
     */
    @PostMapping("/token")
    ResponseData auth(@RequestBody User user);
}
