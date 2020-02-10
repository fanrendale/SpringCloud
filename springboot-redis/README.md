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
    - 书上讲解的是会生成一个 zset 保存所有的 id 值，zset 的 key 为 get。实践出来没有 zset
    - 真实运行生成只有一个 String ，如果传的 id 值为 1001 ， 则真实 key 为 ：get::1001
    - 不一致的原因可能是 Redis 版本不一致。没实践。
     