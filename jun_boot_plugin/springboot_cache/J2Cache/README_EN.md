## J2Cache —— Double Level Cache Framework base on Memory and Redis

J2Cache is a two-level cache framework for Java. The first level cache uses the memory cache framework and the second level cache uses Redis. Since a large number of cache reads cause the L2 network to become a bottleneck to the entire system, the goal of L1 is to reduce the number of reads to L2. This cache framework is mainly used in a clustered environment. Stand-alone can also be used to avoid memory data loss due to application restart.

We also provided Python version, Please refer to [https://gitee.com/ld/Py3Cache](https://gitee.com/ld/Py3Cache)

From 1.3.0, `J2Cache` supports two ways to notify the cache event, include `JGroups` and `Redis PubSub`. In cloud platforms may not be able to use JGroups's multicast mode, so you can choose `Redis PubSub`. For more details, please see configuration file description in `j2cache.properties`.

**J2Cache two-level cache system architecture**

L1： In-Process Memory Cache Framework(ehcache,caffeine)   
L2： Redis Server

Since a large amount of cache reads causes  network bandwidth to become a bottleneck in the overall system, the goal of L1 is to reduce the number of L2 reads

		 
### Data Flow

1. Data reading -> L1 -> L2 -> DB
2. Data writing

    1 Read latest data from the business system, updates L1 -> L2, and
    then broadcast clear event to all nodes in cluster
    2 When received the clear event, clear corresponding data in memory

### J2Cache settings

settings file located in `core/resources` directory, include:

* `ehcache.xml` for ehcache 2.x  
  `ehcache3.xml` for ehcache 3.x
* `j2cache.properties` J2Cache settings, include redis configurations, connection pool, broadcast, serialization etc.
* `network.xml` JGroups network settings, needed when setting `j2cache.broadcast = jgroups` 

Those setting files must be placed in classpath , such as `WEB-INF/classes`

### How to Test

1. Install Redis  
2. Modify `core/resource/j2cache.properties` to use installed redis servers
3. Open shell windows and execute command: `mvn package -DskipTests=true`  
4. Open multiple command line windows and running `runtest.sh` 
5. In > prompt type `help` to show command list 

### Maven

```
<dependency>
  <groupId>net.oschina.j2cache</groupId>  
  <artifactId>j2cache-core</artifactId>  
  <version>xxxxx</version>  
</dependency>
```
### Usage

Refer to [J2CacheCmd.java](https://gitee.com/ld/J2Cache/blob/master/core/src/net/oschina/j2cache/J2CacheCmd.java)

### Questions

1. J2Cache scenarios？  
First, your application is running in cluster environment, J2Cache can effectively reduce the amount of data transfer between nodes
2. Why j2cache api does not provide the validity of the cache settings  
Defining the validity period of the cached data in the program will cause the cache to be uncontrollable. Because it's difficult to find the source of the problem, it is impossible to check the validity of all the caches in J2Cache. Therefore, the validity period of all caches of J2Cache must be preset in the ehcache configuration

## Projects using J2Cache

* www.oschina.net
* https://gitee.com/jfinal/jfinal
* https://gitee.com/fuhai/jboot
* https://gitee.com/tywo45/t-io
* ...