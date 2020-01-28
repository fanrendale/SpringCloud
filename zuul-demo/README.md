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