package com.xjf.service.apilimit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 具体的 API 并发控制切面。对使用了注解的方法，使用特定的限流值；其他接口用默认限流值
 * Order 注解是定义 Bean 的执行顺序
 *
 * @author xjf
 * @date 2020/2/8 12:06
 */
@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiLimitAspect {

    /**
     * 信号量 map ， 创建信号量是会指定同时能有多少个线程获得资源。限流中的令牌桶算法
     */
    public static Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    /**
     * around 为环绕通知，在目标方法执行前后都会处理。
     * 切点是 controller 中的所有方法，不管有没有被 ApiRateLimit 注解
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.xjf.service.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint){
        Object result = null;
        Semaphore semaphore = null;

        // 获取目标执行类
        Class<?> clazz = joinPoint.getTarget().getClass();
        //  joinPoint.getSignature().getName 获取当前方法的方法名，是所有的方法
        String key = getRateLimitKey(clazz, joinPoint.getSignature().getName());
        if (key != null){
            // 如果有注解配置的变量，则获取对应的信号量
            semaphore = semaphoreMap.get(key);
        } else {
            // 没有注解，则使用默认的信号量
            semaphore = semaphoreMap.get("defaultLimit");
        }

        try {
            // 获取一个许可，如果没有可获得的许可则阻塞
            System.out.println("当前线程：" + Thread.currentThread().getName() + "  获取许可");
            semaphore.acquire();
            // 指定目标方法
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            // 释放许可
            semaphore.release();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "  释放许可");

        }

        return result;
    }

    /**
     * 对指定类中的有 ApiRateLimit 注解的方法，获取配置的 confKey 的值
     *
     * @param clazz
     * @param methodName
     * @return
     */
    private String getRateLimitKey(Class<?> clazz, String methodName){
        // 获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)){
                if (method.isAnnotationPresent(ApiRateLimit.class)){
                    String key = method.getAnnotation(ApiRateLimit.class).confKey();

                    return key;
                }
            }
        }

        return null;
    }
}
