package com.xjf.demo;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import rx.Observable;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 使用ribbon做负载均衡
 *
 * 该例子演示了Ribbon如何去做负载操作，调用接口用的最底层的HttpURLConnection
 *
 * @author xjf
 * @date 2020/1/23 10:15
 */
public class Main {

    public static void main(String[] args) {
        // 服务列表
        List<Server> serverList = Arrays.asList(new Server("localhost", 8081)
                , new Server("localhost", 8083));

        // 构建负载实例
        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);

        // 调用5次来测试效果
        for (int i = 0; i < 5; i++) {
            String result = LoadBalancerCommand.<String>builder()
                    .withLoadBalancer(loadBalancer)
                    .build()
                    .submit(new ServerOperation<String>() {
                        @Override
                        public Observable<String> call(Server server) {
                            try {
                                String addr = "http://" + server.getHost() + ":" +
                                        server.getPort() + "/user/hello";
                                System.out.println("调用地址：" + addr);
                                URL url = new URL(addr);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.connect();
                                InputStream in = conn.getInputStream();
                                byte[] data = new byte[in.available()];
                                in.read(data);
                                return Observable.just(new String(data));
                            } catch (Exception e) {
                                e.printStackTrace();
                                return Observable.error(e);
                            }
                        }
                    })
                    .toBlocking().first();
            System.out.println("调用结果：" + result);
        }

    }
}
