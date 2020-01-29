package com.xjf.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 请求信息过滤器，获取请求的信息
 *
 * @author xjf
 * @date 2020/1/29 21:16
 */
public class RequestInfoFilter extends ZuulFilter {

    /**
     * pre 在请求被路由之前调用
     * post 类型在 route 和 error 过滤器之后调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
        System.err.println("REQUEST::" + req.getScheme() + " " + req.getRemoteAddr() + ":" + req.getRemotePort());

        StringBuilder params = new StringBuilder("?");
        //获取 URL 参数
        Enumeration<String> names = req.getParameterNames();
        if ("GET".equals(req.getMethod())){
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                params.append(name);
                params.append("=");
                params.append(req.getParameter(name));
                params.append("&");
            }
        }
        // 去掉最后一个参数的 "&" 符号
        if (params.length() > 0){
            params.delete(params.length()-1, params.length());
        }

        System.err.println("REQUEST::>" + req.getMethod() + " " + req.getRequestURI() + params + " " + req.getProtocol());

        // 请求头信息
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()){
            String name = headers.nextElement();
            String value = req.getHeader(name);
            System.err.println("REQUEST::>" + name + ":" + value);
        }

        final RequestContext ctx = RequestContext.getCurrentContext();
        // 获取请求体参数
        if (!ctx.isChunkedRequestBody()){
            ServletInputStream inp = null;
            try {
                inp = ctx.getRequest().getInputStream();
                String body = null;
                if (inp != null){
                    body = IOUtils.toString(inp);
                    System.err.println("REQUEST::>" + body);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
