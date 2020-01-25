package com.xjf.demo.config;

import com.xjf.demo.annotation.MyLoadBalanced;
import com.xjf.demo.interceptor.MyLoadBalancerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 配置类，将自定义拦截器注入到RestTemplate中
 *
 * @author xjf
 * @date 2020/1/24 14:16
 */
@Configuration
public class MyLoadBalancerAutoConfiguration {

    @MyLoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    public MyLoadBalancerInterceptor myLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient,
                                                               LoadBalancerRequestFactory requestFactory){
        return new MyLoadBalancerInterceptor(loadBalancerClient,requestFactory);
    }

    /**
     * SmartInitializingSingleton:
     * 实现该接口后，当所有单例 bean 都初始化完成以后， 容器会回调该接口的方法 afterSingletonsInstantiated。
     * 主要应用场合就是在所有单例 bean 创建完成之后，可以在该回调中做一些事情。
     *
     * @return
     */
    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer(LoadBalancerClient loadBalancerClient,
                                                                            LoadBalancerRequestFactory requestFactory){
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate restTemplate : MyLoadBalancerAutoConfiguration.this.restTemplates) {
                    List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
                    //将自定义拦截器注入
                    list.add(myLoadBalancerInterceptor(loadBalancerClient,requestFactory));
                    restTemplate.setInterceptors(list);
                }
            }
        };
    }
}
