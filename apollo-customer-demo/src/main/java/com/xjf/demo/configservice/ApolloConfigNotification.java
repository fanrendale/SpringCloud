package com.xjf.demo.configservice;

import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/3 11:56
 */
@Data
public class ApolloConfigNotification {

    private String namespaceName;

    private long notificationId;

    public ApolloConfigNotification(String namespaceName, long notificationId) {
        this.namespaceName = namespaceName;
        this.notificationId = notificationId;
    }
}
