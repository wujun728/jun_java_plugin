# mydb

[![Build Status](https://travis-ci.org/supermy/rocksdb-service.svg?branch=master)](https://github.com/supermy/rocksdb-service)

## 简介 
* RocksDb Service提供key/Value的微服务，构建集群提升服务能力；在大数据领域对Cube进行了扩展，通过执行 MapReduce/Spark任务构建Cube，对业务所需的维度组合和度量进行预聚合，当查询到达时直接访问预计算聚合结果，省去对大数据的扫描和运算；把Cube结果存入 mydb;
在数据集市层面取代 Redis、Hbase、Apache Kylin。

* 单节点的mydb是一个可持久化的大容量硬盘版的redis存储服务，兼容string、json 数据格式的接口;解决redis由于存储数据量巨大而导致内存不够用的容量瓶颈，可以当做一个大容量的 HashMap 来用。


## 特点
* 容量大，支持百G数据量的存储
* 在线WEB监控
* 方便的 Restfull 接口
* 方便的key 前缀检索

## 适用场景

如果你的业务场景的数据比较大，Redis 很难支撑， 比如大于50G，或者你的数据很重要，不允许断电丢失，
那么使用mydb 就可以解决你的问题。 而在实际使用中，mydb的性能大约是Redis的50%。


## 优势：

* 容量大：mydb没有Redis的内存限制, 最大使用空间等于磁盘空间的大小
* 加载db速度快：mydb 在写入的时候, 数据是落盘的, 所以即使节点挂了, 不需要rdb或者oplog，mydb 重启不用加载所有数据到内存就能恢复之前的数据, 不需要进行回放数据操作。
* 备份速度快：mydb备份的速度大致等同于cp的速度（拷贝数据文件后还有一个快照的恢复过程，会花费一些时间），这样在对于百G大库的备份是快捷的，更快的备份速度更好的解决了主从的全同步问题
* 高压缩比：mydb 存储的数据默认会被压缩，相对于 Redis，mydb 有 5~10 倍的压缩比。所以 Redis 的数据存储到 mydb，数据体积会小很多。
* 高性价比：相对于 Redis 使用昂贵的内存成本，mydb 使用磁盘存储数据，性价比极高

## 劣势：

由于mydb是基于内存和文件来存放数据, 所以性能肯定比Redis低一些, 可以使用使用SSD盘来存放数据, 尽可能跟上Redis的性能。



## 快照式备份方案

不同于Redis，mydb的数据主要存储在磁盘中，这就使得其在做数据备份时有天然的优势，可以直接通过文件拷贝实现

## mydb 是否会取代 Redis？为什么？

mydb 并不会取代 Redis，mydb 是作为 Redis 的一个补充，从上面的对比可以看出 mydb 的主要应用场景在于：

* 业务量并没有那么大，使用 Redis 内存成本太高

* 数据量很大，使用 Redis 单个服务器内存无法承载

* 经常出现时间复杂度很高的请求让 Redis 间歇性阻塞

因此对于对性能要求非常高，但是数据量非常小的场景，推荐业务使用 Redis。

同一项目或者产品mydb，Redis，Redis-cluster 均在线上使用的情况。




## 快速试用
  如果想快速试用mydb，需要 jdk8环境。

```
# 1. 解压文件
tar zxf mydbX.Y.Z_bin.tar.gz
# 2. 运行目录
cd mydbX.Y.Z
# 3. 运行mydb:
sh start.sh
```

## 编译安装

1.下载安装 jdk8,下载源文件

```
    git clone https://github.com/supermy/rocksdb-service
```

2.安装 maven，编译打包：

```
     mvn -DskipTests=true package
```

3.切换的 shell目录：

```
	 sh start.sh 运行
```
4.观察输出日志

```
	tail -f mydb.log
```



## 使用

```
    若启动失败，通过修改 application.yam，重新设置启动端口，已经数据库的保存路径

    restful 支持 json
    
    
    单数据处理
    
    put:curl -XPUT "http://127.0.0.1:9008/api/mydb/abc" -d '{"a"=12,"b"=c}'
    get:curl http://127.0.0.1:9008/api/mydb/abc
    del:curl -XDELETE "http://127.0.0.1:9008/api/mydb/abc" 
    
    批量数据处理，采用 Json 格式，采用 Flume 批量数据生产与消费。
    约定格式'{a:{"a":1},b:{b:1}}'，a,b 为主键，{"a":1}、{b:1}为数据 ;
    
    post: curl -XPOST "http://127.0.0.1:9008/api/mydb" -d  '{a:{"a":1},b:{b:1}}'
    delete:  curl "http://127.0.0.1:9008/api/mydb?pre=a"  根据前缀key批量删除数据
    get:  curl "http://127.0.0.1:9008/api/mydb?pre=a"  根据前缀key查询数据
    
    
    数据的备份与恢复
    
    查看数据状态：
        http://127.0.0.1:9000/rocksdb/status
    构造数据：
        http://127.0.0.1:9000/rocksdb/gendata
    备份数据：
        http://127.0.0.1:9000/rocksdb/backdb
    删除指定备份数据
        http://127.0.0.1:9000/rocksdb/delbackdb?backid=11
    清理备份数据，保留几分数据
        http://127.0.0.1:9000/rocksdb/purebackdb?amount=2
    恢复最新备份数据
        http://127.0.0.1:9000/rocksdb/restorbackdb
    恢复指定备份数据
        http://127.0.0.1:9000/rocksdb/restorbackdbid?backid=14

```
    
    
##  其他相关
    
```
    可以采用F6(Nginx+lua) 进行鉴权
    
    可以采用 F6(Nginx+lua) 对数据进行编码与解码
    
    可以采用 F6(Nginx+lua) 对数据进行压缩与解压
    
    注意操作系统以及网络的优化
    
```



## 性能

### 测试环境：
```
	相同配置服务端、客户机虚机各一台：
	处理器：4核 
	内存：4G
	操作系统：Debian 8.7
```
### 测试接口：
```
	
```

### 测试方法：
```
	ab -c 200 -n 8000 "http://127.0.0.1:9008/api/mydb?pre=a" 
```

### 测试结果：
<img src="https://github.com/supermy/rocksdb-service/raw/master/mydb-test.png" height = "400" width = "480" alt="1">
    


## 文档
1. [Wiki] (https://)

## 联系方式

QQ群：201108549