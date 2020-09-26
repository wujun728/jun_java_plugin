# jun_distributed_lock，基于redis实现的分布式锁，同时实现了jdk lock接口，方便与已有项目无缝接入<br>
 
 底层实现redis、zookeeper、MQ、数据库表（也可以支持）、以及自定义实现

> ```java
Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
hostAndPortSet.add(new HostAndPort("127.0.0.1", 6379));
final FastLockManager manager = new JedisFastLockManager(hostAndPortSet);
//兼容jdk Lock接口，使用方便，无缝接入
Lock lock = manager.getLock("testKey");

