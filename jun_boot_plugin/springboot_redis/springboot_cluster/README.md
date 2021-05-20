#springboot-jedisCluster

项目主框架采用springboot，介绍redis集群安装及redis3.0安装方式以及和jedisCluster结合使用的方法！

##Redis 3.0 集群搭建

### 虚拟机安装

```
开启两个虚拟机 ，分别在两个虚拟机上开启3个Redis实例，
3主3从两个虚拟机里的实例互为主备，虚拟机安装非本文范畴，可查阅相关资料。

下面分别在两个虚拟机上安装，在安装redis之前需要先将两台虚拟机防火墙关闭，并配置好网络，
两台机器IP分别设置为：10.16.70.133，10.16.70.134。此处可根据自己网络环境自行设置。
需要保证两台虚拟机均可连接外网，以方便执行yum安装相关软件。
```

### 安装ruby rubygems

```
    yum install ruby rubygems -y
```

### 安装Redis3.0.2

```
　　wget http://download.redis.io/releases/redis-3.0.2.tar.gz
　　tar xzf redis-3.0.2.tar.gz
　　cd redis-3.0.2
　　make
```
##### 在执行 wget 命令时如果碰到如下提示，需要先执行命令：yum -y install wget

```
[root@centos7 local]# wget http://download.redis.io/releases/redis-3.0.2.tar.gz
-bash: wget: command not found
```

##### 在执行 make 命令时，如果碰到如下提示，需要先执行命令：yum  install  gcc

```
cd src && make all
………略………
"/bin/sh: cc: command not found"
make[1]: *** [adlist.o] Error 127
make[1]: Leaving directory `/usr/local/redis-3.0.2/src'
make: *** [all] Error 2
```

##### 在执行 make 命令时，如果碰到如下提示，需要将 make 命令替换为： make MALLOC=libc

```
[root@centos7 redis-3.0.2]# make
cd src && make all
make[1]: Entering directory `/usr/local/redis-3.0.2/src'
    CC adlist.o
In file included from adlist.c:34:0:
zmalloc.h:50:31: fatal error: jemalloc/jemalloc.h: No such file or directory
 #include <jemalloc/jemalloc.h>
                               ^
compilation terminated.
make[1]: *** [adlist.o] Error 1
make[1]: Leaving directory `/usr/local/redis-3.0.2/src'
make: *** [all] Error 2
```

### 配置

* 在两个机器上分别建立 6379 6380 6381 文件夹

```
　　[root@centos7 redis-3.0.2]# mkdir 6379 6380 6381
　　[root@centos7 redis-3.0.2]# cp redis.conf 6379
　　[root@centos7 redis-3.0.2]# cp redis.conf 6380
　　[root@centos7 redis-3.0.2]# cp redis.conf 6381
```

### 分别修改位于文件夹6379，6380，6381下的Redis配置文件

```
    port 6379
    pidfile redis-6379.pid
    dbfilename dump-6379.rdb
    appendfilename "appendonly-6379.aof"
    cluster-config-file nodes-6379.conf
    cluster-enabled yes
    cluster-node-timeout 5000
    appendonly yes
```

### 分别启动两个机器的Redis实例，更换端口，命令共在两台机器上执行6次

```
　　[root@centos7 src]# ./redis-server ../6379/redis.conf
```

### 启动集群　

```
　　[root@centos7 src]# ./redis-trib.rb create --replicas 1 10.16.70.133:6379 10.16.70.133:6380 10.16.70.133:6381
 10.16.70.134:6379 10.16.70.134:6380 10.16.70.134:6381
    　>>> Creating cluster
      Connecting to node 10.16.70.133:6379: OK
      Connecting to node 10.16.70.133:6380: OK
      Connecting to node 10.16.70.133:6381: OK
      Connecting to node 10.16.70.134:6379: OK
      Connecting to node 10.16.70.134:6380: OK
      Connecting to node 10.16.70.134:6381: OK
      >>> Performing hash slots allocation on 6 nodes...
      Using 3 masters:
      10.16.70.133:6379
      10.16.70.134:6379
      10.16.70.133:6380
      Adding replica 10.16.70.134:6380 to 10.16.70.133:6379
      Adding replica 10.16.70.133:6381 to 10.16.70.134:6379
      Adding replica 10.16.70.134:6381 to 10.16.70.133:6380
      M: 11dcaf921094fe7d000e6b684072f50fe860ba62 10.16.70.133:6379
         slots:0-5460 (5461 slots) master
      M: f8767f679c53a949cbc65a1657a41617e4079bde 10.16.70.133:6380
         slots:10923-16383 (5461 slots) master
      S: 0b0c95e7c8f6e1f366a8cc265b2ca39a5b56f4fe 10.16.70.133:6381
         replicates 52cf14a1919a9bf280aee2d0bfacd7811778bac8
      M: 52cf14a1919a9bf280aee2d0bfacd7811778bac8 10.16.70.134:6379
         slots:5461-10922 (5462 slots) master
      S: 91273dcb163ddf54e6e0a50beac84ee034a9d92d 10.16.70.134:6380
         replicates 11dcaf921094fe7d000e6b684072f50fe860ba62
      S: 708710dd3926a5f61b65de2797385c3ac06c716a 10.16.70.134:6381
         replicates f8767f679c53a949cbc65a1657a41617e4079bde
      Can I set the above configuration? (type 'yes' to accept): yes
      >>> Nodes configuration updated
      >>> Assign a different config epoch to each node
      >>> Sending CLUSTER MEET messages to join the cluster
      Waiting for the cluster to join..
      >>> Performing Cluster Check (using node 10.16.70.133:6379)
      M: 11dcaf921094fe7d000e6b684072f50fe860ba62 10.16.70.133:6379
         slots:0-5460 (5461 slots) master
      M: f8767f679c53a949cbc65a1657a41617e4079bde 10.16.70.133:6380
         slots:10923-16383 (5461 slots) master
      M: 0b0c95e7c8f6e1f366a8cc265b2ca39a5b56f4fe 10.16.70.133:6381
         slots: (0 slots) master
         replicates 52cf14a1919a9bf280aee2d0bfacd7811778bac8
      M: 52cf14a1919a9bf280aee2d0bfacd7811778bac8 10.16.70.134:6379
         slots:5461-10922 (5462 slots) master
      M: 91273dcb163ddf54e6e0a50beac84ee034a9d92d 10.16.70.134:6380
         slots: (0 slots) master
         replicates 11dcaf921094fe7d000e6b684072f50fe860ba62
      M: 708710dd3926a5f61b65de2797385c3ac06c716a 10.16.70.134:6381
         slots: (0 slots) master
         replicates f8767f679c53a949cbc65a1657a41617e4079bde
      [OK] All nodes agree about slots configuration.
      >>> Check for open slots...
      >>> Check slots coverage...
      [OK] All 16384 slots covered.
　　[root@centos7 src]#
```
##### 启动集群时，如果碰到如下提示，需要先执行命令 gem install redis --version 3.0.0

```
[root@centos7 src]# ./redis-trib.rb create --replicas 1 10.16.70.133:6379 10.16.70.133:6380 10.16.70.133:6381
 10.16.70.134:6379 10.16.70.134:6380 10.16.70.134:6381
 /usr/share/rubygems/rubygems/core_ext/kernel_require.rb:55:in `require': cannot load such file -- redis (LoadError)
	from /usr/share/rubygems/rubygems/core_ext/kernel_require.rb:55:in `require'
	from ./redis-trib.rb:25:in `<main>'
```

###参考

http://redisdoc.com/index.html

### 期望

欢迎提出更好的意见，帮助完善我们的项目，以督促我们前行！

### 版权

[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

### 捐赠

![捐赠 springboot-jedisCluster](http://git.oschina.net/uploads/images/2016/1023/084255_833edeac_364262.png "支持一下springboot-jedisCluster")
