### 介绍：
1. 本项目主要测试 API 文档管理工具 Swagger 的使用
2. 在注册中心配置 Swagger 跳转如下：
    ```properties
    # 配置注册中心的当前服务的状态地址，可以直接点击到 Swagger 接口页面
    eureka.instance.status-page-url=http://${spring.cloud.client.ip-address}:${server.port}/swagger-ui.html
    ```