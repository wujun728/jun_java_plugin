# fastlock
基于jdk lock接口实现的分布式锁<br>
方便与已有项目无缝接入<br>
底层实现可以使用redis（已支持）、zookeeper（即将支持）以及自定义实现

```java
Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
hostAndPortSet.add(new HostAndPort("127.0.0.1", 6379));
final FastLockManager manager = new JedisFastLockManager(hostAndPortSet);
//兼容jdk Lock接口，使用方便，无缝接入
Lock lock = manager.getLock("testKey");
```
