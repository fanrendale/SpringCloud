### 介绍：
1. Zuul 接收文件，及对应的文件大小配置等。默认只能接收 1MB 及以下的文件。
2. Zuul 接收大文件解决办法：<br> 
①Zuul 会检查文件大小，在 Zuul 服务的配置文件中配置如下：
    ```properties
    spring.servlet.multipart.max-file-size=1000Mb
    spring.servlet.multipart.max-request-size=1000MB
    ```
    ②在转发的请求路径中，在开头添加路径 "/zuul" 可以绕过 Zuul 的大文件检测。但是具体的接收服务也必须要配置接收大文件，否则也会上传失败。此时的 Zuul 只是中转，不进行检测。
3. 大文件上传比较费时，要设置合理的超时时间：
    ```properties
       ribbon.ConnectionTimeout=3000
       ribbon.ReadTimeout=60000
    ``` 
    在 Hystrix 隔离模式为 Thread 时，需要设置 timeoutInMillisseconds 超时时间
4. **经验**： Zuul 路由转发，只能路由 Eureka 注册中心中的其他服务，不能路由本身。
5. 获取请求头信息：添加一个 pre 类型的过滤器，获取请求信息。
6. 获取响应内容：添加一个 post 类型的过滤器，有两种方式获取响应内容（原理在书上）：<br>
    ①从RequestContext中获取参数 "zuulResponse"。
    ```java
    Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");
    ```  
    ②获取响应的数据流
    ```java
    InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();
    ```
7. Zuul 自带的 Debug 功能：开启 DebugFilter 过滤器，然后把 Debug 信息添加响应头信息中。配置如下：
    ```java
    # Zuul 自带的 Debug 功能
    # 这两个的配置功能一样：开启 Debug 过滤器
    zuul.debug.request=true
    # 此配置是指定请求参数的某个属性的值为true则开启(可以自定义请求中的一个属性)，默认是 debug 属性（可以在请求中加 debug=true ）
    zuul.debug.parameter=flag
    # 开启在响应过滤器中将 debug 信息填充到响应头中
    zuul.include-debug-header=true
    ```