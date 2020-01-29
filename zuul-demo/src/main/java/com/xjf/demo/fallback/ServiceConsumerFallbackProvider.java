package com.xjf.demo.fallback;

import com.netflix.zuul.context.RequestContext;
import com.xjf.demo.base.ResponseCode;
import com.xjf.demo.base.ResponseData;
import com.xjf.demo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Zuul 默认整合了 Hystrix， 当后端服务异常时可以为 Zuul 添加回退功能。此处实现
 *
 * @author xjf
 * @date 2020/1/29 15:35
 */
@Component
public class ServiceConsumerFallbackProvider implements FallbackProvider {

    private Logger log = LoggerFactory.getLogger(ServiceConsumerFallbackProvider.class);

    /**
     * 此处 "*" 代表对所有服务进行回退。如果要指定服务，则填写对应的服务名称（必须注册在 Eureka 中的服务）
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 返回相应的状态码
             *
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            /**
             * 获取状态码的整数值
             *
             * @return
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * 返回响应状态码对应的文本
             *
             * @return
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 返回回退的内容
             *
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                if (Objects.nonNull(cause)){
                    log.error("", cause.getCause());
                }

                RequestContext ctx = RequestContext.getCurrentContext();
                ResponseData data = ResponseData.fail(" 服务器内部错误 ", ResponseCode.SERVER_ERROR_CODE.getCode());

                return new ByteArrayInputStream(JsonUtils.toJson(data).getBytes());
            }

            /**
             * 返回响应的请求头信息
             *
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);

                return headers;
            }
        };
    }

}
