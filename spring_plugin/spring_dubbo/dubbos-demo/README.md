#dubbos-demo
利用[dubbos](https://git.oschina.net/qilong/dubbos.git)框架写的demo，并持续完善...
## 主要贡献者

* 齐龙 [乐影网](http://www.leying.com/) qilongjava@163.com

**讨论QQ群**： 93849809  （不限于dubbos，包括SOA设计、互联网技术等等兴趣交流）

## 当前主要功能
* **项目基础结构，提供者和消费者的注解配置
* **zookeeper作为注册中心(zookeeper://127.0.0.1:2181)
* **用dubbo协议远程调用
* **利用Kryo序列化数据
* **服务提供方利用main方法模拟启动(暂时未连接数据库,后续会实现数据库的链接)
* **消费者端为web项目，使用springMVC框架

启动后输入uri(/user/list)返回json数据便成功