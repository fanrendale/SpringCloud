package com.xjf.demo;

/**
 * feign原生调用接口，脱离SpringCloud
 */
public class Main {

    public static void main(String[] args) {
        HelloRemote helloRemote = RestApiCallUtils.getRestClient(HelloRemote.class, "http://localhost:8081");
        System.out.println("调用结果：" + helloRemote.hello());
    }

}
