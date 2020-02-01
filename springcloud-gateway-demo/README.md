### 介绍：
1. Springcloud Gateway的简单使用
2. Gateway 整合 Eureka ，配置路由转发规则如下：
    ```yaml
    spring:
      cloud:
        gateway:
          # 路由配置
          routes:
            - id: user-service
              # 服务名称区分大小写
              uri: lb://eureka-client-user-service
              predicates:
                - Path=/ecus/**
              filters:
              # 转发请求时去掉1级前缀
              - StripPrefix=1
    ```
    Gateway 路由规则和 Zuul 规则比较：<br>
    ①在 Zuul 中：
    
        ```properties
        zuul.routes.eureka-client-user-service.path=/us/**
        ```
    此时将 eureka-client-user-service 服务路由给 us，请求转换如下：<br>
    **请求：http://localhost:8090/us/user/hello =======> 真实：http://localhost:8090/eureka-client-user-service/user/hello**
    
    ②在 Gateway 中，配置如上面的 yml 配置文件：<br>
    如果没有 StripPrefix=1 ，则转换如下:<br>
    **请求：http://localhost:8090/ecus/user/hello =======> 真实：http://localhost:8090/ecus/user/hello** <br>
    不过此时会去 eureka-client-user-service 中寻找以 **/ecus** 开头的请求匹配，而真正服务只有 **/user/hello** 的接口，所以不合理。<br>
    添加 StripPrefix=1 后，则转换如下：<br>
    **请求：http://localhost:8090/ecus/user/hello =======> 真实：http://localhost:8090/user/hello** <br>
    此时寻找的就是 eureka-client-user-service 服务中以 **/user/hello** 开头的接口服务
3. 注意：
    1. 在 Zuul 中默认会对 Eureka 中注册的服务进行转发，而在 Gateway 中需要我们配置开启。
    2. 在 Zuul 中对服务名称的大小写不区分，Gateway 中只能大小写二选一
4. 路由断言工厂：
    - Path 路由断言工厂
    - Query 路由断言工厂
    - Method 路由断言工厂
    - Header 路由断言工厂
5. 自定义断言工厂。断言工厂的匹配规则可以用 yml 配置，也可以用代码来配置。**断言工厂可以组合使用**。在 yml 配置时有两种方式，如下：
    ```yaml
       predicates:
        - Path=/order/**
        # 方式一：自定义的代码需要实现 shortcutFieldOrder() 方法
        - CheckAuth=xjf
        # 方式二：自定义的代码不需要实现 shortcutFieldOrder() 方法
        - name: CheckAuth
        # 注意：args 与 name 是对齐的，书上没有对齐，会运行失败
          args:
            name: xjf
    ```
6. 四种过滤器工厂的使用：
    ```yaml
    filters:
        # 转发请求时去掉1级前缀
        - StripPrefix=1
        # 添加请求头
        - AddRequestHeader=X-Request-Foo, myHeader
        # 移除请求头
        - RemoveRequestHeader=Cache-Control
        # 接收单个状态，设置 Http 请求的响应码。即自定义所有请求返回的状态码
        - SetStatus=401
        # 重定向, 将请求重定向到百度，并指定响应的状态码
        - RedirectTo=302, http://baidu.com
    ```
7. 自定义过滤器：<br>
    - 一个参数：继承类 AbstractGatewayFilterFactory
    - 两个参数：继承类 AbstractNameValueGatewayFilterFactory
8. 自定义全局过滤器
    - 使用 @Order 来指定执行的顺序，数字越小，优先级越高
    - 自定义实现全局过滤器，只需要实现 GlobalFilter 、 Ordered 两个接口就可以
9. Gateway 限流，可以使用 IP 限流、用户限流、接口限流，结合 Redis 使用（连接 Redis 时， Redis 的配置保护模式需要关闭。<br>
测试 IP 限流时，接口访问后，只在几秒的时间内 Redis 会多两个参数，等一会儿就没了，参数是：
    - request_rate_limiter.{0:0:0:0:0:0:0:1}.tokens     :存储的是当前这秒钟对应的应用可用令牌数量
    - request_rate_limiter.{0:0:0:0:0:0:0:1}.timestamp  :存储的是当前时间的秒数，时间戳
10. 熔断回退：实现请求服务不可达时，降级的方法。目前支持 forward 模式的URI。  
11. 配置跨域两种方式：①使用代码配置 ②使用 yml 配置  
12. 网关的全局异常处理: Gateway 中的全局异常不用使用 @ControllerAdvice ，需要自己使用 Json 返回。
该全局异常处理只对网关有用，服务间的异常不受影响（还是用 @ControllerAdvice 处理）。

