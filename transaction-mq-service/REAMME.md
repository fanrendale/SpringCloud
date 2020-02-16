### 介绍：
1. 该项目为 可靠性消息服务，主要功能是包含对所有消息的操作
2. 操作 MySQL 数据使用的是 JdbcTemplate。有基本的增删改查。

### 分布式事务解决方案
1. 两阶段提交型：当有多个节点操作同一个事务时，无法保证整体的事务时是成功还是失败。则引入一个协调者，每个节点将自己的成功或失败告诉协调者。如果都成功，
   则事务进行提交，有一个失败，则不提交（回滚）。
2. TCC 补偿型：TCC 分别对应 Try、Confirm 和 Cancel。运行过程：
    - 首先通过 Try 锁住服务中的业务资源进行资源预留。
    - Confirm 操作是在 Try 之后进行，对 Try 阶段锁定的资源进行业务操作。
    - Cancel 则是在所有操作失败时的回滚操作
    - 缺点：开发成本高，每个资源都要有资源预留的接口。
3. 最终一致型（本案例的实现）：
    1. A 服务进行数据修改后，发送一个消息到消息服务，将该消息存储到消息表中。（修改失败就不发消息，进行回滚）
    2. 有一个消息发送服务，一直轮询消息表中没有发送的消息（可以多次重发）。将消息发送到 MQ 中。
    3. B 服务对 MQ 推送的消息进行消费，实现跟 A 服务修改数据保持一致性。
    - 拓展：避免 B 服务消息重复消息，可以建一张已消费消息表，在消费前进行判重。
    - 问题注意：如果 B 服务对消息一直没有消费成功，则会导致出现数据不一致。根据业务容错来。
    - 可看博客：https://blog.csdn.net/likun557/article/details/90290426
4. 最大努力通知型：主动方尽自己的最大努力通知被动方，但是不保证一定能通知到（允许消息丢失），可以提供查询接口给对方查询。