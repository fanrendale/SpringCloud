package com.xjf.service.entity;

import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/7 13:15
 */
@Data
public class User {

    private String id;
    private String username;
    private String password;

    public User() {
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
