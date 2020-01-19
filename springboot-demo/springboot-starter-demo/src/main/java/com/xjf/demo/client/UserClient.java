package com.xjf.demo.client;

import com.xjf.demo.properties.UserProperties;

/**
 * 属性值使用的类
 *
 * @Author: xjf
 * @Date: 2020/1/19 11:00
 */
public class UserClient {

    private UserProperties userProperties;

    public UserClient(){

    }

    public UserClient(UserProperties userProperties){
        this.userProperties = userProperties;
    }

    public String getName(){
        return userProperties.getName();
    }
}
