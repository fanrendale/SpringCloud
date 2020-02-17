### 介绍：
1. 该项目主要测试 Sharding-JDBC 实现分库分表
2. 数据源使用 Druid 。 访问数据库操作使用的 MyBatis-Plus
3. 使用 Xml 来进行 Sharding-JDBC 的配置
4. 测试在同一个 MySQL 中建两个数据，分别建表和添加数据如下：
    ```sql
    CREATE TABLE `user` (
      `id` BIGINT(64) NOT NULL,
      `city` VARCHAR(20) NOT NULL,
      `name` varchar(20) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=INNODB  DEFAULT CHARSET=utf8;
    
    -- ds_0 数据库添加
    INSERT into user values(1001, '上海', '咖啡');
    -- ds_1 数据库添加
    INSERT into user values(1002, '北京', '盖伦');
    ```
#### 读写分离
1. 查看俺写的博客: https://blog.csdn.net/fanrendale/article/details/104350362
2. 强制路由主库：
    ```java
    // 强制路由主库。 查询时去主库查询
    HintManager.getInstance().setMasterRouteOnly();
    ```
#### 不分库只分表
1. 常用分片算法：范围分片、取模分片、Hash 分片、时间分片等。
2. 此处模拟数据准备，在一个数据库中建 4 张表，结构一样：ds_0, ds_1, ds_2, ds_3
3. 分片配置在 sharding.xml 中，其中分片算法配置方式有两种（此处是取模分片）：
    1. 使用 inline ：algorithm-expression="user_${id.longValue() % 4}"
    2. 自定义分片算法类：com.xjf.sharding.algorithm.MyUserSingleKeyTableShardingAlgorithm
4. 添加 100 条数据，可以查看 4 个数据库，分别有 25 条数据。
#### 分库分表
1. 分库分表，此处实现为按城市存不同的数据库，在数据库中对 id 进行取模来分表。
2. Sharding-JDBC 默认的分布式主键，是采用 Twitter Snowflake 算法实现的。
3. 查看俺的博客：https://blog.csdn.net/fanrendale/article/details/104360255