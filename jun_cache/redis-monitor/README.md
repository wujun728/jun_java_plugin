# [NRedis-Proxy1.1 final](http://git.oschina.net/284520459/nredis-proxy) 高性能中间件服务 #

## 一、	NRedis-Proxy 介绍 ##
　　NRedis-Proxy 是一个Redis中间件服务，第一个Java 版本开源Redis中间件，无须修改业务应用程序任何代码与配置，与业务解耦；以Spring为基础开发自定义标签，让它可配置化，使其更加容易上手；提供RedisServer监控以及自动、收到failover等功能；以netty 作为通信传输工具，让它具有高性能，高并发，可分布式扩展部署等特点,单机器单个RedisServer QPS在9千左右。 

## 二、	NRedis-Proxy 架构图 ##

### a)	系统架构图 ###

 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/122158_a7c6f4f8_378203.png "在这里输入图片标题")

### b)	技术架构图 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/122211_a53ce801_378203.png "在这里输入图片标题")

### c)	部署架构图 ###
   ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125954_0952e06a_378203.png "在这里输入图片标题")

## 三、	NRedis-Proxy 优势以及缺点 ##
### a)	功能优势 ###
1.  自带连接池，简单稳定且性能高效
2. 支持读写分离，从读按照权重算法
3. 支持灵活主从配置策略
4. 默认支持一致性哈希分片策略，扩展性强
5. 分片策略与从读取策略可自定义化
6. 支持主从自动、手动切换，下次应用程序或者机器重启不会受到任何影响
7. 支持HA 分布式部署，节点可随意扩展
8.  提供redis-monitor小型机器人，监听cpu、jvm、线程、redis 命中率等监控服务 
### b)	天然缺点 ###
　　中间件的存在，会自带网络损耗，但是网络带宽足够，可以忽略不计，最主要损耗在于协议解析。相比客户端分片等策略，中间件可以解决客户端应用过多，解决M*N 问题，Redis-Server连接数不够,造成机器CPU性能降低；如下图：

 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/122255_019f04cb_378203.png "在这里输入图片标题")

## 四、	NRedis-Proxy 类逻辑调用图 ##

 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/122315_e12b7e9c_378203.png "在这里输入图片标题")

## 五、	NRedis-Proxy 配置标签 ##

### a)	redisProxyNode ###
             
| 序号           | 名称            | 描述  |
| ------------- |:-------------:| -----:|
| 1      | id	 | Spring 标签唯一标志 |
| 2      | redisProxyHost	|   NRedis-proxy 对外提供主机号 |
| 3 | redisProxyPort	|    NRedis-proxy 对外提供端口号 |
| 4 | algorithm-ref     |    NRedis-proxy 多主分片实现类引用 |
| 5 | address      |   Zookpeer 地址 |
  
### b)	redisProxyMaster ###

| 序号           | 名称            | 描述  |
| ------------- |:-------------:| -----:|
| 1      | id	 | Spring 标签唯一标志 |
| 2      | host	|   RedisServer主主机名|
| 3 | port	|    RedisServer主端口号 |
| 4 | algorithm-ref     |    NRedis-proxy 从分片读取策略类引用 |
| 5 | config-ref      |  连接池配置 |
  
### c)	redisProxyCluster ###

| 序号           | 名称            | 描述  |
| ------------- |:-------------:| -----:|
| 1      | id	 | Spring 标签唯一标志 |
| 2      | host	|   RedisServer主主机名|
| 3 | port	|    RedisServer主端口号 |
| 4 | algorithm-ref     |    NRedis-proxy 从分片读取策略类引用 |
| 5 | config-ref      |  连接池配置 |
| 6 | weight      |  权重 |

### d)	 redisPoolConfig  ###

| 序号           | 名称            | 描述  |
| ------------- |:-------------:| -----:|
| 1      | id	 | Spring 标签唯一标志 |
| 2      | connectionTimeout	|   连接超时时间|
| 3 | maxActiveConnection	|    最大活跃连接数 |
| 4 | maxIdleConnection    |    最大空闲连接数 |
| 5 | minConnection      |  最小连接数 |
| 6 | maxWaitMillisOnBorrow     |  取出最大等待时间|
| 7 | initialConnection      |  初始化连接数 |
| 8 | timeBetweenEvictionRunsMillis|  每隔多久检查一次连接池 |
| 9 | minEvictableIdleTimeMillis|  连接池最小生存时间 |
| 10 | minIdleEntries     |  最小空闲数|
| 11 | testOnBorrow|  取出是否检测 |
| 12 | testOnReturn|  归还是否检测 |
| 13 | testWhileIdle|  空闲是否检测 |
  
### e)	默认两个分片策略 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/124615_d246cdd2_378203.png "在这里输入图片标题")
 
## 六、	NRedis-Proxy 部署 ##
###1. 部署环境要求 ###
 - 1.1 JDK 1.7  
 - 1.2 Redis-Server 
 - 1.3 Zookpeer3.4.6

###2.　调试步骤 ###
 - ** 2.1 启动Redis Server 主机器 **
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125043_9859a13e_378203.png "在这里输入图片标题")
 - ** 2.2 启动Redis Server **
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125054_3f429fa1_378203.png "在这里输入图片标题")
 - ** 2.3 启动 zookpeer ** 
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125109_2275b86e_378203.png "在这里输入图片标题")
 - ** 2.4 启动NRedis-Proxy Server **
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125126_4ec1db15_378203.png "在这里输入图片标题")
 - ** 2.5 使用Redis 命令行连接NRedis-Proxy **
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/125140_29ffc9fb_378203.png "在这里输入图片标题")

### 3. 部署方式 ###
　　maven执行nredis-proxy-bootstrap ,然后再找到 nredis-proxy-server.sh 文件执行

## 七、	redis-monitor监控 ##
### a)	nredis-proxy监控 ###
![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130520_d86c3e8d_378203.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130557_b5c6e729_378203.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130608_19c94f35_378203.jpeg "在这里输入图片标题")
### b)	redis-server监控 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130627_290fd567_378203.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130650_85c37528_378203.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130658_f6d7bff4_378203.jpeg "在这里输入图片标题")
### c)	redis-monitor本身监控 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130744_f40d3a6b_378203.jpeg "在这里输入图片标题")
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130751_7f152334_378203.jpeg "在这里输入图片标题")
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130800_f421d41e_378203.jpeg "在这里输入图片标题")
## 八、	redis-monitor 服务治理 ##
### a)	定时器配置 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130810_d9f9357f_378203.jpeg "在这里输入图片标题")
### b)	手动服务治理 ###
 ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130817_0eab7c35_378203.jpeg "在这里输入图片标题")
### c)	自动服务治理 ###
  ![输入图片说明](http://git.oschina.net/uploads/images/2017/0116/130823_5fb4fc86_378203.jpeg "在这里输入图片标题")