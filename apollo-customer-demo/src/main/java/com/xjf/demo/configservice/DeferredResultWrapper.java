package com.xjf.demo.configservice;

import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * 响应结果类
 *
 * @author xjf
 * @date 2020/2/3 11:54
 */
public class DeferredResultWrapper {

    /**
     * 60秒
      */
    private static final long TIMEOUT = 60 * 1000;

    private static final ResponseEntity<List<ApolloConfigNotification>> NOT_MODIFIED_RESPONSE_LSIT =
            new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private DeferredResult<ResponseEntity<List<ApolloConfigNotification>>> result;

    public DeferredResultWrapper() {
        result = new DeferredResult<>(TIMEOUT, NOT_MODIFIED_RESPONSE_LSIT);
    }

    public void onTimeout(Runnable timeoutCallback){
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback){
        result.onCompletion(completionCallback);
    }

    public void setResult(ApolloConfigNotification notification){
        setResult(Lists.newArrayList(notification));
    }

    public void setResult(List<ApolloConfigNotification> notifications){
        result.setResult(new ResponseEntity<>(notifications, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<List<ApolloConfigNotification>>> getResult(){
        return result;
    }
}
