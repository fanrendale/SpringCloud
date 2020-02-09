package com.xjf.service.controller;

import com.xjf.service.apilimit.ApiRateLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 此控制器的方法主要用来测试 Zuul 网关中的白名单过滤拦截
 *
 * @author xjf
 * @date 2020/2/7 12:18
 */
@RestController
public class TestController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 使用 ApiRateLimit 注解来标识接口限流，参数为 Apollo 中的限流值
     * @return
     */
    @GetMapping("/user/hello")
    @ApiRateLimit(confKey = "user-hello-limit")
    public String hello(){

        System.err.println("当前登录用户：" + request.getHeader("username"));

        try {
            // 暂停 0.5s
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("调用了 /user/hello 方法 ");
        return "我是 zuul-extend-user-service 服务的 hello 方法";
    }

    @GetMapping("/user/logout/{id}")
    public String logout(@PathVariable("id") String id){
        return "用户：" + id + " 注销成功";
    }

    @ApiRateLimit(confKey = "user-aaa-limit")
    @GetMapping("/user/aaa")
    public String login(HttpServletRequest request){
        try {
            // 暂停 0.5s
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println(" 当前用户名：" + request.getHeader("username"));
        return "我是 zuul-extend-user-service 服务的 aaa方法";
    }
}
