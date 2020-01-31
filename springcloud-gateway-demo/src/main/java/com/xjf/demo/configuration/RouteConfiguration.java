package com.xjf.demo.configuration;

import com.xjf.demo.predicate.factory.CheckAuthRoutePredicateFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 路由配置
 *
 * @author xjf
 * @date 2020/1/30 22:44
 */
@Configuration
public class RouteConfiguration {

    /**
     * 根据 IP 限流
     *
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver(){
        System.err.println("进入了 IP 限流的配置");
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 代码配置路由（与 yml 中配置效果一样）
     */
    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(predicateSpec ->
                predicateSpec.path("/order/**")
                    .and().asyncPredicate(checkAuthRoutePredicateFactory().applyAsync(config -> config.setName("xjf")))
                    .uri("http://www.jd.com").id("customer_route")
        ).build();
    }*/

    @Bean
    public CheckAuthRoutePredicateFactory checkAuthRoutePredicateFactory(){
        return new CheckAuthRoutePredicateFactory();
    }
}
