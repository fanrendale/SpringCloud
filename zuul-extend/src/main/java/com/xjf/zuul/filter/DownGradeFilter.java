package com.xjf.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.auth.common.ResponseCode;
import com.xjf.auth.common.ResponseData;
import com.xjf.auth.util.JsonUtils;
import com.xjf.zuul.apollo.BasicConf;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 降级服务的过滤器，
 *
 * @author xjf
 * @date 2020/2/8 22:20
 */
public class DownGradeFilter extends ZuulFilter {

    @Autowired
    private BasicConf basicConf;

    @Override
    public String filterType() {
        // 路由前
        return "route";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object isSuccess = ctx.get("isSuccess");
        return isSuccess == null ? true : Boolean.parseBoolean(isSuccess.toString());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取当前请求的服务 id
        Object serviceId = ctx.get("serviceId");
        System.err.println("当前服务 serviceId: " + serviceId);

        if (Objects.nonNull(serviceId) && Objects.nonNull(basicConf)) {
            // 降级名单
            List<String> downGradeList = Arrays.asList(basicConf.getDownGradeServiceStr().split(","));
            if (downGradeList.contains(serviceId.toString())){
                ctx.setSendZuulResponse(false);
                ctx.set("isSuccess", false);
                ResponseData data = ResponseData.fail("服务降级中", ResponseCode.DOWNGRADE.getCode());
                ctx.setResponseBody(JsonUtils.toJson(data));
                ctx.getResponse().setContentType("application/json; charset=utf-8");

                return null;
            }
        }

        return null;
    }
}
