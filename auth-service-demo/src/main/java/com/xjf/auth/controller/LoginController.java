package com.xjf.auth.controller;

import com.xjf.auth.common.ResponseData;
import com.xjf.auth.entity.User;
import com.xjf.auth.util.JWTUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xjf
 * @date 2020/2/5 12:36
 */
@RestController
public class LoginController {

    /**
     * Post 方式登录认证
     * @param user
     * @return
     */
    @PostMapping("/token")
    public ResponseData auth(@RequestBody User user){
        if (user == null){
            return ResponseData.failByParam("参数不能为空");
        }

        if (!"xjf".equals(user.getUsername()) || !"123456".equals(user.getPassword())){
            return ResponseData.failByParam("用户名或密码错误");
        }

        user.setId(1L);

        // 生成 token
        JWTUtils instance = JWTUtils.getInstance();
        return ResponseData.ok(instance.getToken(user.getId().toString()));
    }

    /**
     * Get 方式登录认证
     * @param user
     * @return
     */
    @GetMapping("/token")
    public ResponseData auth2(User user){
        if (user == null){
            return ResponseData.failByParam("参数不能为空");
        }

        if (!"xjf".equals(user.getUsername()) || !"123456".equals(user.getPassword())){
            return ResponseData.failByParam("用户名或密码错误");
        }

        user.setId(1L);

        // 生成 token
        JWTUtils instance = JWTUtils.getInstance();
        return ResponseData.ok(instance.getToken(user.getId().toString()));
    }
}
