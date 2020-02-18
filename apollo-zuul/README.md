### 介绍：
1. 该项目主要是 Apollo 结合 Zuul 实现动态路由
2. 实现原理：
    - 首先通过 Apollo 配置路由配置，然后使用一个监听器来获取值
    - 使用 ApplicationContext 来设置更新后的值，实现动态更新
#### Apollo 整合 Archaius
1. 用 Apollo 的服务端来替代 Archaius 的服务端
2. 只需要在启动类添加如下：
    ```java
    public class ZuulApplication {
    
        public static void main(String[] args) {
            // 指定环境（开发演示用，不能用于生产环境）
            System.setProperty("env", "DEV");
    
            // 整合 Archaius, 将 Apollo 的服务端给 Archaius 使用
            // 指定 archaius 获取配置的 URL
            String apolloConfigServiceUrl = "http://localhost:8080";
            String appId = "SampleApp";
            System.setProperty("archaius.configurationSource.additionalUrls",
                    apolloConfigServiceUrl + "/configfiles/" + appId + "/default/application");
    
            SpringApplication.run(ZuulApplication.class, args);
        }
    }
    ```