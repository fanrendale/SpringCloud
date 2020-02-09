### 介绍：
1. 该项目为测试 Zuul 网关扩展内容的用户服务，有登录等接口
2. 实现登录接口，账号密码正确则返回生成的 token

#### 接口限流：
1. 某个服务可能被调用的频率很高，因此需要实现对接口的限流。使用信号量来实现，限流算法是令牌桶算法，没有获取令牌会阻塞。
2. 实现：
    1. 自定义一个注解，注解可以配置一个 String 值，该值对应 Apollo 中的 key。该注解使用在方法上，对应的变量 key 是该接口的限流值。
    2. 自定义一个类实现 ApplicationContextAware 接口，目的是读取所有有 @RestController 注解的类，然后获取所有的方法。并初始化信号量，
    默认的限流值用一个信号量，使用了自定义注解的则根据配置的变量来初始化信号量。所有信号量的允许值都默认写个 100 （后面会初始化修改）。
    3. 定义一个 Apollo 的取值类，使用 Apollo 的 @ApolloConfig 注解的 Config 变量来动态获取不同的值。使用一个 @Bean 的方法（主要是初始化，不是配置 Bean），
    从 Apollo 中获取值后，将所有的信号量的允许值都覆盖。并写上一个 Apollo 值变化的回调方法，也要更新信号量的值。
    4. 定义一个切面，做具体的限流控制，切点为所有的方法。根据方式是否有注解来取对应的限流信号量，然后调用信号量的 acquire() 方法来获取一个许可
    （记得释放许可）。此时就配置好了接口限流。
    5. 测试：使用 Apache ab 来测试，为了看效果，调用的接口都睡眠了 0.5s 。直接调用该服务的接口，不经过网关（因为阻塞时间久了会触发 Hystrix 的降级）。
    6. 总结：此时可以使用 Apollo 动态配置具体的接口限流值和默认的限流值。
3. 本项目实现是结合了 Apollo 来配置，如果简单点可以使用 properties 配置文件来配置。
4. <font color="red">升级：对于 自定义注解、 Apollo 取值类、初始化获取类的所有方法的类 可以放到公共包中。(实践未成功)</font>
    