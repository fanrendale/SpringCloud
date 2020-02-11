### 介绍
1. 该项目用来测试 Redis 的使用
2. 如果 RedisTemplate 操作的都是字符串，则可以使用专用类 StringRedisTemplate 
3. 用 Repository 操作 Redis，实现像操作 MySql 数据库一样来操作 Redis：
    - 主键 id 使用 @Id 注解，可以自动增长，不过是一串字符串，比如：fe15df52-830d-47f2-9b62-31021ae012c7
    - 在 Redis 中存储情况：注解 id 在 Set 类型中有一份，具体的实体所有值都在 Hash 类型中。
#### Spring Cache 整合 Redis
1. 我们一般使用缓存的逻辑：请求过来后首先去缓存中取值，如果缓存中有值则直接返回；没有的话就从数据库中查询，然后更新缓存，并返回给用户。
这种逻辑我们在每一次使用都会写，会冗余。因此使用 Spring Cache 可以使用切面来完成这一步，我们只需要注解就可以了。
2. Redis配置：在配置类加上 @EnableCaching 开启缓存。添加如下的配置：
    - 配置 RedisTemplate 的序列化，使用 Json 序列化，我们数据会由 Json 字符串保存。
    - 配置 CacheManager ，缓存的配置可以设置过期时间等。
    - 配置 KeyGenerator ， 自定义缓存 key 的生成策略，使 key 值能唯一。我此处的是：类名 + 方法名 + 参数
3. 使用注解来使用缓存，主要有以下三种注解：
    - @Cacheable: 查询的时候，如果缓存中有值则返回，没有则执行方法后并加入缓存
    - @CachePut: 新增和修改时，如果缓存中没有值则新增，有值则修改
    - @CacheEvict: 用于对数据删除的时候清除缓存中的数据
4. 生成 key 讲解，比如使用如下：
    ```java
    @Cacheable(value = "get", key = "#id")
    public Person get(String id) {
        Person person = new Person(id,"xjf","123456");
    
        return person;
    }
    ```
    - 书上讲解的是会生成一个 ZSet 保存所有的 id 值，ZSet 的 key 为 get。实践出来没有 ZSet
    - 真实运行生成只有一个 String ，如果传的 id 值为 1001 ， 则真实 key 为 ：get::1001
    - 不一致的原因可能是 Redis 版本不一致。没实践。
#### 缓存异常处理
1. 我们在使用 Redis 来做缓存时，可能会发生 Redis 服务挂掉，此时调用接口会报错，无法提供服务。我们应该进行异常处理，让 Redis 服务挂掉后也能从
   数据库查数据来继续提供服务。
2. 实现：添加一个配置类，继承实现 CachingConfigurerSupport 类，然后 errorHandler() 方法，并实现每种异常的具体处理。
3. 此时在 Redis 服务挂了之后，也能正常的提供服务。
#### 自定义缓存工具类
1. 如果不使用 Spring Cache 的注解方法，可以自定义实现缓存的工具类，包含了判断逻辑等。
2. 实现原理：
    - 相当于是对获取缓存时添加了一层包装，使用 Closure 接口来承接具体的数据库查询操作，然后将判断缓存是否有值的逻辑放在工具类中
    - 在使用缓存服务时实现 Closure 接口，接口内容为调用具体数据库的查询服务。
3. 注意：此时如果 Redis 挂了，是会出现异常无法提供服务。我在案例中在获取缓存和设置缓存时捕获了异常，可以暂时处理 Redis 异常的情况。有改进的空间。
   上面的缓存异常处理只适用于注解的缓存方式。
     