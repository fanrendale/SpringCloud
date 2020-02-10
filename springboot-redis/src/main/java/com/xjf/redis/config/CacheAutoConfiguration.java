package com.xjf.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存异常处理
 *
 * @author xjf
 * @date 2020/2/10 14:07
 */
@Slf4j
@Configuration
public class CacheAutoConfiguration extends CachingConfigurerSupport {

    /**
     * redis 数据操作异常处理，这里的处理：在日志中打印出错误信息，但是放行。保证 Redis 服务器出现连接等问题的时候不影响程序的正常运行，
     * 使得能够出问题时不用缓存，继续执行业务逻辑去查询 DB
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("redis 异常：key=[{}]", key, exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error("redis 异常：key=[{}]", key, exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("redis 异常：key=[{}]", key, exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("redis 异常：", exception);
            }
        };
    }
}
