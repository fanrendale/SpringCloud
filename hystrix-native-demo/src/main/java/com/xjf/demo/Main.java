package com.xjf.demo;

import com.xjf.demo.command.MyHystrixCommand;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //同步执行
        String result = new MyHystrixCommand("xjf").execute();
        System.out.println(result);

        //异步调用
        /*Future<String> future = new MyHystrixCommand("xjf").queue();
        System.out.println(future.get());*/
    }

}
