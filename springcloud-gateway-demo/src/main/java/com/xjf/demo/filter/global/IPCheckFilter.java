package com.xjf.demo.filter.global;

import com.xjf.demo.base.ResponseData;
import com.xjf.demo.util.JsonUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 自定义 IP 过滤器，在白名单中的 IP 才能通过
 *
 * @author xjf
 * @date 2020/1/31 16:00
 */
@Component
public class IPCheckFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.err.println("进入了自定义的IP过滤器");

        HttpHeaders headers = exchange.getRequest().getHeaders();

        // 此处写得非常绝对，只作演示用， 实际中需要采取配置的方式
        if (getIp(headers).equals("127.0.0.0")){
            ServerHttpResponse response = exchange.getResponse();
            ResponseData data = new ResponseData();
            data.setCode(401);
            data.setMessage("非法请求");
            byte[] datas = JsonUtils.toJson(data).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(datas);

            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    // 这里从请求头中获取用户的实际 IP ，根据 Nginx 转发的请求头获取
    private String getIp(HttpHeaders headers){
        // 模拟写死
        return "127.0.0.1";
    }
}
