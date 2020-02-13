### 介绍：
1. 本项目主要是对 Elasticsearch 的基本操作
2. 使用 Repository 方式和 ElasticsearchTemplate 方式分别操作

#### 索引构建
1. 索引构建方式：
    1. 我们自己写代码将 MySQL 或 MongoDB 的数据同步保存到 ES 中:
        1. 用 ElasticTemplate 实现：从 MySQL 或 MongoDB 获取到所有数据后，按批次（比如一万条一批）封装成 ES 的实体，然后调用 elasticTemplate 
           的 save 方法保存即可。save 方法是基于 bulkIndex 实现的。
        2. 用 transport client 实现。
    2. 使用第三方工具来做数据同步：
        1. 将 MySQL 数据同步可以用 elasticsearch-jdbc (https://github.com/jprante/elasticsearch-jdbc)
        2. 将 MongoDB 数据同步可以用 mongo-connector (https://github.com/mongodb-labs/mongo-connector)