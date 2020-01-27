package com.xjf.demo;

import feign.RequestLine;

/**
 * 接口定义类
 *
 * @author xjf
 * @date 2020/1/27 18:04
 */
public interface HelloRemote {

    /**
     * Feign原生注解定义客户端
     * @return
     */
    @RequestLine("GET /user/hello")
    String hello();
}
