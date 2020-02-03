package com.xjf.demo.configservice;

/**
 * @author xjf
 * @date 2020/2/3 11:42
 */
public interface ReleaseMessageListener {

    void handleMessage(ReleaseMessage message);
}
