package com.xjf.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * '@EnableCaching' 注解是spring framework中的注解驱动的缓存管理功能。
 * 自spring版本3.1起加入了该注解。如果你使用了这个注解，那么你就不需要在XML文件中配置cache manager了，
 * 等价于 <cache:annotation-driven/> 。能够在服务类方法上标注@Cacheable
 *
 * @author xjf
 * @date 2020/2/10 10:40
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 必须调用该方法，初始化 RedisTemplate
        redisTemplate.afterPropertiesSet();
        // 将缓存数据进行 Json 序列化，保存在 Redis 中为 Json 字符串
        setSerializer(redisTemplate);

        return redisTemplate;
    }

    /**
     * 设置 RedisTemplate 为 Json 序列化
     *
     * @param redisTemplate
     */
    private void setSerializer(RedisTemplate<String, String> redisTemplate) {
        // 使用 Jackson2JsonRedisSerializer 来序列化和反序列化 redis 的 value 值（默认使用 JDK 的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY 是都有包括 private 和 public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非 final 修饰的，final修饰的类，比如 String,Integer 等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 使用 StringRedisSerializer 来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 值采用 json 序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }

    /**
     * 缓存配置
     *
     * @param factory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        // 设置缓存有效期 30s
                        .entryTtl(Duration.ofSeconds(30))
                        // 不缓存空值
                        .disableCachingNullValues()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }

    /**
     * 设置缓存 key 的生成方式：类名 + 方法名 + 参数
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(":" + method.getName());
                for (Object param : params) {
                    sb.append(":" + param.toString());
                }

                return sb.toString();
            }
        };
    }
}
