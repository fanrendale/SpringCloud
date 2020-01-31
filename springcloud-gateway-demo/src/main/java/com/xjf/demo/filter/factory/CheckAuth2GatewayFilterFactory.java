package com.xjf.demo.filter.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器，两个参数
 *
 * @author xjf
 * @date 2020/1/31 15:05
 */
@Component
public class CheckAuth2GatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return ((exchange, chain) -> {
            System.err.println("进入了CheckAuth2GatewayFilterFactory\t\t" + config.getName() + ":" + config.getValue());

            ServerHttpRequest request = exchange.getRequest().mutate().build();
            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
