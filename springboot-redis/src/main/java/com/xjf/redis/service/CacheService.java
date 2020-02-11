package com.xjf.redis.service;

import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存操作的封装
 *
 * @author xjf
 * @date 2020/2/11 10:13
 */
public interface CacheService {

    /**
     * 设置缓存
     *
     * @param key 缓存的 key
     * @param value 缓存的值
     * @param timeout 缓存过期时间
     * @param timeUnit 缓存过期时间单位
     */
    void setCache(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    String getCache(String key);

    /**
     * 获取缓存。如果缓存没值则需要设置缓存，此时过期时间和单位是默认值：1 小时
     *
     * @param key
     * @param closure
     * @return
     */
    <V, K> String getCache(K key, Closure<V, K> closure);

    /**
     * 获取缓存。如果缓存没值则需要设置缓存，此时过期时间和单位有用
     *
     * @param key
     * @param closure
     * @param timeout
     * @return
     */
    <V, K>String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param key
     */
    void deleteCache(String key);
}
