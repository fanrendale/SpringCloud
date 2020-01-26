### 说明：
1. 该项目测试使用feign进行声明式服务调用
2. 使用Feign调用接口方式一：创建API Client的feign调用接口，在controller中直接使用
3. 使用Feign调用接口方式二：抽取出一个maven项目，写上对应要调用的接口定义，在服务提供者的controller实现该接口，在服务消费者的接口调用中实现此接口，然后在controller中调用对应的方法。

<b> 第二种方式参看博客：https://www.jianshu.com/p/ff79509b0962  <br>
项目地址：https://github.com/hansonwang99/spring_cloud_feign_usage </b>