# J2Cache 版本更新记录


**J2Cache 2.4.1-release (2018-8-xx)**

* 修复 Spring Boot 模块的 bug ([#ILUOW](https://gitee.com/ld/J2Cache/issues/ILUOW))

**J2Cache 2.4.0-release (2018-8-4)**

* 增加 JSON 序列化的支持(测试阶段)

**J2Cache 2.3.23-release (2018-7-30)**

* 二级缓存增加对 memcached 的支持
* spring boot 模块的改进

**J2Cache 2.3.22-release (2018-7-3)**

* 增加单元测试代码
* 修复了使用 loader 加载缓存数据的逻辑错误问题

**J2Cache 2.3.21-release (2018-5-23)**

* 重新打包

**J2Cache 2.3.20-release (2018-5-23)**

* 修复 redis.maxWaitMillis 配置丢失导致的异常信息
* 这可能是 2.3.x 的最后一个更新版本

**J2Cache 2.3.19-release (2018-5-22)**

* 优化多线程同时读取同一个region同一个key情况下只读取一次L2数据，进一步降低redis的压力（实际100个线程测试性能提升30%左右，Redis 读取次数从 100 降低到 1）
* 可通过 `j2cache.default_cache_null_object` 配置 J2Cache 是否默认启用 null 对象缓存

**J2Cache 2.3.18-release (2018-5-21)**

* 解决了使用 spring boot devtool 时 ClassCastException 异常的问题
* 调整第三方包的依赖关系，不强制依赖，需要开发者自行加入依赖

**J2Cache 2.3.17-release (2018-5-16)**

* [#IJTFT](https://gitee.com/ld/J2Cache/issues/IJTFT) 修复CacheChannel的get接口的cacheNullObject参数并不生效的问题
* 对仓库进行重构，将扩展模块移入 modules 目录

**J2Cache 2.3.16-release (2018-5-4)**

* 修复了 Ehcache3 配置不失效缓存时，写入缓存异常的问题
* 修复了 Ehcache 下空指针的异常

**J2Cache 2.3.15-release (2018-5-4)**

* 修复了集群通知不工作的严重 Bug，如果你正在使用 2.3.13 和 2.3.14 版本，请即刻升级


**J2Cache 2.3.14-release (2018-5-3)**

* 允许通过 j2cache.broadcast = none 来关闭集群节点关于缓存数据的失效通知
* 修复了 json 反序列化失败的问题


**J2Cache 2.3.13-release (2018-5-1)**

* 支持通过 `j2cache.sync_ttl_to_redis` 配置项来决定是否 Redis 缓存的数据也带 ttl 信息（相当于该配置如果设置为 false 时，redis 上的数据不会自动过期，该配置值默认为 true）
* 增加 Spring Boot 2.0 的支持模块
* CacheChannel 增加 check 方法，用来检测 key 存在哪一级缓存

**J2Cache 2.3.12-release (2018-4-26)**

该版本增加了 redis 只读的模式，可通过配置启用
```
j2cache.L2.provider_class = readonly-redis  
j2cache.L2.config_section = redis
```

**J2Cache 2.3.11-release (2018-4-xx)**

* 修复 `redis` 接口中关于同步块的代码逻辑错误 [详情](https://gitee.com/ld/J2Cache/tree/7fe229d5f49f0c253e95810554b1bea58aa40f08/)
* 根据码云的 `Sonar` 检测的代码问题进行修复

**J2Cache 2.3.10-release (2018-4-5)**

* 升级 Caffeine 到 2.6.2 版本
* 升级 JGroups 到 3.6.15.Final 版本
* 升级 kryo-shaded 到 4.0.1 版本
* 升级 fastjson 到 1.2.46 版本
* 修复 ehcache 3 下使用 keys 方法的空指针异常
* 修复 @阿南哥 提交的关于 ehcache2 的 bug https://gitee.com/ld/J2Cache#note_893981

**J2Cache 2.3.9-release (2018-2-13)**

* 使用 Generic 模式时，写入 redis 的数据都带 TTL 参数设置，取值来自 L1 缓存中的配置【重大调整】
* 默认缓存 null 对象（2.3.9 版本以前默认不缓存 null 对象）

**J2Cache 2.3.8-release (2018-2-7)**

* 启用全新 Logo
* 增加 redis 连接时间显示
* 一级缓存数据失效时再一次清除一级缓存是为了避免缓存失效时再次从 L2 获取到值
* 默认禁用 Jedis 的 JMX 功能
* 修复了 generic 模式下 keys 方法返回的 key 包含 region 信息的问题
* 内部代码优化
* 完善使用文档

**J2Cache 2.3.7-release (2018-1-24)**

* 支持独立的 Caffeine 缓存定义文件 caffeine.properties

**J2Cache 2.3.6-release (2018-1-24)**

* 同时兼容 JGroups 3.6.x 和 4.0.x 版本
* 支持直接使用 J2CacheConfig 配置 J2Cache，请参考 `J2CacheBuilder` 类
* get with loader 方法支持 cacheNullObject 参数
* CacheChannel 提供直接访问底层 Redis 的接口，示例代码：
    ```
    CacheChannel channel = J2Cache.getChannel();
    RedisClient client = ((RedisCacheProvider)channel.getL2Provider()).getRedisClient();
    try {
        client.get().xxxxx(...);
    } finally {
        client.release();
    }
    ```

**J2Cache 2.3.5-release (2018-1-23)**
* [新特性] 支持缓存空对象
* [BUG] 修复了批量加载缓存后没有设置 L1 数据的问题
* [BUG] 修复了 `generic` 模式下如果没有对应的数据会导致 `clear` 方法执行失败的问题
* [BUG] 修复 `java.lang.ClassCastException: org.hibernate.cache.QueryKey cannot be cast to java.lang.String`

**J2Cache 2.3.4-release (2018-1-18)**

* 增加 regions 方法获取所有缓存中的已用缓存区域
* 整理对第三方库的依赖关系
* 删除 `DataLoader` 接口，改用 Java 8 的 Function 接口替代（传递 key 参数）
* 接口增加 `get(String region, Collection<String> keys, Function<String,Object> loader)` 方法

**J2Cache 2.3.3-release (2018-1-17)**
* 修复 `EhcacheProvider3` 中代码和注释的拼写错误
* 修复 ehcache3 没有实现 Level1Cache 接口的问题，导致无法使用 ehcache3 作为一级缓存

**J2Cache 2.3.2-release (2018-1-17)**

* 配置默认启用 testWhileIdle 以保持到 redis 的网络连接
* `RedisPubSubClusterPolicy` 和 `RedisCacheProvider` 独立两个连接池
* [增强] 改善 redis 连接的可靠性，包括被 redis 服务器强行断开连接的处理
* 继续优化同时获取多个缓存数据的方法
* 增加 `get(String region, String key, DataLoader loader)` 方法用于从外部获取需要缓存的数据  
  示例代码：  
  ```
  CacheChannel cache = J2Cache.getChannel();
  CacheObject obj = cache.get("Users", "13", () -> "Hello J2Cache");
  System.out.println(obj);
  //[Users,13,L3]=>Hello J2Cache
  ```

**J2Cache 2.3.1-release (2018-1-16)**
* 重构：`RedisPubSubClusterPolicy` 不再依赖 `RedisCacheProvider` 的 `RedisClient` 实例
* 增加单独的 Redis PubSub 服务器配置项 `redis.channel.host` 和 `redis.channel.timeout` , redis 数据存储和订阅服务可以分开
* 优化 `get(Collection<String> keys)` 使用 Redis 的 MGET 操作符
* 优化 evict 多个 key 的操作

**J2Cache 2.3.0-release (2018-1-15)**
* 删除缓存读取方法 getXxx 只保留 get 方法（自动识别不同类型数据），可通过 `CacheObject` 的 asXxx 方法来获取不同类型数据
* 删除 incr 和 decr 方法

**J2Cache 2.3.0-beta (2018-1-15)**
* 内部结构的重构，减少模块间的耦合，简化内部接口
* 去掉对 `commons-beanutils` 的依赖，由于 beanutils 1.8 和 1.9 版本 api 变化大，此举可避免对宿主系统的影响
* 修复了 RedisGenericCache 中 clear 方法的逻辑错误[BUG]

**J2Cache 2.2.4-release (2018-1-14)**
* 修复了配置中设置 Caffeine 缓存有效期时，单位无效的问题

**J2Cache 2.2.3-release (2018-1-10)**
* 修复字符串数据的处理问题

**J2Cache 2.2.2-release (2018-1-10)**
* 修复使用 `redis` 的订阅广播方式没有释放 `redis` 连接的问题（严重）
* `sharded` 模式下的 `Redis` 启用连接池
* `redis.mode` 的配置如果无效默认使用 `single` 模式，而不是抛出异常

**J2Cache 2.2.1-release (2018-1-10)**

* 提供 `clear` 和 `keys` 方法在 `generic` 存储方式下的非 `cluster` 模式下可用（性能可能会比较差，慎用）  
官方文档声称：`KEYS 的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，如果你需要从一个数据集中查找特定的 key ，你最好还是用 Redis 的集合结构(set)来代替。`
* 命令行测试工具支持上下键调用历史命令记录（依赖 `JLine` 库）
* 当 Redis 重启时会导致订阅线程断开连接，J2Cache 将进行自动重连
* 支持指定 jgroups 配置文件名称 (`jgroups.configXml = /network.xml`）
* 删除 setIfAbsent 方法（有点多余，二级缓存以及有一些缓存框架不支持这个方法，开发者只需自行判断即可)
* 支持自定义的 ClusterPolicy ，详情请看 j2cache.properties
* `getObject` 改名 `get` ，能自动识别缓存中的数据是字符串还是序列化对象

**J2Cache 2.2.0 beta (2018-1-6)**

注意，该版本跟以往版本在 API 上不兼容！！！  

***新特性***

* 原有的 get/getAll 方法替换成 getXxxx 方法（删除原有方法）
* 增加对 Caffeine 的支持（一级缓存）
* 支持设置缓存对象的有效期
* 支持多种 Redis 普通存储模式和哈希存储模式(`redis.storage = generic|hash`)
* 增加 incr 和 decr 方法

***Bug修复***
* 修复 redis 连接没有释放的问题（严重,必须升级）

**J2Cache 2.1 (2018-1-3)**
* 为了避免在实际应用中的混淆，缓存的key统一为字符串（如果你不能确定，请谨慎升级到2.1）
* 增加更多的缓存操作方法(getAll,setAll)
* 增加新节点加入和退出集群的日志信息
* 增加了 Spring Boot Starter 模块（感谢 @zhangsaizz 的贡献）


**J2Cache 2.0.1 (2017-12-26)**
* 修复了 database 参数无效的问题
* 统一了几种 Redis 模式下的密码认证处理
* 修复了 sharded 模式无法使用 database 和 password 参数的问题
* 给子模块定义版本号，统一父模块的版本号
* 补充和完善 Javadoc 文档

**J2Cache 2.0-release (2017-12-24)**

* 增加对 Ehcache 3.x 的支持 `j2cache.L1.provider_class = ehcache3`
* 合并 1.x 中的 hibernate3 和 hibernate4 支持模块
* J2Cache 命令行工具改名 J2CacheCmd

**J2Cache 2.0-beta (2017-12-22)**

* 要求 Java 8 支持
* 全 Maven 模块化，去掉老版本的 Ant 支持
* 重构内部的各个接口，更加清晰直观，减少依赖关系
* 支持多种 Redis 单机和集群模式，并启用 Redis 连接池
* 支持带密码认证的 Redis 服务
* 支持 Ehcache 3.x (`j2cache.L1.provider_class = ehcache3`)
* 启用线程方式发送缓存失效的广播通知，避免网络问题导致的堵塞
* [重要] 尽管接口变化不大，但是 J2Cache 2.0 的接口跟 1.x 不兼容
* 对 Hibernate 以及其他框架的支持将在后期通过模块的方式引入项目中

**J2Cache 1.4.0 ()**

**J2Cache 1.3.0 (2015-11-5)**

* 支持使用 Redis 发布订阅机制实现缓存更新通知，用于替换 JGroups 组播方式，两种方式可在 j2cache.properties 中进行配置切换 (感谢 @flyfox 330627517@qq.com)
* 对 J2Cache 的调用进行重构，无法直接从老版本升级，需要更改调用方式为 J2Cache.getChannel()

**J2Cache 1.2.0 (2015-10-27)**

* 升级 jedis 和 jgroups 到最新版本
* 使用 Maven 模块对项目结构进行重新整理