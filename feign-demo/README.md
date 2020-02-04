### 说明：
1. 该项目测试使用feign进行声明式服务调用
2. 使用Feign调用接口方式一：创建API Client的feign调用接口，在controller中直接使用
3. 使用Feign调用接口方式二：抽取出一个maven项目，写上对应要调用的接口定义，在服务提供者的controller实现该接口，在服务消费者的接口调用中实现此接口，然后在controller中调用对应的方法。（耦合度高）

    <b> 第二种方式参看博客：https://www.jianshu.com/p/ff79509b0962  <br>
    项目地址：https://github.com/hansonwang99/spring_cloud_feign_usage </b>
4. 添加如下 pom ，可以实现链路追踪
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    ```
5. 配置 logback ，实现日志使用 Json 格式输出
6. 集成 ZipKin ，显示调用日志
7. ZipKin 抽样比例设置
    ```properties
    # zipKin 抽样比例:默认是调用10次记录一次，改为1.0每次都记录
    spring.sleuth.sampler.probability=1.0
    ```
8. 异步任务线程池定义，使用 @Async开启一个异步任务后， Sleuth 会为调用创建一个 Span。如果自定义了异步任务的线程池，则无法创建 Span，就需要使用
 Sleuth 提供的 LazyTraceExecutor 来包装。说明：<br>
 在 Controller 中调用了一个异步的 Service 方法，如果在自定义了异步任务线程池后，直接返回 executor ，则该异步任务就不会显示出 Span。而使用了 LazyTraceExecutor 来包装，
 则会显示出来。
9. TracingFilter 添加自定义的标记以及将请求 ID 添加到响应头返回给客户端
10. Sleuth 监控本地方法两种：一、手动埋点。 二、 @NewSpan 注解埋点
11. 过滤不想跟踪的请求：对于某些请求不想跟踪，可以通过配置 HttpSampler 来过滤掉，如果 swagger 等请求。