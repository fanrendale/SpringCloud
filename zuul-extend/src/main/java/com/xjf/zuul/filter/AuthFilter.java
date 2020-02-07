package com.xjf.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.auth.common.ResponseCode;
import com.xjf.auth.common.ResponseData;
import com.xjf.auth.util.JWTUtils;
import com.xjf.auth.util.JsonUtils;
import com.xjf.zuul.config.BasicConf;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 认证过滤器，白名单不过滤
 *
 * @author xjf
 * @date 2020/2/7 11:51
 */
public class AuthFilter extends ZuulFilter {

    @Autowired
    private BasicConf basicConf;


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
        RequestContext ctx = RequestContext.getCurrentContext();
        String apis = basicConf.getApiWhiteStr();

        // 白名单，放过
        List<String> apiList = Arrays.asList(apis.split(","));
        String uri = ctx.getRequest().getRequestURI();
        System.out.println("当前请求uri: " + uri);
        if (apiList.contains(uri)){
            System.out.println("白名单中，放过");
            return null;
        }

        // 带参数的白名单，使用正则匹配，放过
        for (String wapi : apiList) {
            if (wapi.contains("{") && wapi.contains("}")){
                if (wapi.split("/").length == uri.split("/").length){
                    // 将参数去掉，再使用白名单去匹配当前请求的 uri,路径一致才会匹配成功
                    String reg = wapi.replaceAll("\\{.*}", ".*{1,}");
                    Pattern pattern = Pattern.compile(reg);
                    Matcher matcher = pattern.matcher(uri);
                    if (matcher.find()){
                        System.out.println("白名单中，放过");
                        return null;
                    }
                }
            }
        }

        System.err.println("====================当前uri不在白名单中：" + uri + ", 将进行拦截验证====================");

        // 验证 token
        JWTUtils jwtUtils = JWTUtils.getInstance();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");

        // 1. 没有 token 的情况
        if (StringUtils.isBlank(token)){
            ctx.setSendZuulResponse(false);
            // 参数传递，下面的过滤器可以根据该参数判断是否继续执行
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail(" 非法请求【缺少 Authorization 信息】", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }

        JWTUtils.JWTResult jwtResult = jwtUtils.checkToken(token);
        if (!jwtResult.isStatus()){
            // 2. token 验证不合格的情况
            ctx.setSendZuulResponse(false);
            // 参数传递，下面的过滤器可以根据该参数判断是否继续执行
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail(jwtResult.getMsg(), ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }

        // token 验证成功，将用户名放入请求头中
        ctx.addZuulRequestHeader("username", jwtResult.getUid());

        return null;
    }
}
