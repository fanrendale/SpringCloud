package com.xjf.zuul.apollo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Apollo 配置类（获取配置属性）
 *
 * @author xjf
 * @date 2020/2/7 11:30
 */
@Data
@Configuration
public class BasicConf {

    /**
     * API 接口白名单，多个用逗号分开
     */
    @Value("${apiWhiteStr:/zuul-extend-user-service/user/login}")
    private String apiWhiteStr;

    /**
     * 使用 Redis 做集群限流，其一秒的限流值
     */
    @Value("${clusterLimitRate:10}")
    private int clusterLimitRate;

    /**
     * 降级服务的 serviceId 名单，多个用逗号隔开
     */
    @Value("${downGradeServiceStr:default}")
    private String downGradeServiceStr;

    /**
     * 灰度发布的服务器地址，多个用英文逗号隔开。如：localhost:8088
     */
    @Value("${grayPushServers:default}")
    private String grayPushServers;

    /**
     * 灰度发布的用户 id 集合，多个用逗号隔开
     */
    @Value("${grayPushUsernames:default}")
    private String grayPushUsernames;
}
