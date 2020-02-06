### 介绍：
1. zuul的简单使用
2. zuul集成eureka使用：注册到服务注册中心中，可以使用服务名称来调用服务接口。
访问规则： API 网关地址 + 访问的服务名称 + 接口 URI
3. 指定具体服务路由
4. 路由前缀
5. 本地跳转。使用 forward 实现（转发）
6. 使用 zuul 实现 IP 过滤器
7. zuul 禁用过滤器有两种方式：一、在实现ZuulFilter中的shouldFilter()方法返回false。  
二、在properties文件中配置：zuul.IpFilter.pre.disable=true。（规则：zuul.过滤器的类名.过滤器类型.disable=true）
8. 过滤器中传值，使用RequestContext，原理是使用的ThreadLocal。如下：
```java
RequestContext ctx = RequestContext.getCurrentContext();
ctx.set("name", "xjf");
Object name = ctx.get("name");
```
9. 如果有多个过滤器，第一个过滤器不符合条件应直接返回。<br> 该情况不能靠设置 ctx.setSendZuulResponse(false); 解决。
解决办法：在拦截方法中添加一个参数（比如： ctx.set("isSuccess", false) ），然后再 ShouldFilter 方法中获取参数，判断后面的过滤器是否应该执行。
10. 过滤器中异常处理：①自定义异常过滤器实现 ZuulFilter ，实现日志记录功能。
②自定义异常返回数据，自定义 Controller 实现 ErrorController ,可以实现 Json 格式返回。（不用 @ControllerAdvice 的原因是，该方法是作用于 @RequestMapping 的方法上，对 Zuul 无效。

---

**第二部分**:
1. Zuul 的容错机制： zuul 可以配置重试机制，在当前服务不可用时，自动重试该服务的下一个服务，直到遇到可用的服务。
2. Zuul 的回退机制：有时某个服务都不可用，则调用时会报错。 Zuul 默认整合了Hystrix ，自定义实现回退，需要实现 FallbackProvider 接口，当后端服务异常时设置返回默认的数据。
3. @EnableZuulProxy 与 Spring Boot Actuator 配合使用时，暴露所有端点，可以访问 "http://localhost:2103/actuator/routes" 查看路由信息。
4. 端点对应关系: /routes ===> 所有路由信息。  /filters ===> 所有过滤器信息
5. 在 Zuul 中聚合所有服务的 Swagger 文档，去除调自身服务。这样方便文档统一管理。 在 Web 页面的右上角可以选择不同的服务来查看文档。