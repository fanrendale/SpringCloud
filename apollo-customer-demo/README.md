### 介绍：
1. 该项目主要自定义演示 Apollo 的原理
2. 服务端的实时推送原理是基于 Spring DeferredResult 实现的：当一个请求到达API接口，如果该API接口的return返回值是DeferredResult，
在没有超时或者DeferredResult对象设置setResult时，接口不会返回，但是Servlet容器线程会结束，
DeferredResult另起线程来进行结果处理(即这种操作提升了服务短时间的吞吐能力)，并setResult，如此以来这个请求不会占用服务连接池太久，
如果超时或设置setResult，接口会立即返回。<br>
DeferredResult的执行流程如下：
    1. 浏览器发起异步请求
    2. 请求到达服务端被挂起
    3. 向浏览器进行响应，分为两种情况：
        1. 调用DeferredResult.setResult()，请求被唤醒，返回结果
        2. 超时，返回一个你设定的结果
    4. 浏览得到响应，再次重复1，处理此次响应结果
3. Apollo 客户端与 Spring 集成，实现通过 @Value 获取配置。集成原理：**在应用启动阶段， Apollo 从远端获取配置，然后组装成 PropertySource 并插入到第一个。
PropertySource 就是 Spring 的配置，Spring 支持使用 @Value 中该配置中取值。**  
4. 实现了在运行中动态修改配置的值，不怎么理解 