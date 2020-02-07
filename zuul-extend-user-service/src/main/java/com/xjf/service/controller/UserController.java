package com.xjf.service.controller;

import com.xjf.auth.common.ResponseData;
import com.xjf.auth.util.JWTUtils;
import com.xjf.service.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author xjf
 * @date 2020/2/7 13:15
 */
@RestController
public class UserController {

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseData login(@RequestBody User user){
        if (Objects.isNull(user) || StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return ResponseData.failByParam("username 和 password 不能为空");
        }

        JWTUtils instance = JWTUtils.getInstance();
        String token = "";
        // 模拟判断，实际应去数据库对比
        if ("xjf".equals(user.getUsername()) && "123456".equals(user.getPassword())){
            token = instance.getToken(user.getUsername());
        }else {
            return ResponseData.fail("账号或密码错误");
        }

        return ResponseData.ok(token);
    }
}
