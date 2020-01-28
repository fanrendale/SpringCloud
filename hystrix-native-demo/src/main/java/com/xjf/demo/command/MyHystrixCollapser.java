package com.xjf.demo.command;

import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Hystrix合并请求：将多个请求合并一起，一次性执行，节约网络开销
 *
 * @author xjf
 * @date 2020/1/28 11:43
 */
public class MyHystrixCollapser extends HystrixCollapser<List<String>, String, String> {
    private final String name;

    public MyHystrixCollapser(String name) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("MyCollapserCommand"))
                    //设置合并器的时间，即多长时间合并一次请求
                    .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.name = name;
    }

    /**
     * 获取请求的参数
     * @return
     */
    @Override
    public String getRequestArgument() {
        return name;
    }

    /**
     * 合并请求
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> collection) {
        return new BatchCommand(collection);
    }

    /**
     * 将返回结果一一对应填充到对应的的返回值中
     *
     * @param batchResponse
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, String>> collection) {
        int count = 0;
        for (CollapsedRequest<String, String> request : collection) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {
        private final Collection<CollapsedRequest<String, String>> requests;

        private BatchCommand(Collection<CollapsedRequest<String, String>> requests){
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }


        @Override
        protected List<String> run() throws Exception {
            System.out.println(" 真正执行请求......");
            ArrayList<String> response = new ArrayList<>();
            for (CollapsedRequest<String, String> request : requests) {
                response.add(" 返回结果：" + request.getArgument());
            }

            return response;
        }
    }
}
