#dhz-j2cache
## 说明
目前一级缓存用的是ehcache，二级使用redis；集群同步工具使用jgroups或redis pub sub；
## 计划
下一步会改为模块化工程，使用时按需加载；
包括 :
 <ul>
 <li>一二级缓存自定义</li>
 <li>序列化工具自定义</li>
 </ul>

## 使用说明
在src/test/resources目录下有个spring-cache-test.xml文件，演示了在spring项目中如何使用dhz-j2cache；对于可以使用的配置项也有相应的文本注释
<pre>
* 属性说明：
* useCluster                - 是否为集群，默认true
* cacheBroadcast            - 集群模式下个节点数据同步的机制，可选择为：REDIS_PUBSUB/JGROUPS_MULTICAST
* openSecondCache           - 是否启用二级缓存，默认false
* cache_jgroup_conf_file    - 使用jgroups实现ehcache集群
* cache_ehcache_conf_file   - 一级缓存ehcache配置文件
* cache_redis_conf_file     - 二级缓存redis配置文件
</pre>