### 介绍：
1. 该项目主要测试 分布式任务调度框架 Elastic-Job。地址：https://github.com/elasticjob
2. Elastic-Job 的配置方式有两种：①Xml 方式配置。②Java 代码配置。两种都进行了测试

3. 数据流任务：streaming-process 参数配置是否开启流式作业。

 **注意：Xml 中配置必须要配置 id**
 4. 在线生成 corn 表达式: http://cron.qqe2.com/
 #### 分片任务
 1. sharding-item-parameters 配置分片的参数，该配置的参数在任务中可以进行获取，然后我们自己判断该参数怎么使用。至于执行的机器选择，有 Elastic-Job
    的任务节点分片策略决定（有三种策略）。
2. overwirte 参数：需要覆盖注册中心的配置信息。
3. failover：是否开启失效转移。比如有两个分片，开的两台机器，如果其中一台挂了，如果开启了失效转移，则所有的任务都会分配到活着的可用的那台机器上(在下一次任务执行前)。
#### 事件追踪
1. Elastic-Job提供了事件追踪功能，可通过事件订阅的方式处理调度过程的重要事件，用于查询、统计和监控。
   Elastic-Job目前提供了基于关系型数据库两种事件订阅方式记录事件。
#### 自定义任务监听器
1. 自定义监听器实现 ElasticJobListener 接口
2. 在任务中添加如下配置：
    ```xml
    <job:listener class="com.xjf.demo.listener.MyJobListener" />
    ```
#### 自定义任务异常处理
1. 自定义异常处理实现 JobExceptionHandler 接口
2. 配置 job 的属性 job-exception-handler 
#### 运维平台
1. 可以查看我写的博客，对运维平台进行编译部署：https://blog.csdn.net/fanrendale/article/details/104342830
 
