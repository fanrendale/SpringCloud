### 介绍
1. 该项目用来测试 Redis 的使用
2. 如果 RedisTemplate 操作的都是字符串，则可以使用专用类 StringRedisTemplate 
3. 用 Repository 操作 Redis，实现像操作 MySql 数据库一样来操作 Redis：
    - 主键 id 使用 @Id 注解，可以自动增长，不过是一串字符串，比如：fe15df52-830d-47f2-9b62-31021ae012c7
    - 在 Redis 中存储情况：注解 id 在 Set 类型中有一份，具体的实体所有值都在 Hash 类型中。