package com.xjf.customer.api.interceptor;

import com.xjf.customer.support.RibbonFilterContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Map;

/**
 * Feign 调用前的拦截器。将网关传过来的参数保存到请求的请求头中，实现服务与服务之间的参数传递
 *
 * @author xjf
 * @date 2020/2/7 15:23
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取本地保存的所有属性
        Map<String, String> attributes = RibbonFilterContextHolder.getCurrentContext().getAttributes();

        // 此处是将本地的所有值都放进了请求头中
        for (String key : attributes.keySet()) {
            String value = attributes.get(key);
            requestTemplate.header(key,value);
        }
    }
}
