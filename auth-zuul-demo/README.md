### 介绍：
1. 该项目是一个 zuul 网关，需要做的是前端调用服务时也需要验证 token。
2. 使用一个 pre 的过滤器来给请求的请求头添加上 token。
3. 记得要在配置文件添加如下：
    ```properties
    # sensitiveHeaders会过滤客户端附带的headers
    zuul.sensitive-headers=Cookie,Set-Cookie
    ```