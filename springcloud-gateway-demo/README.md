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


