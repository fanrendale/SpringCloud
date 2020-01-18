package com.example.demo.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义端点
 *
 * @Author: xjf
 * @Date: 2020/1/18 11:13
 */
@Component
@Endpoint(id = "user")
public class UserEndpoint {

    @ReadOperation
    public List<Map<String, Object>> health(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", "xjf");
        map.put("age", 25);
        list.add(map);

        return list;
    }
}
