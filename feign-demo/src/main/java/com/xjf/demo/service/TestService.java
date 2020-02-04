package com.xjf.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author xjf
 * @date 2020/2/4 17:10
 */
public interface TestService {

    /**
     * 异步方法
     * @param log
     */
    void saveLog(String log);

    /**
     *  本地方法手动埋点
     *
     * @param log
     */
    void saveLog2(String log);
}
