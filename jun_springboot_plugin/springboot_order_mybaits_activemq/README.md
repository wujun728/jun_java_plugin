#springboot-order-mybaits-activeMQ

项目主框架采用springboot，介绍Zookeeper集群安装和ActiveMQ的高可用集群安装方式，以及基于ActiveMQ简单的消息队列应用场景！

### 一、虚拟机安装

```
开启一个虚拟机，虚拟机安装非本文范畴，可查阅相关资料。
在安装Zookeeper,ActiveMQ之前需要先将虚拟机防火墙关闭，并配置好网络。
虚拟机IP设置为：10.16.70.134，此处可根据自己网络环境自行设置。
需要保证虚拟机可连接外网，以方便执行wget,yum等命令安装相关软件。
需要保证虚拟机中已安装JDK1.8，并且已配置好环境变量，JDK安装非本文范畴，可查阅相关资料。
```

### 二、Zookeeper 集群搭建
##### 下载并上传zookeeper-3.4.6.tar.gz到各个服务器的/usr/local/目录,或者使用wget命令，如下：

```
cd /usr/local/
wget http://apache.fayea.com/zookeeper/zookeeper-3.4.6/zookeeper-3.4.6.tar.gz
tar -zxvf zookeeper-3.4.6.tar.gz
```

##### 在各个服务器上解压zookeeper安装包，并按节点号对zookeeper目录重命名

```
# 服务器1
cp -r zookeeper-3.4.6 zookeeper-3.4.6_1
# 服务器2
cp -r zookeeper-3.4.6 zookeeper-3.4.6_2
# 服务器3
cp -r zookeeper-3.4.6 zookeeper-3.4.6_3
```

##### 在各zookeeper节点目录下创建以下目录

```
cd /usr/local/zookeeper-3.4.6_1
mkdir data
mkdir logs
cd /usr/local/zookeeper-3.4.6_2
mkdir data
mkdir logs
cd /usr/local/zookeeper-3.4.6_3
mkdir data
mkdir logs
```

##### 将各节点zookeeper/zookeeper-3.4.6_x/conf目录下的zoo_sample.cfg文件拷贝一份，命名为zoo.cfg:

```
cd /usr/local/zookeeper-3.4.6_1/conf
cp zoo_sample.cfg zoo.cfg
cd /usr/local/zookeeper-3.4.6_2/conf
cp zoo_sample.cfg zoo.cfg
cd /usr/local/zookeeper-3.4.6_3/conf
cp zoo_sample.cfg zoo.cfg
```

##### 修改 zoo.cfg 配置文件

```
cd /usr/local/zookeeper-3.4.6_1/conf
# 设置内容如下：
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/local/zookeeper-3.4.6_1/data
dataLogDir=/usr/local/zookeeper-3.4.6_1/logs
clientPort=2181
server.1=10.16.70.134:2881:3881
server.2=10.16.70.134:2882:3882
server.3=10.16.70.134:2883:3883
# -----------------------------------
cd /usr/local/zookeeper-3.4.6_2/conf
vi zoo.cfg
# 设置内容如下：
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/local/zookeeper-3.4.6_2/data
dataLogDir=/usr/local/zookeeper-3.4.6_2/logs
clientPort=2182
server.1=10.16.70.134:2881:3881
server.2=10.16.70.134:2882:3882
server.3=10.16.70.134:2883:3883
# -----------------------------------
cd /usr/local/zookeeper-3.4.6_3/conf
vi zoo.cfg
# 设置内容如下：
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/local/zookeeper-3.4.6_3/data
dataLogDir=/usr/local/zookeeper-3.4.6_3/logs
clientPort=2183
server.1=10.16.70.134:2881:3881
server.2=10.16.70.134:2882:3882
server.3=10.16.70.134:2883:3883
```

##### 在dataDir=/usr/local/zookeeper-3.4.6_x/data下创建 myid 文件

```
vi /usr/local/zookeeper-3.4.6_1/data/myid #设置值为1
vi /usr/local/zookeeper-3.4.6_2/data/myid #设置值为2
vi /usr/local/zookeeper-3.4.6_3/data/myid #设置值为3
```

##### 启动并查看zookeeper:

```
cd /usr/local
/usr/local/zookeeper-3.4.6_1/bin/zkServer.sh start
/usr/local/zookeeper-3.4.6_2/bin/zkServer.sh start
/usr/local/zookeeper-3.4.6_3/bin/zkServer.sh start
```

##### 验证zookeeper启动情况

```
/usr/local/zookeeper-3.4.6_1/bin/zkServer.sh status
/usr/local/zookeeper-3.4.6_2/bin/zkServer.sh status
/usr/local/zookeeper-3.4.6_3/bin/zkServer.sh status
```

* 如果启动成功，三台机器结果类似如下,标红部分为一个leader,两个follower：

```
[root@centos7 local]# /usr/local/zookeeper-3.4.6_1/bin/zkServer.sh status
JMX enabled by default
Using config: /usr/local/zookeeper-3.4.6_1/bin/../conf/zoo.cfg
Mode: "follower"
[root@centos7 local]# /usr/local/zookeeper-3.4.6_2/bin/zkServer.sh status
JMX enabled by default
Using config: /usr/local/zookeeper-3.4.6_2/bin/../conf/zoo.cfg
Mode: "leader"
[root@centos7 local]# /usr/local/zookeeper-3.4.6_3/bin/zkServer.sh status
JMX enabled by default
Using config: /usr/local/zookeeper-3.4.6_3/bin/../conf/zoo.cfg
Mode: "follower"
```

* zookeeper集群搭建成功

### 三、ActiveMQ集群集群搭建

##### 下载并上传apache-activemq-5.9.0-bin.tar.gz到各个服务器的/usr/local/目录，或者使用wget命令，如下：

```
cd /usr/local/
wget wget http://archive.apache.org/dist/activemq/apache-activemq/5.9.0/apache-activemq-5.9.0-bin.tar.gz
tar -zxvf apache-activemq-5.9.0-bin.tar.gz
```

##### 在各个服务器上解压ActiveMQ安装包，并按节点号对ActiveMQ目录重命名

```
# 服务器1
cp -r apache-activemq-5.9.0 activemq-5.9.0_1
# 服务器2
cp -r apache-activemq-5.9.0 activemq-5.9.0_2
# 服务器3
cp -r apache-activemq-5.9.0 activemq-5.9.0_3
```

#### 在3个ActiveMQ节点中配置conf/jetty.xml中的监控端口

```
# 节点1
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
        <property name="port" value="8161"/>
</bean>
# 节点2
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
        <property name="port" value="8162"/>
</bean>
# 节点3
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
        <property name="port" value="8163"/>
</bean>
```

#### broker-name的统一

```
# 将broker标签的brokerName属性设置为统一的值，我将这个值设置为“order”，只有三个实例的brokerName一致，zookeeper才能识别它们属于同一个集群。
```

#### persistenceAdapter的配置
* persistenceAdapter设置持久化方式，主要有三种方式：kahaDB（默认方式）、数据库持久化、levelDB（v5.9.0提供支持）。
* 本文采用levelDB来进行持久化，并使用zookeeper实现集群的高可用，配置如下：

```
# 首先注释掉原来kahaDB的持久化方式，然后配置levelDB+zookeeper的持久化方式
# 节点1
<persistenceAdapter>
  <replicatedLevelDB
    directory="${activemq.data}/leveldb"
    replicas="3"
    bind="tcp://0.0.0.0:0"
    zkAddress="localhost:2181,localhost:2182,localhost:2183"
    hostname="10.16.70.134"
    zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
# 节点2
<persistenceAdapter>
  <replicatedLevelDB
    directory="${activemq.data}/leveldb"
    replicas="3"
    bind="tcp://0.0.0.0:0"
    zkAddress="localhost:2181,localhost:2182,localhost:2183"
    hostname="10.16.70.134"
    zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
# 节点3
<persistenceAdapter>
  <replicatedLevelDB
    directory="${activemq.data}/leveldb"
    replicas="3"
    bind="tcp://0.0.0.0:0"
    zkAddress="localhost:2181,localhost:2182,localhost:2183"
    hostname="10.16.70.134"
    zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```

#### 修改各节点的消息端口

```
节点1：
<transportConnector name="openwire" uri="tcp://10.16.70.134:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
节点2：
<transportConnector name="openwire" uri="tcp://10.16.70.134:61617?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
节点3：
<transportConnector name="openwire" uri="tcp://10.16.70.134:61618?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>

```

#### 按顺序启动 3 个 ActiveMQ 节点：

```
节点1：
/usr/local/activemq-5.9.0_1/bin/activemq start
节点2：
/usr/local/activemq-5.9.0_2/bin/activemq start
节点3：
/usr/local/activemq-5.9.0_3/bin/activemq start
```

#### 监听日志：

```
节点1：
tail -f /usr/local/activemq-5.9.0_1/data/activemq.log
节点2：
tail -f /usr/local/activemq-5.9.0_2/data/activemq.log
节点3：
tail -f /usr/local/activemq-5.9.0_3/data/activemq.log
```

#### 集群搭建完毕

* 说明1：本文采用的是官网中提到的三种集群方案中的<可复制的LevelDB>，其它方式请参考官网。
* 说明2：经测试，此部署方案继承了Zookeeper的特性，即需要有大于一半机器存活，则集群状态正常
* 说明3：由于ActiveMQ 的客户端只能访问Master的Broker，其他处于Slave的Broker不能访问。所以客户端连接Broker应该使用failover协议。<br>
    即：failover:(tcp://10.16.70.134:61616,tcp://10.16.70.134:61617,tcp://10.16.70.134:61618)?randomize=false
* 说明4：三个节点的管理地址：<br>
    http://10.16.70.134:8161/admin/index.jsp<br>
    http://10.16.70.134:8162/admin/index.jsp<br>
    http://10.16.70.134:8163/admin/index.jsp<br>
    因为使用zookeeper做负载均衡，三台只有一台是master，其他两台处于等待状态，所以只有其中一台提供服务，但一旦这台服务器宕机以后，会有另外一台顶替上来，所以其他几个ip地址是打不开的，只有一台能打开。

###参考

http://zookeeper.apache.org/<br>
http://activemq.apache.org/

### 期望

欢迎提出更好的意见，帮助完善我们的项目，以督促我们前行！

### 版权

[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

### 捐赠

![捐赠 springboot-order-mybaits-activeMQ](http://git.oschina.net/uploads/images/2016/1023/084255_833edeac_364262.png "支持一下springboot-order-mybaits-activeMQ")
