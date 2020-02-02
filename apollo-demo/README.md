### 介绍：
1. 分布式配置管理 Apollo 的使用。在 Java 中使用的示例代码在测试文件中
2. 从配置中心取值：
    1. 使用 Placeholder 注入配置
    2. Java Config 方式
    3. ConfigurationProperties 方式注入值。配置中心配置 redis.cache.host。（不推荐）
    4. @ApolloConfig 注解方式
3. 使用 @ApolloConfigChangeListener 注解方式添加值修改的监听。
4. @ApolloJsonValue 注解添加 Json 值