## 什么是服务发现

微服务的框架体系中，服务发现是不能不提的一个模块。我相信了解或者熟悉微服务的童鞋应该都知道它的重要性。这里我只是简单的提一下，毕竟这不是我们的重点。我们看下面的一幅图片：

![Image.png](http://upload-images.jianshu.io/upload_images/4742055-873b31b3280ccd57.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

图中，客户端的一个接口，需要调用服务A-N。客户端必须要知道所有服务的网络位置的，以往的做法是配置是配置文件中，或者有些配置在数据库中。这里就带出几个问题：

* 需要配置N个服务的网络位置，加大配置的复杂性
* 服务的网络位置变化，都需要改变每个调用者的配置
* 集群的情况下，难以做负载（反向代理的方式除外）

**总结起来一句话：服务多了，配置很麻烦，问题多多**

既然有这些问题，那么服务发现就是解决这些问题的。话说，怎么解决呢？我们再看一张图


![Image.png](http://upload-images.jianshu.io/upload_images/4742055-b5c590e819912447.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


与之前一张不同的是，加了个服务发现模块。图比较简单，这边文字描述下。服务A-N把当前自己的网络位置注册到服务发现模块（这里注册的意思就是告诉），服务发现就以K-V的方式记录下，K一般是服务名，V就是IP:PORT。服务发现模块定时的轮询查看这些服务能不能访问的了（这就是健康检查）。客户端在调用服务A-N的时候，就跑去服务发现模块问下它们的网络位置，然后再调用它们的服务。这样的方式是不是就可以解决上面的问题了呢？客户端完全不需要记录这些服务网络位置，客户端和服务端完全解耦！

> 这个过程大体是这样，当然服务发现模块没这么简单。里面包含的东西还很多。这样表述只是方便理解。

图中的服务发现模块基本上就是微服务架构中服务发现的作用了。

## consul 简介

做服务发现的框架常用的有

* zookeeper
* eureka
* etcd
* consul

这里就不比较哪个好哪个差了，需要的童鞋自己谷歌百度。

那么consul是啥？consul就是提供服务发现的工具。然后下面是简单的介绍：

consul是分布式的、高可用、横向扩展的。consul提供的一些关键特性：

* service discovery：consul通过DNS或者HTTP接口使服务注册和服务发现变的很容易，一些外部服务，例如saas提供的也可以一样注册。
* health checking：健康检测使consul可以快速的告警在集群中的操作。和服务发现的集成，可以防止服务转发到故障的服务上面。
* key/value storage：一个用来存储动态配置的系统。提供简单的HTTP接口，可以在任何地方操作。
* multi-datacenter：无需复杂的配置，即可支持任意数量的区域。

我们这里会介绍服务发现，健康检查，还有一些基本KV存储。多数据中心有机会另一篇文章再说。

**总结：只要知道它是解决我上一部分提出的问题就行，其它的东西慢慢理解**

### consul的几个概念

![Image.png](https://www.consul.io/assets/images/consul-arch-420ce04a.png)

上图是我从[consul官方文档](https://www.consul.io/docs/internals/architecture.html "文档")抠出来的。

我们只看数据中心1，可以看出consul的集群是由N个SERVER，加上M个CLIENT组成的。而不管是SERVER还是CLIENT，都是consul的一个**节点**，所有的服务都可以注册到这些节点上，正是通过这些节点实现服务注册信息的共享。除了这两个，还有一些小细节，一一简单介绍。

* #### CLIENT

CLIENT表示consul的client模式，就是客户端模式。是consul节点的一种模式，这种模式下，所有注册到当前节点的服务会被转发到SERVER，本身是**不持久化**这些信息。

* #### SERVER

SERVER表示consul的server模式，表明这个consul是个server，这种模式下，功能和CLIENT都一样，唯一不同的是，它会把所有的信息持久化的本地，这样遇到故障，信息是可以被保留的。

* #### SERVER-LEADER

中间那个SERVER下面有LEADER的字眼，表明这个SERVER是它们的老大，它和其它SERVER不一样的一点是，它需要负责同步注册的信息给其它的SERVER，同时也要负责各个节点的健康监测。

* #### 其它信息

其它信息包括它们之间的通信方式，还有一些协议信息，算法。它们是用于保证节点之间的数据同步，实时性要求等等一系列集群问题的解决。这些有兴趣的自己看看[官方文档](https://www.consul.io/docs/internals/index.html "文档")。

## consul 基本使用

> 自己就一台机子，所以这里就演示下docker下部署使用consul。容器与宿主机的端口映射忽略，正常生产环境每个宿主机一个consul，端口需要映射到宿主机

### 部署

#### 拉取镜像

        docker search consul

咱们用官方的镜像玩玩

        docker pull consul

> 不指定tag就拉取last，当前版本是0.8.0

#### 启动consul

* ##### 启动节点1（server模式）

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' --name=node1 consul agent -server -bind=172.17.0.2  -bootstrap-expect=3 -node=node1
> -node：节点的名称
-bind：绑定的一个地址，用于节点之间通信的地址，可以是内外网，必须是可以访问到的地址
-server：这个就是表示这个节点是个SERVER
-bootstrap-expect：这个就是表示期望提供的SERVER节点数目，数目一达到，它就会被激活，然后就是LEADER了

* ##### 启动节点2-3（server模式）

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' --name=node2 consul agent -server -bind=172.17.0.3  -join=172.17.0.2 -node-id=$(uuidgen | awk '{print tolower($0)}')  -node=node2

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' --name=node3 consul agent -server -bind=172.17.0.4  -join=172.17.0.2 -node-id=$(uuidgen | awk '{print tolower($0)}')  -node=node3 -client=172.17.0.4
>  -join：这个表示启动的时候，要加入到哪个集群内，这里就是说要加入到节点1的集群
-node-id：这个貌似版本8才加入的，这里用这个来指定唯一的节点ID，可以查看这个[issue](https://github.com/hashicorp/consul/issues/2877 "issue")
 -client：这个表示注册或者查询等一系列客户端对它操作的IP，如果不指定这个IP，默认是127.0.0.1。

* ##### 启动节点4（client模式）

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"leave_on_terminate": true}' --name=node4 consul agent -bind=172.17.0.5 -retry-join=172.17.0.2 -node-id=$(uuidgen | awk '{print tolower($0)}')  -node=node4
> 除了没有**-server**，其它都是一样的，没有这个就说明这个节点是CLIENT

* ##### 查看下集群的状态

        docker exec -t node1 consul members
![Image.png](http://upload-images.jianshu.io/upload_images/4742055-d2660468207664d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4个节点都列出来了。Status表示它们的状态，都是alive。Type表示它们的类型，三个SERVER一个CLIENT，和我们之前启动的一样。DC表示数据中心，都是dc1。

* #### 节点异常consul的处理
 - LEADER 挂了
 leader挂了，consul会重新选取出新的leader，只要超过一半的SERVER还活着，集群是可以正常工作的。node1是leader，所以把这个容器停了。

        docker stop node1
    看看其他节点的日志（node2）：
     ![Image.png](http://upload-images.jianshu.io/upload_images/4742055-bc8b184110e5a599.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
日志打印，心跳检查node1的ip超时，接着开始选举。node2被选举为新的leader。我们查看下现在的leader：

         curl http://172.17.0.4:8500/v1/status/leader
返回的内容：

          "172.17.0.3:8300"
> 172.17.0.3 就是 node2节点的IP

### （补充内容）节点与宿主机的端口映射

指定node1与宿主机（192.168.99.100）的端口映射

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' --name=node1 -p 8300:8300 -p 8301:8301 -p 8301:8301/udp  -p 8302:8302/udp -p 8302:8302 -p 8400:8400 -p 8500:8500 -p 53:53/udp -h consul  consul agent -server -bind=172.17.0.2  -bootstrap -node=node1  

node2进入的IP指定为宿主机的IP

        docker run -d -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' --name=node2 consul agent -server -bind=172.17.0.3  -join=192.168.99.100 -node-id=$(uuidgen | awk '{print tolower($0)}')  -node=node2

正常加入，node1的日志：

```
    2017/04/09 05:53:08 [INFO] serf: EventMemberJoin: node2 172.17.0.3
    2017/04/09 05:53:08 [INFO] consul: Adding LAN server node2 (Addr: tcp/172.17.0.3:8300) (DC: dc1)
    2017/04/09 05:53:08 [INFO] raft: Updating configuration with AddStaging (172.17.0.3:8300, 172.17.0.3:8300) to [{Suffrage:Voter ID:172.17.0.2:8300 Address:172.17.0.2:8300} {Suffrage:Voter ID:172.17.0.3:8300 Address:172.17.0.3:8300}]
    2017/04/09 05:53:08 [INFO] raft: Added peer 172.17.0.3:8300, starting replication
    2017/04/09 05:53:08 [INFO] consul: member 'node2' joined, marking health alive
    2017/04/09 05:53:08 [INFO] serf: EventMemberJoin: node2.dc1 172.17.0.3
    2017/04/09 05:53:08 [INFO] consul: Handled member-join event for server "node2.dc1" in area "wan"
    2017/04/09 05:53:08 [WARN] raft: AppendEntries to {Voter 172.17.0.3:8300 172.17.0.3:8300} rejected, sending older logs (next: 1)
    2017/04/09 05:53:08 [INFO] raft: pipelining replication to peer {Voter 172.17.0.3:8300 172.17.0.3:8300}

```

```
Node   Address          Status  Type    Build  Protocol  DC
node1  172.17.0.2:8301  alive   server  0.8.0  2         dc1
node2  172.17.0.3:8301  alive   server  0.8.0  2         dc1
```

### 使用

部署完了，那么可以看看怎么用这个东东了。

#### 注册个服务
使用HTTP API 注册个服务，使用[接口API](https://www.consul.io/api/agent/service.html API)调用

调用 http://consul:8500/v1/agent/service/register PUT 注册一个服务。request body:

```
{
  "ID": "userServiceId", //服务id
  "Name": "userService", //服务名
  "Tags": [              //服务的tag，自定义，可以根据这个tag来区分同一个服务名的服务
    "primary",
    "v1"
  ],
  "Address": "127.0.0.1",//服务注册到consul的IP，服务发现，发现的就是这个IP
  "Port": 8000,          //服务注册consul的PORT，发现的就是这个PORT
  "EnableTagOverride": false,
  "Check": {             //健康检查部分
    "DeregisterCriticalServiceAfter": "90m",
    "HTTP": "http://www.baidu.com", //指定健康检查的URL，调用后只要返回20X，consul都认为是健康的
    "Interval": "10s"   //健康检查间隔时间，每隔10s，调用一次上面的URL
  }
}
```
使用curl调用

 ```
curl http://172.17.0.4:8500/v1/agent/service/register -X PUT -i -H "Content-Type:application/json" -d '{
  "ID": "userServiceId",  
  "Name": "userService",
  "Tags": [
    "primary",
    "v1"
  ],
  "Address": "127.0.0.1",
  "Port": 8000,
  "EnableTagOverride": false,
  "Check": {
    "DeregisterCriticalServiceAfter": "90m",
    "HTTP": "http://www.baidu.com",
    "Interval": "10s"
  }
}'
```
OK，注册了一个服务

#### 发现个服务

刚刚注册了名为userService的服务，我们现在发现（查询）下这个服务

    curl http://172.17.0.4:8500/v1/catalog/service/userService
返回的响应：
```
[
    {
        "Address": "172.17.0.4",
        "CreateIndex": 880,
        "ID": "e6e9a8cb-c47e-4be9-b13e-a24a1582e825",
        "ModifyIndex": 880,
        "Node": "node3",
        "NodeMeta": {},
        "ServiceAddress": "127.0.0.1",
        "ServiceEnableTagOverride": false,
        "ServiceID": "userServiceId",
        "ServiceName": "userService",
        "ServicePort": 8000,
        "ServiceTags": [
            "primary",
            "v1"
        ],
        "TaggedAddresses": {
            "lan": "172.17.0.4",
            "wan": "172.17.0.4"
        }
    }
]
```
内容有了吧，这个就是我们刚刚注册的服务的信息，就可以获取到
> 服务的名称是“userService”
服务地址是“127.0.0.1”
服务的端口是“8000”

#### 存储个K/V

设置一个值到user/config/connections 内容为5

    docker exec -t node1 consul kv put user/config/connections 5

获取特定的值

    docker exec -t node1 consul kv get -detailed user/config/connections

![Image.png](http://upload-images.jianshu.io/upload_images/4742055-9a89920e127b568f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

值的内容为5,还有key等相关的值

## 总结

服务发现以及配置共享的简单样例展示了下，详细的使用还是需要看官方文档，这里只是列举了一些样例，用于理解和简单的使用consul。

## Spring Cloud 结合consul使用

如果是使用spring cloud来使用consul，可以查看我的相关样例：http://git.oschina.net/buxiaoxia/spring-demo 

spring cloud 结合consul的使用，下一篇文章再进行描述吧

## 相关文档连接
CONSUL:https://www.consul.io/
CONSUL HTTP API:https://www.consul.io/api/index.html
CONSUL CLI:https://www.consul.io/docs/commands/info.html
CONSUL Health Checks:https://www.consul.io/docs/agent/checks.html