#smart-boot


##运行系统
dbapi-restful模块中运行BootStrap.java

**因为smart-boot默认依赖数据库和redis，所以在未做任何配置的情况下启动会报异常。**

- 若项目本身需要依赖数据库,可在`smart-assembly`模块的`application-dev.properties`文件中修改数据配置,否则参照《无数据库运行》章节去除数据库依赖；
- 若项目需要启用分布式Session或缓存服务,可在`smart-assembly`模块的`application-dev.properties`文件中修改redis配置，否则去除`applicaton.properties`中`spring.profiles.include`的配置值`redis`并参照《无分布式会话(Session)》章节关闭分布式会话；



##无数据库运行
作为一套微服务项目，可能自身并不直接访问数据库，此时需要去除工程中的数据库配置项。

1. 修改smart-component模块的pom.xml，将其中的`smart-dal`修改成`smart-assembly`
2. 修改smart-assembly模块中的`application.properties`,去除`spring.profiles.include`中的`database`配置值

>当工程不依赖数据库时，意味着业务层的事务管理功能将失效。此时在`smart-service-impl`层继承`AbstractService`的类中再调用`operateTemplate.operateWithTransaction`将等同于`operateTemplate.operateWithoutTransaction`的效果

##无分布式会话(Session)
因为smart-boot是针对企业应用，所以默认启用了分布式会话。对于个人学习而言在使用上略显不便，因此需要修改一下配置关闭分布式会话。修改`smart-assembly`中的`application.properties`文件，在该文件内添加一条配置项`spring.session.store-type=none`即可。

##单元测试
在smart-boot中进行单元测试的开发非常方便，目前默认支持在`smart-dal`、`smart-component`、`smart-service-integration`、`smart-service-imp`中进行单元测试的编写。需要要在各模块中的`src/test/java`中定义**AbstractUnitTest**的子类，并在测试类中添加注解`@EnableAutoConfiguration`,`@SpringBootConfiguration`即可。例如：

	@SpringBootConfiguration
	@EnableAutoConfiguration
	public class CacheClientTest extends AbstractUnitTest {
	
		@Autowired
		private CacheClient cacheClient;
	
		@Test
		public void testA() {
			int[] a = new int[1024];
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			cacheClient.putObject("1111111", a, 60);
			int[] q = cacheClient.getObject("1111111");
			for (int i = 0; i < q.length; i++) {
				System.out.println(q[i]);
			}
		}
	}
>注：若项目不依赖数据库,需要去除AbstractUnitTest中的注解`@Rollback`、`@Transactional`

##日志系统log4j2
### 为什么选用log4j2?  
1. Apache Log4j 2 is an upgrade to Log4j that provides significant improvements over its predecessor, Log4j 1.x, and provides many of the improvements available in Logback while fixing some inherent problems in Logback's architecture. 一句话总结，官方号称log4j2比log4j和logback都牛逼.
2. 配置简单集中，修改smart-assembly中的log4j2.xml即可实现整个工程的日志管理。

