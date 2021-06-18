[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![JDK 1.8](https://img.shields.io/badge/JDK-1.8-green.svg "JDK 1.8")]()

Aspect Cache是一个针对Spring Boot，基于AOP注解方式的轻量级缓存，目前支持EHCache，Redis缓存方式。数据类型支持POJO、Map和List数据类型，同时支持自定义缓存key解析，也支持自定义的缓存处理方式，或者扩展支持更多缓存方式。
缓存key使用Spring表达式(SpEL)解析生成。
### 使用
#### 1.引入包
```xml
<dependency>
    <artifactId>aspect-cache</artifactId>
    <groupId>com.gosalelab</groupId>
    <version>1.0.0</version>
</dependency>
```
#### 2. 在Spring Boot启动类上使用如下注解
> @EnableAspectCache

### 3. 添加配置项，确认开启缓存和具体缓存方式
> com.gosalelab.cache.enable=true

> com.gosalelab.cache.provider=eh

或

> com.gosalelab.cache.provider=redis

### 配置项说明
| 名称                 | 配置项 | 数据类型 | 可选值 | 默认值  | 必填  | 说明  |
| ------------------- | ---- | ------- | ------ | ------ | ------ | ------ |
| com.gosalelab.cache | enable | boolean  | true \| false | false| Y | 是否启用Aspect Cache |
| com.gosalelab.cache | provider | String  | eh \| redis  | eh| Y | 默认使用EHCache，如果要使用redis，则改为redis即可|
| com.gosalelab.cache | expire-time | int  | /  | 3600秒(半小时)| N | 全局缓存时间|
| com.gosalelab.cache | key-generator | String  | /  | default| N | 默认缓存key生成类，可以通过扩展KeyGenerator接口，使用自定义类，具体扩展方法见“自定义缓存key生成类”描述|
| com.gosalelab.cache.ehcache | default-cache-name | String  | /  | ehcache_cache| N | EHCache缓存名称 |
| com.gosalelab.cache.ehcache | disk | int  | /  | 200| N | 可使用磁盘持久化多大，单位为：MB |
| com.gosalelab.cache.ehcache | ehcache-file-name | String  | /  | ehcache.xml | N | EHCache外部配置文件名，使用此配置项需要将`com.gosalelab.cache.ehcache.use-xml-file-config`设置为true |
| com.gosalelab.cache.ehcache | max-entries-local-heap | int  | /  | 1000 | N | 堆资源池可存储条目数量|
| com.gosalelab.cache.ehcache | off-heap | int  | /  | 20 | N | 非堆资源池存储大小，单位为：MB|
| com.gosalelab.cache.ehcache | use-xml-file-config | boolean  | true \| false  | false | N | 是否使用xml配置文件|
| com.gosalelab.cache.redis-cache | database | int  |  /  | 0 | N | 缓存存在redis哪一个数据库|
| com.gosalelab.cache.redis-cache | host | String  |  /  | 127.0.0.1 | N | redis服务器地址 |
| com.gosalelab.cache.redis-cache | max-idle | int  |  /  | 100 | N | 最大允许空闲对象数 |
| com.gosalelab.cache.redis-cache | max-total | int  |  /  | 1000 | N | 最大活动对象数 |
| com.gosalelab.cache.redis-cache | max-wait-millis | int  |  /  | 1000 | N | 最大等待时间，单位：毫秒 |
| com.gosalelab.cache.redis-cache | min-idle | int  |  /  | 20 | N | 最小允许空闲对象数 |
| com.gosalelab.cache.redis-cache | password | String  |  /  | / | N | redis服务器登录密码 |
| com.gosalelab.cache.redis-cache | port | int  |  /  | 6379 | N | redis服务器连接端口号 |
| com.gosalelab.cache.redis-cache | timeout | int  |  /  | 2000 | N | 连接超时时间，单位：毫秒 |

### 自定义缓存key生成类
1. 继承KeyGenerator接口，实现getKey方法，可参考默认实现方法。
2. 在新的类上添加如下注解，注解名称命名规则为：自定义名称 + KeyGenerator，如：defaultKeyGenerator。
3. 然后在配置文件中使用如下方式使用缓存key自定义类：`com.gosalelab.cache.key-generator = xxx`

### 自定义缓存提供类
1. 继承`CacheProvider`接口，实现put、get、del方法
2. 添加注解：`@Component("xxxCacheProvider")`，注解命名规则为：自定义名称 + CacheProvider
3. 然后再配置文件中使用如下方式使用自定义缓存提供类：`com.gosalelab.cache.provider = xxx`

### 添加配置项
1. 在`properties`文件夹下新增配置文件
2. 在`CacheProperties`文件初始化新增配置类
3. 在新初始化配置类中添加`@NestedConfigurationProperty`注解
4. 重新编译项目文件
5. 在application.properties中使用配置项，按照如下方式：
`com.gosalelab.cache.新增配置.具体配置项 = 新增配置值
`

### 注解使用
#### 注解
* 注解`@CacheInject`用于缓存写入和读取
* 注解`@CacheEvict`用于删除缓存

#### 注解`@CacheInject`配置项
* key：缓存key
* expire：缓存时间，单位：秒
* pre：缓存key的前缀
* desc：描述
* opType：缓存操作方式，可选值有：
    `CacheOpType.READ_WRITE`：读和写缓存，`CacheOpType.WRITE`：只写缓存，`CacheOpType.READ_ONLY`：只读缓存

#### 注解`@CacheEvict`
* key:缓存key
* pre：缓存key的前缀
* desc：描述

具体参考自带Demo中的测试类：`com.gosalelab.testcase.CacheTest`

### 缓存表达式
默认使用`SpEL`表达式，具体的使用可以参考Demo中的测试项。

### 计划
1. 增加加英文文档
2. 添加缓存后台管理功能
3. 进一步优化代码

### 源码地址
* Gitee:[https://gitee.com/gosalelab/aspect-cache](https://gitee.com/gosalelab/aspect-cache)
* Github:[https://github.com/gosalelab/aspect-cache](https://github.com/gosalelab/aspect-cache)

### 其他
* 如果在使用中遇到问题，欢迎在[Github留言](https://github.com/gosalelab/aspect-cache/issues)  [Gitee留言](https://gitee.com/gosalelab/aspect-cache/issues)
* 同时也欢迎您提交代码，一起完善项目


