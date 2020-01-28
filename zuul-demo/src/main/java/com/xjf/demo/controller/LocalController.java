package com.xjf.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/1/28 21:47
 */
@RestController
public class LocalController {

    /**
     * 本地方法测试
     * @param id
     * @return
     */
    @GetMapping("/local/{id}")
    public String local(@PathVariable String id){
        return id;
    }
}
