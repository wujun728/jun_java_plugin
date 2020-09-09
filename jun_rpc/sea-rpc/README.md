# sea-rpc
## 模块
- rpc-core:
  该模块是rpc目前的所有代码
- rpc-demo
  该模块是demo,包括server和client的代码

## 如何运行demo
修改rpc-demo位于src/main/resources下的配置文件,配置文件说明如下：
-     netty_server.properties 里面配置了Netty Server的启动端口号
-     zk_servers.properties 里面配置了zookeeper的服务器列表
-     log4j.properties

编译父工程sea-rpc(连带编译子模块rpc-core和rpc-demo)。  
    将rpc-demo运行于tomcat下，出现以下log则说明Netty Server正常启动：Netty Server started at 192.168.1.104:9090 in 323 ms。 (其中192.168.1.104是本机Netty server的启动ip,9090是Netty server的启动端口)  
运行客户端测试程序：com.github.ghsea.rpc.demo.client.HelloTest  

## 关于demo代码的说明
- 服务端

```
	<bean id="serverBootstrap" class="com.github.ghsea.rpc.server.RpcServerBootstrap"></bean>

<bean id="helloImpl" class="com.github.ghsea.rpc.demo.server.service.impl.HelloImpl" />

<sea-rpc:provider id="hello"
		interface="com.github.ghsea.rpc.server.test.Hello" ref="helloImpl"
		pool="groupon-data" version="1.0-ghsea" />
```
以上serverBootstrap是必须配置的，它相当于Netty server的启动类  
sea-rpc:provider id="hello"将helloImpl暴露为一个远程服务

- 客户端

```
<sea-rpc:client id="helloClient" targetService="hello"
		targetInterface="com.github.ghsea.rpc.server.test.Hello" poolName="groupon-data"
		groupName="mobile" version="1.0-ghsea" timeoutMs="20000" retryTimes="5"
		callType="SYNC" />
```
targetService引用了远程服务，它必须与服务端的provider id相一致。  
targetInterface是远程服务的接口类型。  
剩下的几个配置定义了客户端的一些行为:
- poolName与服务端的pool相对应
- version与服务端的version相对应
- timeoutMs定义了单次请求的超时时间
- retryTimes定义了客户端的重试次数(目前还未实现)
- callType定义了客户端的调用类型：同步(SYNC),异步(ASYNC)  
以上配置的xsd参照rpc-core模块的 com/github/ghsea/rpc/common/spring/sea-rpc.xsd