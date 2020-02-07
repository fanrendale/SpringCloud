package com.xjf.zuul.controller;

import com.xjf.zuul.config.BasicConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xjf
 * @date 2020/2/7 11:33
 */
@RestController
public class TestController {

    @Autowired
    private BasicConf basicConf;

    @GetMapping("/apiWhiteStr")
    public String apiWhiteStr(){
        return basicConf.getApiWhiteStr();
    }
}
