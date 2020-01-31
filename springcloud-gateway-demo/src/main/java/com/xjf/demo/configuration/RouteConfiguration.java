package com.xjf.demo.configuration;

import com.xjf.demo.factory.CheckAuthRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置
 *
 * @author xjf
 * @date 2020/1/30 22:44
 */
@Configuration
public class RouteConfiguration {

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
