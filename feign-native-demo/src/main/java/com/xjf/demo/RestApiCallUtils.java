package com.xjf.demo;

import feign.Feign;

/**
 * Feign构建工具类
 * @author xjf
 * @date 2020/1/27 18:02
 */
public class RestApiCallUtils {

    /**
     * 获取 API 接口代理对象
     * @param apiType 接口类
     * @param url API 地址
     * @param <T>
     * @return
     */
    public static <T> T getRestClient(Class<T> apiType, String url){
        return Feign.builder().target(apiType, url);
    }
}
