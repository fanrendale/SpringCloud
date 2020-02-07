package com.xjf.zuul.controller;

import com.xjf.zuul.apollo.BasicConf;
import com.xjf.zuul.apollo.LimitConf;
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

    @Autowired
    private LimitConf limitConf;

    @GetMapping("/apiWhiteStr")
    public String apiWhiteStr(){
        return basicConf.getApiWhiteStr();
    }

    @GetMapping("/rate")
    public double limitRate(){
        return limitConf.getLimitRate();
    }
}
