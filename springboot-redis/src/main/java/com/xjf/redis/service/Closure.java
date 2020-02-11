package com.xjf.redis.service;

/**
 * 缓存的回调方法接口
 *
 * @author xjf
 * @date 2020/2/11 10:19
 */
public interface Closure<A, B> {
    /**
     * 获取缓存时，如果没取到值，执行的数据库操作可以用该接口的实现。
     *
     * @param input
     * @return
     */
    A execute(B input);
}
