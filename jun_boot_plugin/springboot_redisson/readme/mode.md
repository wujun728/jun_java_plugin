
##### 单例模式

>单机版redis
```
#单Redis节点模式
redisson.singleServerConfig.address=127.0.0.1:6379
```

##### 集群模式

>集群模式除了适用于Redis集群环境，也适用于任何云计算服务商提供的集群模式，例如AWS ElastiCache集群版、Azure Redis Cache和阿里云（Aliyun）的云数据库Redis版。
```
#集群模式
redisson.model=CLUSTER
#redis机器.一直累加下去
redisson.multiple-server-config.node-addresses[0]=127.0.0.1:6379
redisson.multiple-server-config.node-addresses[1]=127.0.0.1:6380
redisson.multiple-server-config.node-addresses[2]=127.0.0.1:6381
```

##### 云托管模式

>云托管模式适用于任何由云计算运营商提供的Redis云服务，包括亚马逊云的AWS ElastiCache、微软云的Azure Redis 缓存和阿里云（Aliyun）的云数据库Redis版

```
#云托管模式
redisson.model=REPLICATED
#redis机器.一直累加下去
redisson.multiple-server-config.node-addresses[0]=127.0.0.1:6379
redisson.multiple-server-config.node-addresses[1]=127.0.0.1:6380
redisson.multiple-server-config.node-addresses[2]=127.0.0.1:6381
```

##### 哨兵模式

```
redisson.model=SENTINEL
#主服务器的名称是哨兵进程中用来监测主从服务切换情况的。
redisson.multiple-server-config.master-name="mymaster"
#redis机器.一直累加下去
redisson.multiple-server-config.node-addresses[0]=127.0.0.1:6379
redisson.multiple-server-config.node-addresses[1]=127.0.0.1:6380
redisson.multiple-server-config.node-addresses[2]=127.0.0.1:6381
```

##### 主从模式

```
redisson.model=MASTERSLAVE
#第一台机器就是主库.其他的为从库
redisson.multiple-server-config.node-addresses[0]=127.0.0.1:6379
redisson.multiple-server-config.node-addresses[1]=127.0.0.1:6380
redisson.multiple-server-config.node-addresses[2]=127.0.0.1:6381
```