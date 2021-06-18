

# bboss http工程
 bboss http  project.
 包含的功能有：

 http连接池

 http服务调用组件-HttpRequestUtil

 http负载均衡组件-HttpRequestProxy  使用参考文档https://esdoc.bbossgroups.com/#/httpproxy

 负载均衡组件特点：

 1.服务负载均衡（目前提供RoundRobin负载算法）

 2.服务健康检查

 3.服务容灾故障恢复

 4.服务自动发现（zk，etcd，consul，eureka，db，其他第三方注册中心）

 5.分组服务管理

 可以配置多组服务集群地址，每一组地址清单支持的配置格式：

 http://ip:port

 https://ip:port

 ip:port（默认http协议）

 多个地址用逗号分隔

 6.服务安全认证（配置basic账号和口令）

 7.主备路由/异地灾备特色

 负载均衡器主备功能开发，如果主节点全部挂掉，请求转发到可用的备用节点，如果备用节点也挂了，就抛出异常，如果主节点恢复正常，那么请求重新发往主节点 



# 工程gradle构建运行说明：
构建发布版本：gradle publish


# License

The BBoss Framework is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0

# todo
