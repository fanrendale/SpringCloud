### 介绍：
1. 该项目是 SpringbootAdmin 的服务端，集成了 Eureka ，能查看所有服务的信息
2. 此时添加 springSecurity 开启认证，则在 Web 显示 当前服务是 DOWN 的，不知道原因。因此先注释
3. 当前服务也会在 Web 端被查看到，不过所有信息也需要暴露健康监控的所有端点。其他服务也要暴露才能看到