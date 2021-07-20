> 1.公共参数

属性名 | 默认值|备注
---|    ---    |---
redisson.password | |用于节点身份验证的密码。 
redisson.pingConnectionInterval | 1000| 连接时间,超过这个时间则报错
redisson.timeout | 3000|等待节点回复命令的时间。该时间从命令发送成功时开始计时。 
redisson.attemptTimeout |10000L | 等待获取锁超时时间,-1则是一直等待 单位毫秒
redisson.lockWatchdogTimeout | 30000| 锁定之后业务最长的执行时间,超时则报错回滚业务(毫秒) 
redisson.dataValidTime | 1000\*60\* 30L | 数据缓存时间 默认30分钟 -1永久缓存
redisson.lockModel | 单个key默认`可重入锁`多个key默认`红锁` | 锁的模式.如果不设置, REENTRANT(可重入锁),FAIR(公平锁),MULTIPLE(联锁),REDLOCK(红锁),READ(读锁), WRITE(写锁)
redisson.model |SINGLE | 集群模式:SINGLE(单例),SENTINEL(哨兵),MASTERSLAVE(主从),CLUSTER(集群),REPLICATED(云托管)
redisson.codec | org.redisson.codec.JsonJacksonCodec | Redisson的对象编码类是用于将对象进行序列化和反序列化，以实现对该对象在Redis里的读取和存储 
redisson.threads | 当前处理核数量 * 2 |这个线程池数量被所有RTopic对象监听器，RRemoteService调用者和RExecutorService任务共同共享。 
redisson.nettyThreads | 当前处理核数量 * 2 | 这个线程池数量是在一个Redisson实例内，被其创建的所有分布式数据类型和服务，以及底层客户端所一同共享的线程池里保存的线程数量。 
redisson.transportMode | NIO | TransportMode.NIO,</br>TransportMode.EPOLL - 需要依赖里有netty-transport-native-epoll包（Linux）</br> TransportMode.KQUEUE - 需要依赖里有 netty-transport-native-kqueue包（macOS） 
redisson.idleConnectionTimeout | 10000 | 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒
redisson.connectTimeout |10000 |同任何节点建立连接时的等待超时。时间单位是毫秒。 
redisson.retryAttempts |3 |如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。 
redisson.retryInterval |1500 |在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。 
redisson.subscriptionsPerConnection |5 | 每个连接的最大订阅数量。 
redisson.clientName | |在Redis节点里显示的客户端名称。 
redisson.sslEnableEndpointIdentification |true |开启SSL终端识别能力。 
redisson.sslProvider | JDK|确定采用哪种方式（JDK或OPENSSL）来实现SSL连接。 
redisson.sslTruststore | | 指定SSL信任证书库的路径。
redisson.sslTruststorePassword | |指定SSL信任证书库的密码。 
redisson.sslKeystore | |指定SSL钥匙库的路径。 
redisson.sslKeystorePassword | |指定SSL钥匙库的密码。 
redisson.keepPubSubOrder | true|通过该参数来修改是否按订阅发布消息的接收顺序出来消息，如果选否将对消息实行并行处理，该参数只适用于订阅发布消息的情况。 


>2. 单例模式参数

属性名 | 默认值|备注
---|---|---
redisson.singleServerConfig.address | | 服务器地址,必填ip:port
redisson.singleServerConfig.database | 0| 尝试连接的数据库编号。
redisson.singleServerConfig.subscriptionConnectionMinimumIdleSize |1 | 用于发布和订阅连接的最小保持连接数（长连接）。Redisson内部经常通过发布和订阅来实现许多功能。长期保持一定数量的发布订阅连接是必须的。
redisson.singleServerConfig.subscriptionConnectionPoolSize | 50| 用于发布和订阅连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
redisson.singleServerConfig.connectionMinimumIdleSize |32 | 最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度。
redisson.singleServerConfig.connectionPoolSize |64 | 连接池最大容量。连接池的连接数量自动弹性伸缩。
redisson.singleServerConfig.dnsMonitoringInterval |5000 | 用来指定检查节点DNS变化的时间间隔。使用的时候应该确保JVM里的DNS数据的缓存时间保持在足够低的范围才有意义。用-1来禁用该功能。

>3. 集群模式


属性名 | 默认值|备注
---|---|---
redisson.multiple-server-config.node-addresses | | 服务器节点地址.必填 <br/>redisson.multiple-server-config.node-addresses[0]=127.0.0.1:6379<br/>redisson.multiple-server-config.node-addresses[1]=127.0.0.1:6380<br/>redisson.multiple-server-config.node-addresses[2]=127.0.0.1:6381<br/>
redisson.multiple-server-config.loadBalancer |org.redisson.connection.balancer.RoundRobinLoadBalancer | 在多Redis服务节点的环境里，可以选用以下几种负载均衡方式选择一个节点：<br/> org.redisson.connection.balancer.WeightedRoundRobinBalancer - 权重轮询调度算法<br/> org.redisson.connection.balancer.RoundRobinLoadBalancer - 轮询调度算法<br/> org.redisson.connection.balancer.RandomLoadBalancer - 随机调度算法
redisson.multiple-server-config.slaveConnectionMinimumIdleSize |32 | 多从节点的环境里，每个 从服务节点里用于普通操作（非 发布和订阅）的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时读取反映速度。
redisson.multiple-server-config.slaveConnectionPoolSize |64 | 多从节点的环境里，每个 从服务节点里用于普通操作（非 发布和订阅）连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
redisson.multiple-server-config.masterConnectionMinimumIdleSize |32 | 多节点的环境里，每个 主节点的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度。
redisson.multiple-server-config.masterConnectionPoolSize | 64| 多主节点的环境里，每个 主节点的连接池最大容量。连接池的连接数量自动弹性伸缩。
redisson.multiple-server-config.readMode | SLAVE| 设置读取操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里读取。 MASTER - 只在主服务节点里读取。 MASTER_SLAVE - 在主从服务节点里都可以读取。
redisson.multiple-server-config.subscriptionMode |SLAVE | 设置订阅操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里订阅。 MASTER - 只在主服务节点里订阅。
redisson.multiple-server-config.subscriptionConnectionMinimumIdleSize |1 |用于发布和订阅连接的最小保持连接数（长连接）。Redisson内部经常通过发布和订阅来实现许多功能。长期保持一定数量的发布订阅连接是必须的。 redisson.multiple-server-config.subscriptionConnectionPoolSize |50 | 用于发布和订阅连接的连接池最大容量。连接池的连接数量自动弹性伸缩。connectionMinimumIdleSize（最小空闲连接数）
redisson.multiple-server-config.dnsMonitoringInterval |5000 | 监测DNS的变化情况的时间间隔。
redisson.multiple-server-config.scanInterval | 1000 | (集群,哨兵,云托管模特特有) 对Redis集群节点状态扫描的时间间隔。单位是毫秒。
redisson.multiple-server-config.database | 0 | (哨兵模式,云托管,主从模式特有)尝试连接的数据库编号。
redisson.multiple-server-config.masterName | | (哨兵模式特有)主服务器的名称是哨兵进程中用来监测主从服务切换情况的。