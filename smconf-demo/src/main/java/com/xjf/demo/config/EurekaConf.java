package com.xjf.demo.config;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

/**
 * Smconf 的配置说明：
 * CxytianDiConf 注解：标识这是一个 Smconf 配置类
 * system ： 表示当前是哪个系统在使用
 * env : env=true 表示将当前类下的配置信息通过 System.setProperty 将值存储在系统变量中，
 *       在代码中可以通过 System.getProperty 来获取，在属性文件中可以通过 ${key} 来获取。
 * prefix : 给配置类中的字段加前缀
 *
 * @author xjf
 * @date 2020/2/1 16:23
 */
@CxytianDiConf(system = "smconf-demo", env = true, prefix = "eureka")
public class EurekaConf implements SmconfUpdateCallBack {

    @ConfField("Eureka 注册中心地址")
    private String defaultZone="http://xjf:123456@localhost:8761/eureka/";

    public String getDefaultZone() {
        return defaultZone;
    }

    public void setDefaultZone(String defaultZone) {
        this.defaultZone = defaultZone;
    }

    /**
     * 监听修改事件的回调方法
     * @param conf
     */
    @Override
    public void reload(Conf conf) {
        System.out.println(conf.getKey() + " 更新了");
    }
}
