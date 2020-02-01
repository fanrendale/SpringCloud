### 介绍：
1. 主要是使用 smconf 分布式配置管理
2. 使用注意：
    - 必须要在配置文件中配置 smconf.conf.package ，在 Spring 初始化前加载配置
    - smconf 分为客户端和服务端。在使用时，我们需要将 cxytiandi-conf-client 客户端包首先打入本地 maven 仓库，然后引用
    - 重点：要实现配置变量的共享，需要先在本地建立对应的配置属性的实体类，当该配置类在 MongoDB 中还没有值时，则将本地的值初始化到数据库中；如果数据库中有值，则是将数据库中的值拉取到本地变量中。
    - 动态修改变量值：变量值在服务端修改后，会根据 Zookeeper 的 Watcher 事件推送到本地。实体类的实现方法 reload() 方法可以获取到，我们可以自己将对应的值进行更新(但是由配置文件配置的值不会动态改变)。
3. Smconf 项目地址：[https://github.com/yinjihuan/smconf](https://github.com/yinjihuan/smconf)