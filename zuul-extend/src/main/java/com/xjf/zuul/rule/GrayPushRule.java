package com.xjf.zuul.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.xjf.auth.support.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 灰度发布的转发规则，基于 RoundRobinRule 规则改造
 *
 * @author xjf
 * @date 2020/2/9 10:57
 */
@Slf4j
public class GrayPushRule extends AbstractLoadBalancerRule {

    /**
     * 原子引用类型的整型，保存遍历服务的模数（即使用哪一个服务器）
     */
    private AtomicInteger nextServerCyclicCounter;
    private static final boolean AVAILABLE_ONLY_SERVERS = true;
    private static final boolean ALL_SERVERS = false;

    public GrayPushRule() {
        this.nextServerCyclicCounter = new AtomicInteger();
    }

    public GrayPushRule(ILoadBalancer lb){
        this();
        this.setLoadBalancer(lb);
    }

    /**
     * 自定义负载均衡策略
     * @param lb
     * @param key
     * @return
     */
    public Server choose(ILoadBalancer lb, Object key){
        if (lb == null){
            log.warn("no load balance");
            return null;
        } else {
            String currentUserId = RibbonFilterContextHolder.getCurrentContext().get("username");
            String userIds = RibbonFilterContextHolder.getCurrentContext().get("usernames");
            String servers = RibbonFilterContextHolder.getCurrentContext().get("servers");
            System.err.println("灰度的用户：" + userIds);
            System.err.println("灰度过滤器当前线程和灰度服务：" + Thread.currentThread().getName() + ":" + servers);

            // 如果配置了灰度用户和灰度服务信息，并且灰度服务在所有服务中（还未从所有服务移除灰度服务），则将对应的服务返回。
            if (StringUtils.isNotBlank(servers)){
                List<String> grayServerList = Arrays.asList(servers.split(","));
                if (StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(currentUserId)){
                    List<String> userIdList = Arrays.asList(userIds.split(","));
                    if (userIdList.contains(currentUserId)){
                        List<Server> allServers = lb.getAllServers();
                        for (Server server : allServers) {
                            // 判断是否在灰度服务名单中，并且该服务要可用
                            if (grayServerList.contains(server.getHostPort())
                                    && server.isAlive() && server.isReadyToServe()){
                                System.err.println("使用了灰度服务");
                                return server;
                            }
                        }
                    }
                }
            }

            Server server = null;
            int count = 0;

            // 不在灰度服务（灰度服务关闭等情况）中，获取当前服务的其他地址
            while (true){
                if (server == null && count++ < 10){
                    // 所有能用的服务
                    List<Server> reachableServers = lb.getReachableServers();
                    // 所有服务，包含了不能用的服务
                    List<Server> allServers = lb.getAllServers();

                    // 移除已经设置为灰度发布的服务信息
                    if (StringUtils.isNotBlank(servers)){
                        reachableServers = removeServer(reachableServers, servers);
                        allServers = removeServer(allServers,servers);
                    }

                    int upCount = reachableServers.size();
                    int serverCount = allServers.size();
                    if (upCount != 0 && serverCount != 0){
                        // 得到所有服务中的一个服务
                        int nextServerIndex = this.incrementAndGetModulo(serverCount);
                        server = allServers.get(nextServerIndex);

                        // 对得到的服务进行判断，如果服务正常则返回服务；如果不正常则遍历获取下一个服务判断
                        if (server == null){
                            Thread.yield();
                        }else {
                            if (server.isAlive() && server.isReadyToServe()){
                                System.err.println("未使用灰度服务");
                                return server;
                            }

                            server = null;
                        }
                        continue;
                    }

                    log.warn("在负载均衡器中没有能得到的服务：" + lb);
                    return  null;
                }

                if (count >= 10){
                    log.warn("在负载均衡器中重试了 10 次依然没有能用的服务");
                }

                return server;
            }

        }

    }

    /**
     * 从所有服务中移除灰度的服务
     *
     * @param allServers
     * @param servers
     * @return
     */
    private List<Server> removeServer(List<Server> allServers, String servers){
        // 新的 list ，不含灰度服务
        List<Server> newServerList = new ArrayList<>();
        List<String> grayServerList = Arrays.asList(servers.split(","));

        newServerList = allServers.stream().filter(server -> !grayServerList.contains(server.getHostPort())).collect(Collectors.toList());

        return newServerList;
    }

    /**
     * 根据所有服务的数量取模，实现效果为遍历所有服务的索引
     *
     * @param modulo
     * @return
     */
    private int incrementAndGetModulo(int modulo){
        int current;
        int next;
        do {
            current = this.nextServerCyclicCounter.get();
            next = (current + 1) % modulo;
            // 使用了 CAS
        }while (!this.nextServerCyclicCounter.compareAndSet(current, next));

        return next;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }
}
