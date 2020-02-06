### 介绍：
1. 该项目测试 SpringbootAdmin ，当前为客户端
2. 将当前项目注册到 SpringbootAdmin 的服务端上
3. 将日志输出到文件中，并能在服务端查看本地日志，客户端配置如下：
    ```properties
    # 配置本地日志文件的地址,会自动将日志输出到该文件中
    logging.file=G:/GitWareHouse/SpringCloud/spring-boot-admin-client/spring-boot-admin-client.log
    ```
4. 连接开启了验证的服务端，配置如下：
    ```properties
    # Spring Boot Admin 服务端开启了验证，客户端连接也必须要配置账号密码
    spring.boot.admin.client.username=xjf
    spring.boot.admin.client.password=123456
    ```