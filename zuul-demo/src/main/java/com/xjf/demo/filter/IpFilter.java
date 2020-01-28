package com.xjf.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.demo.base.ResponseCode;
import com.xjf.demo.base.ResponseData;
import com.xjf.demo.util.IpUtils;
import com.xjf.demo.util.JsonUtils;
import com.xjf.demo.util.NetworkUtil;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义过滤器： IP 过滤
 *
 * @author xjf
 * @date 2020/1/28 22:11
 */
public class IpFilter extends ZuulFilter {

    // IP 黑名单列表
    private List<String> blackIpList = Arrays.asList("192.168.43.8");

    /**
     * 过滤器类型： pre, route, post, error
     * @return
     */
    @Override
    public String filterType() {
        //路由前
        return "pre";
    }

    /**
     * 过滤器的执行顺序，数值越小，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行过滤器：true为执行，false为不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        //从请求中获取请求的IP地址
//        String ip = IpUtils.getIpAddr(ctx.getRequest());
        String ip = null;
        try {
            ip = NetworkUtil.getIpAddress(ctx.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("请求的 IP ： " + ip);

        // 在黑名单中禁用
        if (StringUtils.isNotBlank(ip) && blackIpList.contains(ip)){
            ctx.setSendZuulResponse(false);
            ResponseData data = ResponseData.fail(" 非法请求 ", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");

            return null;
        }

        return null;
    }
}
