### 介绍：
1. 本项目主要介绍 Zuul 网关的扩展
2. 动态管理不需要拦截的 API 请求：使用 Apollo 动态配置白名单（不需要拦截），在 Zuul 中用过滤器过滤掉白名单，其他地址都需要拦截。
    - 注意：有些接口地址会带有参数，比如：/user/login/3，这种在过滤时要将参数去掉来比较。
3. 认证过滤器，白名单不拦截。拦截器中对要拦截的请求会进行 token 验证，如果不合格则返回错误信息；合格就将用户名方法到请求头中，传递走
4. 单节点限流，即一个网关中配置：
    - 令牌桶法：在规定时间内的最高并发量确定，比如1s 最多处理1000个请求，多的请求就等待。Google Guava 有实现，直接使用 RateLimiter 类。
    - 漏桶：输出速率一定，输入速率不定，如果超出了容量，则抛弃。
5. RateLimiter 的令牌桶法限流，使用了 Apollo 配置，可以动态配置 1s 的最大产生令牌数，即 1s 的最大处理请求数。实现了动态限流。
6. 使用 ab压测工具 模拟访问，参考博客:https://www.cnblogs.com/chanwahfung/p/11877021.html。 使用如下：
    ```jshelllanguage
    ab -n 1000 -c 30 http://localhost:2103/zuul-extend-user-service/user/hello
    ```
    说明：
    - -n 1000 代表总共请求 1000 次
    - -c 表示并发数量

#### 集群限流：
1. 集群限流使用 Redis 实现，此处使用的是漏桶算法，即使有很多个请求，也最多只会接收确定的最大数量。
2. 在 Apollo 中配置值，对应集群限流的最大数量。
3. 集群限流原理：
    - 使用当前时间戳的秒数做 Redis 的 key， value 为这 1s 中处理请求数（不管成功还是未成功）。
    - 如果 value 值大于了限流的值，则这 1s 后面的请求都不处理（返回提醒），如果是小于限流值则成功访问接口。
    - 由于所有的网关服务都使用的 Redis ， 保证所有网关服务的时间一致，因此 Redis 是共享的，就实现了集群的限流。
4. 双重保护：在过滤器中首先进行集群限流，Redis 可能会挂，在异常捕获中使用单节点限流。如此做到双重保护。

#### 具体服务限流（单节点的服务限流俺没有实现）：
1. 单节点服务限流：单节点限流使用的是 RateLimiter ,要针对具体的服务，则给不同的服务用不同的 RateLimiter 就可以了。
2. 集群的服务限流：集群限流使用的 Redis 的 key 来区别。要针对具体的服务，则在生成 key 的时候，加上对应的服务名称，则可以实现不同的服务限流
   (获取 serviceId 的过滤器类型必须要为 "route" )。
   在 RibbonRoutingFilter 中有获取当前请求的服务 ID：
   ```java
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
       // 获取具体的 serviceId
        return (ctx.getRouteHost() == null && ctx.get(SERVICE_ID_KEY) != null
                && ctx.sendZuulResponse());
    }
    ```
#### Zuul 结合 Apollo 动态服务降级
1. 在 Apollo 中配置一个降级服务的名单，名单内容为服务的 serviceId（即在注册中心的名称），多个由英文逗号隔开
2. 定义一个 "route" 类型的过滤器，获取当前请求的 serviceId 同降级名单比较，如果存在于名单中则进行降级，不存在就放行。
3. 可以动态在 Apollo 中改变降级服务的名单，会马上生效，实现了动态服务降级。
4. 配置了转发 path 后，不会受影响。
4. 升级：案例需要我们手动去改变名单。我们可以监控某些指标，比如流量、负载等，达到指标后自动触发降级。

#### 灰度发布
1. InheritableThreadLocal 工具使用介绍：在 Spring Cloud 中我们用 Hystrix 来实现断路器，默认是用信号量来进行隔离的，信号量的隔离方式用 ThreadLocal
   在线程中传递数据是没问题的。当隔离模式为线程时， Hystrix 会将请求放入 Hystrix 的线程池中执行，这时候某个请求就由 A 线程变成 B 线程了， ThreadLocal
   必然没有效果了，这时候就用 InheritableThreadLocal 来传递数据。
2. 灰度发布：就是指定一部分用户调用的是指定的服务，这部分服务是先开发出来用一部分人试用。我们需要对人群和服务进行动态配置
3. 实现原理：
    1. 在 Apollo 中设置两个值，保存用户名集合和灰度的服务集合
    2. 设置一个灰度发布的过滤器，过滤器主要将 Apollo 中的配置值保存到本地变量中，该变量是由 InheritableThreadLocal 实现的。
    3. 自定义负载均衡策略，基于 RoundRobinRule 改造。如果当前用户在灰度发布的人群中，并且调用的服务是灰度服务，则将当前请求路由到指定的灰度服务；
    不然就从所有服务剔除灰度服务的地址后，选取服务的其他地址（非灰度服务地址）。
    4. 对指定服务使用自定义负载均衡策略，只有该服务会走这个策略，其他服务不会走。配置如下：
    ```properties
    # 启用自定义负载均衡策略，对指定的服务。其他服务不会使用该策略
    zuul-extend-user-service.ribbon.NFLoadBalancerRuleClassName=com.xjf.zuul.rule.GrayPushRule
    ```
    5. 如果是使用注解配置 Bean 的方式，则所有服务都会走。应该会有指定配置，未实践。
4. 可以按用户名称来使用灰度，也可按 IP 来使用，也可按其他的纬度来区分用户。
5. 问题：如果服务的灰度地址挂了，但是在注册中心中其服务地址还在，而此时会自定义负载均衡策略还是会返回该服务，因此会调用失败。
   只有等注册中心将该服务剔除后，才会去获取到非灰度的地址。因此会有一段时间报错。

#### 框架地址
1. 限流：https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit
2. 灰度：https://github.com/Nepxion/Discovery
