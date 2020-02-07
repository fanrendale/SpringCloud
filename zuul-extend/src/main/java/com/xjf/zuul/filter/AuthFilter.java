package com.xjf.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xjf.zuul.config.BasicConf;
import org.springframework.beans.factory.annotation.Autowired;

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

        System.err.println("当前uri不在白名单中：" + uri);
        return null;
    }
}
