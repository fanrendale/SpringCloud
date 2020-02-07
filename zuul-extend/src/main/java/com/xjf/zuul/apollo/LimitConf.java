package com.xjf.zuul.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.google.common.util.concurrent.RateLimiter;
import com.xjf.zuul.filter.LimitFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 限流的 Apollo 参数
 *
 * @author xjf
 * @date 2020/2/7 17:57
 */
@Data
@Configuration
public class LimitConf {

    @Value("${limitRate:10}")
    private double limitRate;

    @ApolloConfig
    private Config config;

    /**
     * 值改变的回调方法，将本地令牌桶的限流值更新
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent){
        if (changeEvent.isChanged("limitRate")){
            // 该方法也能获取最新值
            System.out.println("更新值为：" + changeEvent.getChange("limitRate").getNewValue() );
            // 更新 RateLimiter , 使用 Config 获取修改后的值
            LimitFilter.rateLimiter = RateLimiter.create(config.getDoubleProperty("limitRate", 10.0));
        }
    }
}
