package com.xjf.cache;

import lombok.Data;

/**
 * @author xjf
 * @date 2020/2/9 20:48
 */
@Data
public class Person {

    private String id;

    private String username;

    private String password;

    public Person(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
