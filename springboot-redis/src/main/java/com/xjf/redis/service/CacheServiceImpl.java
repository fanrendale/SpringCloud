package com.xjf.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xjf
 * @date 2020/2/11 10:22
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认过期时间
     */
    private static final long DEFAULT_TIMEOUT = 30L;

    /**
     * 默认过期时间单位
     */
    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

    @Override
    public void setCache(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key,value,timeout, timeUnit);
    }

    @Override
    public String getCache(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCache(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure) {
        return doGetCache(key, closure, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit) {
        return doGetCache(key, closure, timeout, timeUnit);
    }

   private <K, V> String doGetCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit){

        // 此处的异常捕获可以暂时处理 Redis 异常时，从数据库去取数据
       String value = null;
       try {
           value = getCache(key.toString());
       } catch (Exception e) {
           System.err.println("Redis 异常：" + e.getMessage());
       }

       if (Objects.isNull(value)){
           Object execute = closure.execute(key);
           try {
               setCache(key.toString(), execute.toString(), timeout, timeUnit);
           } catch (Exception e) {
               System.err.println("Redis 异常：" + e.getMessage());
           }
           return execute.toString();
       }

       return value;
   }
}
