package com.xjf.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 请求的响应内容过滤器，获取响应内容
 *
 * @author xjf
 * @date 2020/1/30 10:25
 */
public class ResponseInfoFilter extends ZuulFilter {

    /**
     * pre 在请求被路由之前调用
     * post 类型在 route 和 error 过滤器之后调用
     * @return
     */
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 方法一
        /*try {
            Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");

            if (Objects.nonNull(zuulResponse)){
                RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
                String body = IOUtils.toString(resp.getBody());
                System.err.println("RESPONSE::>" + body);
                resp.close();
                RequestContext.getCurrentContext().setResponseBody(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 方法二
        InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();

        try {
            if (Objects.nonNull(stream)){
                String body = IOUtils.toString(stream);
                System.err.println("RESPONSE::>" + body);
                //设置返回数据
                RequestContext.getCurrentContext().setResponseBody(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
