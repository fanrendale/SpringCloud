package com.xjf.customer.filter;

import com.xjf.customer.support.RibbonFilterContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器，获取请求头的参数。在请求处理之前
 *
 * @author xjf
 * @date 2020/2/7 15:06
 */
public class HttpHeaderParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        //获取网关传递过来的参数
        String username = httpServletRequest.getHeader("username");
        // 本地保存。通过 InheritableThreadLocal 在线程之间进行数据传递（俺也不懂）。实际就是一个 map 存的
        RibbonFilterContextHolder.getCurrentContext().add("username", username);

        chain.doFilter(httpServletRequest,response);
    }

    @Override
    public void destroy() {

    }
}
