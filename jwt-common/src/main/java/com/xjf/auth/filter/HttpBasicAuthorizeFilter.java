package com.xjf.auth.filter;

import com.xjf.auth.common.ResponseCode;
import com.xjf.auth.common.ResponseData;
import com.xjf.auth.util.JWTUtils;
import com.xjf.auth.util.JsonUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * API 调用权限控制. 过滤器
 *
 * @author xjf
 * @date 2020/2/5 14:42
 */
public class HttpBasicAuthorizeFilter implements Filter {

    JWTUtils jwtUtils = JWTUtils.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");

        String auth = httpRequest.getHeader("Authorization");
        // 验证 TOKEN
        if (!StringUtils.hasText(auth)){
            // 没有 token
            PrintWriter print = httpResponse.getWriter();
            print.write(
                    JsonUtils.toJson(
                            ResponseData.fail("非法请求【缺少 Authorization 信息】",
                                    ResponseCode.NO_AUTH_CODE.getCode())
                    )
            );

            return;
        }

        JWTUtils.JWTResult jwtResult = jwtUtils.checkToken(auth);
        if (!jwtResult.isStatus()){
            // token 验证错误
            PrintWriter print = httpResponse.getWriter();
            print.write(
                    JsonUtils.toJson(
                            ResponseData.fail(jwtResult.getMsg(),
                                    jwtResult.getCode())
                    )
            );

            return;
        }

        // 通行
        chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {

    }
}
