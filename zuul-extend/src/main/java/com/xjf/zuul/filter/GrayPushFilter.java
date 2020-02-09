package com.xjf.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.auth.support.RibbonFilterContextHolder;
import com.xjf.zuul.apollo.BasicConf;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 灰度发布的过滤器，将 Apollo 中配置的灰度发布的信息保存到本地变量中。供灰度发布时使用
 *
 * @author xjf
 * @date 2020/2/9 10:01
 */
public class GrayPushFilter extends ZuulFilter {

    @Autowired
    private BasicConf basicConf;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object isSuccess = ctx.get("isSuccess");
        return isSuccess == null || Boolean.parseBoolean(isSuccess.toString());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        // AuthFilter 验证成功之后会将用户名存入请求头
        String username = ctx.getZuulRequestHeaders().get("username");

        RibbonFilterContextHolder.clearCurrentContext();
        RibbonFilterContextHolder.getCurrentContext().add("username", username);
        // 灰度发后的服务信息
        RibbonFilterContextHolder.getCurrentContext().add("servers", basicConf.getGrayPushServers());
        // 灰度发布的用户信息
        RibbonFilterContextHolder.getCurrentContext().add("usernames", basicConf.getGrayPushUsernames());

        return null;
    }
}
