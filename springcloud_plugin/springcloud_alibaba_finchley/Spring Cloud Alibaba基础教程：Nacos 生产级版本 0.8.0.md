# Spring Cloud Alibaba基础教程：Nacos 生产级版本 0.8.0

**原创**

 [2019-01-23](https://blog.didispace.com/spring-cloud-alibaba-nacos-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

昨晚Nacos社区发布了第一个生产级版本：0.8.0。由于该版本除了Bug修复之外，还提供了几个生产管理非常重要的特性，所以觉得还是有必要写一篇讲讲这次升级，在后续的文章中也都将以0.8.0版本为基础。

## 升级的理由

如Nacos官方的发布文档中描述的那样，本版本将支持非常重要的三个特性：

- **第一，用户登录**。在过去版本的Nacos中，用户是可以直接访问Nacos的页面的，我们需要通过网络或者代理手段来增加这样的安全性控制，在该版本后就不需要了。
- **第二：Prometheus的支持**。对于一个基础中间件来说，完善的监控指标输出在生产环境是必须的，通过在`/prometheus`端点上暴露监控指标，以保障Nacos集群的正常服务。
- **第三：Namespace的支持**。服务发现的功能将支持Namespace的隔离，可以方便的在一套Nacos集群下，实现多环境服务发现的隔离等。

> 发布清单可见文末参考资料。这些重要功能的具体使用，后续继续连载，敬请期待！

## 安装与使用

如果之前有看过[《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)的话，只需要将Nacos安装部分把安装包替换成 0.8.0 版本即可。

- 下载地址：https://github.com/alibaba/nacos/releases/download/0.8.0/nacos-server-0.8.0.tar.gz

下载完成之后，解压。根据不同平台，执行不同命令，启动单机版Nacos服务：

- Linux/Unix/Mac：`sh startup.sh -m standalone`
- Windows：`cmd startup.cmd -m standalone`

> `startup.sh`脚本位于Nacos解压后的bin目录下。

启动完成之后，访问：`http://127.0.0.1:8848/nacos/`，可以进入Nacos的登录页面，具体如下；

[![img](https://blog.didispace.com/images/pasted-142.png)](https://blog.didispace.com/images/pasted-142.png)

默认情况下，用户名与密码都为`nacos`，登录后进入控制台如下：

[![img](https://blog.didispace.com/images/pasted-143.png)](https://blog.didispace.com/images/pasted-143.png)

对于应用端，不需要做任何改动，就能够适配新版本。

如果还没有对接过Nacos，那么看看这篇吧：[《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)

## 参考资料

- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
- [0.8.0版本说明](https://github.com/alibaba/nacos/releases/tag/0.8.0)