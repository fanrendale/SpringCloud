package com.xjf.demo.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 自定义负载均衡策略
 *
 * @author xjf
 * @date 2020/1/25 21:07
 */
public class MyRule implements IRule {
    private ILoadBalancer lb;

    @Override
    public Server choose(Object key) {
        List<Server> servers = lb.getAllServers();
        for (Server server : servers) {
            System.out.println(server.getHostPort());
        }

        // 总是调用第一个服务
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;

    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
