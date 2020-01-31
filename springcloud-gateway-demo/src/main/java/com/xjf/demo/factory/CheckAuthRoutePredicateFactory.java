package com.xjf.demo.factory;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author xjf
 * @date 2020/1/30 22:20
 */
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {
    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    /**
     *  此方法不能少
     *
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("name");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            System.err.println(" 进入了 CheckAuthRoutePredicateFactory\t" + config.getName());

            /*if ("xjf".equals(config.getName())){
                return true;
            }

            return false;*/

            String param = exchange.getRequest().getQueryParams().getFirst("name");
            System.err.println("param:" + param);
            System.err.println("name:" + config.getName());
            return config.getName().equals(param);
        };
    }

    public static class Config {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
