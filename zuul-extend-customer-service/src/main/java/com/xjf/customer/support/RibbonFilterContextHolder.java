package com.xjf.customer.support;

import com.alibaba.ttl.TransmittableThreadLocal;

public class RibbonFilterContextHolder {
    private static final TransmittableThreadLocal<RibbonFilterContext> contextHolder = new TransmittableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };


    public static RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }


    public static void clearCurrentContext() {
        contextHolder.remove();
    }
}
