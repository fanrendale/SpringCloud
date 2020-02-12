### 介绍：
1. 本项目主要是对 MongoDB 的使用，在 Test 中有测试。
2. 测试了 MongoTemplate 的基本增删改查 API 操作。使用 Junit 测试的。

#### 1. GridFS 文件操作
1. MongoDB 内置了文件系统 GridFS。当用户把文件上传到 GridFS 后，文件会被分割成大小为 256KB 的块，单独存放。
2. 操作了 GridFsTemplate 类直接 @Autowired 就可以使用
3. 此处实现了对图片的增删查功能。

#### 2. 使用 Repository 操作 MongoDB，测试使用 Junit
#### 3. 自定义实现自增 ID
1. 思路：
    - 使用一个序列集合来保存所有（需要自增 ID 的集合）的集合名称与 ID
    - 使用一个自定义注解了标识需要自增 ID 的集合
    - 当进行业务数据新增时，先从序列集合中获取到新增后的 ID 值，然后再对业务数据进行新增，此时的 ID 就是自增的。
    - 在实现时需要继承实现 AbstractMongoEventListener 类，来实现自定义的自增逻辑。
2. 自增 ID 的实现，就类似于 MySQL 中的自增 ID。
3. 测试在 Junit 中
#### 4. 自定义实现批量更新
1. 原理应该是调用的 MongoDB 的原生 js 命令。