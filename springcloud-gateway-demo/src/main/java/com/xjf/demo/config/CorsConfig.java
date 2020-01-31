package com.xjf.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 使用代码配置跨域
 *
 * @author xjf
 * @date 2020/1/31 21:32
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsFilter(){
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)){
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders responseHeaders = response.getHeaders();

                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
                responseHeaders.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());

                if (requestMethod != null){
                    responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }

                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

                if (request.getMethod() == HttpMethod.OPTIONS){
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            return chain.filter(ctx);
        };
    }
}
