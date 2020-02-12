### 介绍：
1. 本项目主要是对 MongoDB 的使用，在 Test 中有测试。
2. 测试了 MongoTemplate 的基本增删改查 API 操作。使用 Junit 测试的。

#### 1. GridFS 文件操作
1. MongoDB 内置了文件系统 GridFS。当用户把文件上传到 GridFS 后，文件会被分割成大小为 256KB 的块，单独存放。
2. 操作了 GridFsTemplate 类直接 @Autowired 就可以使用
3. 此处实现了对图片的增删查功能。

#### 2. 使用 Repository 操作 MongoDB，测试使用 Junit