### 介绍：
1. 该项目为 消息发送服务（消息生产者）。
2. 使用 Feign 来调用消息服务，以及加上了 Hystrix 熔断。启动 Hystrix 熔断功能时，需要打开如下配置：
    ```properties
    # 打开 Feign 的 Hystrix 的熔断功能
    feign.hystrix.enabled=true
    ```
#### 一、 两个并发关键字
1. Semaphore : 是 synchronized 的加强版，作用是控制线程的并发数量
2. CountDownLatch : 使一个线程等待其他线程各自执行完毕后再执行

#### 二、消息发送服务讲解
1. 消息发送服务的运行时自动的，启动后会一直运行，每隔 10s 去调用消息服务取等待发送的消息。
2. 自动运行时创建的线程，使用线程池来执行具体操作。
3. 使用 Redis 来做分布式锁，因为我们的本服务是集群。如果不加锁，则有可能两个实例同时发送同一个消息，则会导致一些问题，比如消息重复发送等。
4. 使用信号量 Semaphore 来控制流量，使用 CountDownLatch 来控制顺序。
5. 在发送到 MQ 之前，会对消息进行基础验证，比如重试次数是否已经到达死亡次数，以及同一个消息的发送间隔是否达到 30s 等。
6. 使用 Feign 来调用消息服务，使用 Hystrix 实现熔断。