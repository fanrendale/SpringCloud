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
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        // 模拟异常报错，测试异常过滤器
//        System.out.println(2/0);

        RequestContext ctx = RequestContext.getCurrentContext();
        //从请求中获取请求的IP地址
//        String ip = IpUtils.getIpAddr(ctx.getRequest());
        String ip = null;
        try {
            //从请求中获取请求的IP地址
            ip = NetworkUtil.getIpAddress(ctx.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("请求的 IP ： " + ip);

        // 测试 RequestContext 传参，可以在多个过滤器中传值
        ctx.set("name", "xjf");
        Object name = ctx.get("name");
        System.out.println(" RequestContext 中取值: name=" + name);

        // 在黑名单中禁用
        if (StringUtils.isNotBlank(ip) && blackIpList.contains(ip)){
            // 告诉 Zuul 不需要将当前请求转发到后端的服务
            ctx.setSendZuulResponse(false);
            // 拦截本地转发请求（即设置了 forward:/local 的路由），上一行设置不能实现本地请求的拦截。
            ctx.set("sendForwardFilter.ran", true);

            ResponseData data = ResponseData.fail(" 非法请求 ", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");

            return null;
        }

        return null;
    }
}
