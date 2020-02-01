package com.xjf.demo.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器，将异常信息用 Json 返回。不能用 @ControllerAdvice
 *
 * @author xjf
 * @date 2020/2/1 10:03
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);

        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException){
            code = 404;
        }

        System.err.println("获取异常属性");

        return response(code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为 JSON 处理的方法
     *
     * @param errorAttributes
     * @return
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据 code 获取对应的 HttpStatus
     *
     * @param errorAttributes
     * @return
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        // 在 response 方法中定义的状态码字段是 code，所以此处用 code
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 构建异常信息
     *
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex){
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (ex != null){
            message.append(": ");
            message.append(ex.getMessage());
        }

        return message.toString();
    }

    /**
     * 构建返回的 JSON 数据格式
     *
     * @param status 状态码
     * @param errorMessage 异常信息
     * @return
     */
    public static Map<String, Object> response(int status, String errorMessage){
        Map<String, Object> map = new HashMap<>();
        // 此处的状态码用的是 code
        map.put("code", status);
        map.put("message", errorMessage);
        map.put("data", null);

        return map;
    }
}
