package com.xjf.demo.controller;

import com.xjf.demo.api.client.TransactionMqServiceRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author xjf
 * @date 2020/2/14 22:10
 */
@RestController
public class TestController {

    @Autowired
    private TransactionMqServiceRemoteClient transactionMqServiceRemoteClient;

    @GetMapping("/test")
    public String test(int limit){
        Optional.ofNullable(transactionMqServiceRemoteClient.findByWatingMessage(limit))
                .orElse(new ArrayList<>())
                .forEach(System.err::println);

        return "success";
    }
}
