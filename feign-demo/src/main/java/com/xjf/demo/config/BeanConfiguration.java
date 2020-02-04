package com.xjf.demo.config;

import brave.http.HttpAdapter;
import brave.http.HttpSampler;
import org.springframework.cloud.sleuth.instrument.web.ServerSampler;
import org.springframework.cloud.sleuth.instrument.web.SkipPatternProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

/**
 * @author xjf
 * @date 2020/2/4 17:49
 */
@Configuration
public class BeanConfiguration {

    @Bean(name = ServerSampler.NAME)
    public HttpSampler myHttpSampler(SkipPatternProvider provider){
        Pattern pattern = provider.skipPattern();
        return new HttpSampler() {
            @Override
            public <Req> Boolean trySample(HttpAdapter<Req, ?> httpAdapter, Req req) {
                String url = httpAdapter.path(req);
                boolean shouldSkip = pattern.matcher(url).matches();
                if (shouldSkip){
                    return false;
                }

                return null;
            }
        };
    }
}
