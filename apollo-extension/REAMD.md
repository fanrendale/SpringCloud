### 介绍：
1. 该项目主要是对 Apollo 的拓展使用。
2. 使用 Apollo 实现了动态配置日志级别
#### Apollo 存储加密的值
1. 使用加密框架 jasypt-spring-boot 来实现 Apollo 存储加密的值
2. 使用工具类在本地将需要加密的值生成加密后的字符串，然后将字符串存储到 Apollo 中。
3. 在项目中使用 @Value 来获取值，自动解密。缺点：
    - 在配置中心修改值，项目中的值不会刷新。只是初始化加载的值
    - 注入 Config 对象获取的值无法解密
4. 重点：解决缺点，实现能动态修改加密值，能使用 Config 对象获取值，方法如下：
    1. 由我们自己实现 Apollo 的 DefaultConfig 类，因为该类在 Apollo 初始化和修改值的时候都会调用，其中有一个 updateConfig 方法。我们只需要在
       该方法中加上对加密值进行解密的逻辑，并且将值装进属性中，就可以解决问题。
    2. 类的全路径是 com.ctrip.framework.apollo.internals.DefaultConfig.DefaultConfig, 因为我们要重新实现它，直接创建对应的包 
       com.ctrip.framework.apollo.internals，然后再创建 DefaultConfig 类（复制过来即可），然后添加对应的解密。
    3. 由于我们解密用的算法是 AES ，因此加密也要这个。另外注意加密和解密的 Key 要一致。此时，就解决了问题。
    4. 在 Apollo 中存储加密后的值