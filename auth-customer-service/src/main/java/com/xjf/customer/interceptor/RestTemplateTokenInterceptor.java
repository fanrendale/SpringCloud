package com.xjf.customer.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xjf
 * @date 2020/2/5 18:23
 */
@Component
public class RestTemplateTokenInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.err.println(" 进入 RestTemplate 请求拦截器");
        HttpHeaders headers = request.getHeaders();

        // 请求头添加 token
        headers.add("Authorization", System.getProperty("customer.auth.token"));

        return execution.execute(request,body);
    }
}
