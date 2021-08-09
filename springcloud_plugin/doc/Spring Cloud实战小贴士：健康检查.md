# Spring Cloud实战小贴士：健康检查

**原创**

 [2017-04-22](https://blog.didispace.com/spring-cloud-tips-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

今天在博客的交流区收到一条不错的问题，拿出来给大家分享一下。具体问题如下：

> 因为项目里面用到了redis集群，但并不是用spring boot的配置方式，启动后项目健康检查老是检查redis的时候状态为down，导致注册到eureka后项目状态也是down。
> 问下能不能设置spring boot不检查 redis的健康状态

```
"redis": { 
    "status": "DOWN", 
    "error": "org.springframework.data.redis.RedisConnectionFailureException: Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool" 
}
```

问题原帖可[点击此处](http://qa.didispace.com/?/question/7)跳转！

## 原因分析

如提问者所述，由于在Spring Boot项目中引用了Redis模块，所以Spring Boot Actuator会对其进行健康检查，正常情况下不会出现问题，但是由于采用了其他配置方式，导致redis的连接检查没有通过。这样就会导致了Consul或Eureka的HealthCheck认为该服务是DOWN状态。

那么redis的健康检查是如何实现的呢？我们不妨来看看健康检查的自动化配置中针对redis的配置源码：

```
@Configuration
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnEnabledHealthIndicator("redis")
public static class RedisHealthIndicatorConfiguration extends
		CompositeHealthIndicatorConfiguration<RedisHealthIndicator, RedisConnectionFactory> {

	@Autowired
	private Map<String, RedisConnectionFactory> redisConnectionFactories;

	@Bean
	@ConditionalOnMissingBean(name = "redisHealthIndicator")
	public HealthIndicator redisHealthIndicator() {
		return createHealthIndicator(this.redisConnectionFactories);
	}

}
```

以上内容取自：`org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration`类。从自动化配置中我们可以看到，会自动加载一个针对redis的`HealthIndicator`实现，并且该Bean命名为`redisHealthIndicator`。

## 解决方法

通过上面的分析，我们已经知道了是哪个Bean导致了服务实例的健康检查不通过，那么如何解决该问题的方法也就马上能想到了：我们只需要再实现一个redis的`HealthIndicator`实现来替代原先默认的检查逻辑。比如：

```
@Component
public class RedisHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }
    
}
```

上面通过实现`HealthIndicator`接口中的health方法，直接返回了up状态。通过`@Component`注解，让Spring Boot扫描到该类就能自动的进行加载，并覆盖原来的redis健康检查实现。当然，这里的实现并不好，因为它只是为了让健康检查可以通过，但是并没有做真正的健康检查。如提问者所说，采用了其他配置访问，那么正确的做法就是在`health`方法中实现针对其他配置的内容进行健康检查。

**注意：这里隐含了一个实现命名的问题，由于默认的bean名称会使用`redisHealthIndicator`，所以这里的定义可以替换默认的实现，因为它的名字与`@ConditionalOnMissingBean(name = "redisHealthIndicator")`中的命名一致。但是如果您自定义的实现类并非叫`RedisHealthIndicator`，它的默认名称与自动化配置的名称是不匹配的，那么就不会替换，这个时候需要在`@Component`注解中指定该Bean的名称为`redisHealthIndicator`**。