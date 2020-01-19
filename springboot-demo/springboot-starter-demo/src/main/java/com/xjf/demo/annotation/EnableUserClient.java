package com.xjf.demo.annotation;

import com.xjf.demo.config.UserAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义注解，使用该注解来自动配置UserClient
 *
 * @Author: xjf
 * @Date: 2020/1/19 11:20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({UserAutoConfigure.class})
public @interface EnableUserClient {
}
