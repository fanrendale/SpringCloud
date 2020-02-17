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