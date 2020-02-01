package com.xjf.demo.config;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xjf
 * @date 2020/2/1 21:21
 */
@CxytianDiConf(system = "spring-boot", env = true, prefix = "spring.data.mongodb")
public class MongoConf implements SmconfUpdateCallBack {
    @ConfField("数据库名称")
    private String database = "test";

    @ConfField("数据库地址")
    private String host = "localhost";

    @ConfField("数据库其他参数")
    private Map<String, Object> map = new HashMap<String, Object>(){{
        put("maxSize", 100);
    }};

    @ConfField("数据库端口")
    private int port = 27017;



    /**
     * 值动态更新时的回调
     * @param conf
     */
    @Override
    public void reload(Conf conf) {
        System.out.println(conf.getKey() + "更新了，新值为：" + conf.getValue());
        System.setProperty(conf.getKey(),(String)conf.getValue());
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    public Map<String, Object> getMap() {
        return map;
    }
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
