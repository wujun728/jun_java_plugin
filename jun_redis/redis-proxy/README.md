###  **快速开始** 


 **1** .https://git.oschina.net/zhanggaofeng/redis-proxy.git 下载项目 


  **2** .修改redis-proxy-server项目下的配置文件
文件disconf.properties内容
disconf.enable.remote.conf=false

文件zk.properties内容
zk.hosts=127.0.0.1:2181,127.0.0.1:2181,127.0.0.1:2181

文件redis.properties内容
通用redis配置
redis.maxWaitMillis=60000
心跳检查默认开启间隔必须大于5秒(低于该值按5秒)
redis.timeBetweenEvictionRunsMillis=5000
redis.minEvictableIdleTimeMillis=300000
cluster方式集群使用
redis.cluster.soTimeout=10000
redis.cluster.maxAttempts=2
redis.cluster.connectionTimeout=10000
modules=module1,module2
shard 格式  redis://:pwd@localhost:8080/db
module.module1.shards=redis://127.0.0.1:6379,redis://localhost:6384
cluster 方式(如果需要密码只需在一个地址中配置即可) redis://:pwd@localhost:8080
module.module2.clusters=redis://localhost:6371,redis://localhost:6372,redis://localhost:6373,redis://localhost:6374,redis://localhost:6375,redis://localhost:6376


 **3** .redis key 必须以module名字开头(格式:module1_*;不同的module挂载着不同的redis集群，否则会提示找不到写入redis目标),根据module选择集群


 **4** .启动项目 打包 修改redis-proxy父项目pom.xml的打包路径(project.build.target)，会在指定的路径下生成启动项目文件夹(比如redis-proxy-server文件夹)，进入内部有启动脚本，可以选择指定启动参数(修改启动脚本) -group(同一个zk下的代理服务分组 default:default) -min_threads(最小并发操作数 default:50) -max_threads(最大并发操作数 default:200)

 **5** .使用redis-cli.sh -p 6701 or redis-clie.exe -p 6701 连接，并使用以module1_或module2_开头的key操作即可(初始端口为6701如果本端口已经被使用会加+1重试)




###  **项目详情** 

 **1** .redis-proxy-monitor监控系统有待开发(希望开发者积极参与开发)

 **2** .本代理服务支持redis绝大部分redis命令(有需要的话，可以添加命令支持，非常简单 redis命令大全:http://doc.redisfans.com )及管道;不支持事物和发布订阅

 **3** .本服务提供一个客户端应用接入模块(内部封装jedis实现负载均衡及容灾),也可以直接使用shardedjedis和jedis或redis-cli直接连接(本服务实现redis协议进行消息传输)

 **4** .redis代理服务支持redis-cluster和redis-sharded方式进行集群 并对shardedjedis的集群方式进行二次封装及开发：
 * 解决：shardedJedis当一个redis分片服务不可用时导致所有redis分片服务不可用的问题
 * 效果：当一个redis分片服务不可用时，往该分片服务写和读数据失败异常保证其他节点正常读写，心跳不断尝试重新连接直到恢复（redis配置参数必须设置心跳检测选项)
 * 缺点：服务起来时，可能造成缓存数据和数据库不一致，建议清空redis或设置短暂的过期时间
 * 新增功能：增加redis连接失效和恢复报警接口RedisAlarm
 * [本项目不支持读写分离只是支持多台redis服务hash分片，在缓存的应用的读写分离场景感觉很少而且没有必要]