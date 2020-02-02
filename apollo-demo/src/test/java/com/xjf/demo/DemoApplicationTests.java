package com.xjf.demo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * 使用 API 方式获取配置
     */
    @Test
    public void test() throws InterruptedException {
        Config config = ConfigService.getAppConfig();

        //1. 获取配置中不存在的值
        String key = "boss";
        String defaultValue = "咖啡";
        String boss = config.getProperty(key,defaultValue);
        System.out.println("boss=" + boss);

        //2. 获取配置中存在的值
        String key1 = "name";
        String defaultValue1 = "盖伦";
        String name = config.getProperty(key1, defaultValue1);
        System.out.println("name=" + name);

        // 监听配置变化事件，有值修改时能得到通知
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                System.out.println("发生修改数据的命名空间是：" + changeEvent.getNamespace());

                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(change);
                    System.out.println(String.format("发现修改 - 配置 key: %s, 原来的值： %s, 修改后的值: %s, 操作类型：%s",
                            change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
                }
            }
        });

        // 睡眠5分钟，测试监听事件
        TimeUnit.MINUTES.sleep(5);
    }
}
