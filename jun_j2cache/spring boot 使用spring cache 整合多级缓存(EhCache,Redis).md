spring boot 使用spring cache 整合多级缓存(EhCache,Redis)

 
spring boot spring cache实现多级缓存， 只是按照自己的思想实现，若有读者有更好的解决思路，欢迎指点
spring cache实现多级缓存的思路如下：
添加自定义的CacheManager,自定义的Cache,在Cache里实现多级缓存的操作(增删查)
配置类如下注意红色标记的部分：

package com.zyc.zspringboot.config;

import com.zyc.zspringboot.cache.MyCacheManager;
import com.zyc.zspringboot.cache.MyCacheTemplate;
import com.zyc.zspringboot.cache.MyRedisCache;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: RedisConfig
 *
 * @author zyc-admin
 * @date 2018年1月23日
 * @Description:
 */
@Configuration
@EnableCaching(mode = AdviceMode.PROXY)
// model属性默认proxy
// mode属性，可以选择值proxy和aspectj。默认使用proxy。当mode为proxy时，
// 只有缓存方法在外部被调用的时候才会生效。这也就意味着如果一个缓存方法在一个对
// 象的内部被调用SpringCache是不会发生作用的。而mode为aspectj时，就不会有
// 这种问题了。另外使用proxy的时候，只有public方法上的@Cacheable才会发生作用。
// 如果想非public上的方法也可以使用那么就把mode改成aspectj。
@ConfigurationProperties(prefix = "spring.redis")
// 使用@ConfigurationProperties 需要实现属性的getter setter方法，
// 1.5之前版本需要在启动类上使用@EnableConfigurationProperties进行激活，1.5之后直接在配置类上使用@Component，
// 由于本类使用@Configuration注解包含了@Component就不用在声明@Component，
// 这样就可以直接在其他类中使用@Autowired直接把此类注入进来
// extends CachingConfigurerSupport
public class RedisConfig {

	private String hostName;

	private int port;

	private int timeOut;

	private int maxIdle;// 最大空闲连接数, 默认8个

	private int maxWaitMillis;// 获取连接时的最大等待毫秒数

	private boolean testOnBorrow;// 在获取连接的时候检查有效性, 默认false

	private boolean testWhileIdle;// 空闲是否检查是否有效，默认为false

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}


	@Bean("jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestWhileIdle(false);
		return jedisPoolConfig;
	}
	 
	@Bean
	public JedisConnectionFactory redisConnectionFactory(
			JedisPoolConfig jedisPoolConfig) {
		// 如果集群使用new JedisConnectionFactory(new
		// RedisClusterConfiguration()),集群配置在RedisClusterConfiguration,这里省略具体配置
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
		redisConnectionFactory.setPoolConfig(jedisPoolConfig);
	 
		redisConnectionFactory.setHostName(hostName);
		redisConnectionFactory.setPort(port);
		redisConnectionFactory.setTimeout(timeOut);
		return redisConnectionFactory;
	}
	 
	/**
	 * RedisTemplate配置
	 *
	 * @param redisConnectionFactory
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(
			JedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);
		// Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new
		// Jackson2JsonRedisSerializer<Object>(
		// Object.class);
		// ObjectMapper om = new ObjectMapper();
		// om.setVisibility(PropertyAccessor.ALL,
		// JsonAutoDetect.Visibility.ANY);
		// om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// jackson2JsonRedisSerializer.setObjectMapper(om);
		// redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		return redisTemplate;
	}
	 
	/**
	 * redis缓存管理器
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(120); //设置key-value超时时间
		List<String> cacheNames = new ArrayList<>();
		cacheNames.add("myRedis");
		cacheNames.add("j2CacheRedis");
		cacheManager.setCacheNames(cacheNames);
		return cacheManager;
	}
	 
	/**
	 * spring cache整合(EhCache,Redis)二级缓存具体Cache
	 * @param redisCacheManager
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public MyCacheTemplate myCacheTemplate(RedisCacheManager redisCacheManager,RedisTemplate<String, Object> redisTemplate){
		MyCacheTemplate myCacheTemplate=new MyCacheTemplate();
		myCacheTemplate.setRedisCacheManager(redisCacheManager);
		myCacheTemplate.setRedisTemplate(redisTemplate);
		myCacheTemplate.setName("j2CacheRedis");
		return myCacheTemplate;
	}
	/**
	 * 自定义redis缓存
	 * @param redisCacheManager
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public MyRedisCache myRedisCache(RedisCacheManager redisCacheManager,RedisTemplate<String,Object> redisTemplate){
		MyRedisCache myRedisCache=new MyRedisCache();
		//自定义属性配置缓存名称
		myRedisCache.setName("myRedis");
		//redis缓存管理器
		myRedisCache.setRedisCacheManager(redisCacheManager);
		//redisTemplate 实例
		myRedisCache.setRedisTemplate(redisTemplate);
		return myRedisCache;
	}
	 
	/**
	 * spring cache 统一缓存管理器
	 * @param myCacheTemplate
	 * @param myRedisCache
	 * @return
	 */
	@Bean
	@Primary
	public CacheManager cacheManager(MyCacheTemplate myCacheTemplate,MyRedisCache myRedisCache){
		MyCacheManager cacheManager=new MyCacheManager();
		cacheManager.setMyCacheTemplate(myCacheTemplate);
		cacheManager.setMyRedisCache(myRedisCache);
		return cacheManager;
	}
	 
	 // 整合ehcache
	 @Bean
	 public EhCacheCacheManager ehCacheCacheManager() {
		 EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
		  return ehCacheCacheManager;
	 }

 

	 @Bean
	 public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
	 EhCacheManagerFactoryBean cacheManagerFactoryBean = new
	 EhCacheManagerFactoryBean();
	 //这里暂时借用shiro的ehcache配置文件
	 Resource r=new ClassPathResource("ehcache-shiro.xml");
	 cacheManagerFactoryBean.setConfigLocation(r);
	 cacheManagerFactoryBean.setShared(true);
	 return cacheManagerFactoryBean;
	 }

}
自定义CacheManager如下：

package com.zyc.zspringboot.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import java.util.Collection;

/**
 * @author zyc-admin
 * @data 2018-03-20 10:12
 **/
public class MyCacheManager implements CacheManager {

	private MyCacheTemplate myCacheTemplate;

	private MyRedisCache myRedisCache;

	public MyRedisCache getMyRedisCache() {
		return myRedisCache;
	}

	public void setMyRedisCache(MyRedisCache myRedisCache) {
		this.myRedisCache = myRedisCache;
	}

	public MyCacheTemplate getMyCacheTemplate() {
		return myCacheTemplate;
	}

	public void setMyCacheTemplate(MyCacheTemplate myCacheTemplate) {
		this.myCacheTemplate = myCacheTemplate;
	}

	@Override
	public Cache getCache(String name) {
		//多级缓存实现
		if(name.equals(myCacheTemplate.getName())){
			return myCacheTemplate;
		}
		
		return null;
	}

	@Override
	public Collection<String> getCacheNames() {

		return null;
	}
}
自定义Cache实现：

package com.zyc.zspringboot.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.cache.RedisCacheElement;
import org.springframework.data.redis.cache.RedisCacheKey;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.Callable;

/**
 * @author zyc-admin
 * @data 2018-03-19 17:15
 **/
public class MyCacheTemplate implements Cache  {

	private static final Logger logger= LoggerFactory.getLogger(MyCacheTemplate.class);

	private CacheManager ehCacheManager;

	private RedisCacheManager redisCacheManager;

	private RedisTemplate<String, Object> redisTemplate;

	public CacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(CacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

	public RedisCacheManager getRedisCacheManager() {
		return redisCacheManager;
	}

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private String name;

	@Override
	public String getName() {
		return name;
	}
    //自己添加set方法,实现Cache本身无此方法
	public void setName(String name){
		this.name=name;
	}

	@Override
	public Object getNativeCache() {
		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		ehCacheManager=CacheManager.getCacheManager("ec");
		if(ehCacheManager!=null){
			net.sf.ehcache.Cache myEhcache = ehCacheManager.getCache(getName());
			logger.info("取数据ehcache库===key:{}",key);
			if(myEhcache.get(key)!=null){
				ValueWrapper v=new SimpleValueWrapper(myEhcache.get(key).getObjectValue());
				return v;
			}
		}
		Cache myRedis = redisCacheManager.getCache(getName());
		if(myRedis!=null){
			logger.info("取数据reids库===key:{}",key);
			if(myRedis.get(key)!=null){
				RedisCacheElement vr=new RedisCacheElement(new RedisCacheKey(key),myRedis.get(key).get());
				return vr;
			}
		}


		return null;
	}
	 
	@Override
	public <T> T get(Object key, Class<T> type) {
		System.out.println(key+"======================="+type);
		return null;
	}
	 
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return null;
	}
	 
	@Override
	public void put(Object key, Object value) {
		ehCacheManager=CacheManager.getCacheManager("ec");
		if(ehCacheManager!=null){
			net.sf.ehcache.Cache myEhcache = ehCacheManager.getCache(getName());
			Element e=new Element(key,value);
			logger.info("插入ehcache库===key:{},value:{}",key,value);
			myEhcache.put(e);
		}
		Cache myRedis = redisCacheManager.getCache(getName());
		if(myRedis!=null){
			logger.info("插入reids库===key:{},value:{}",key,value);
			myRedis.put(key,value);
		}
		System.out.println("cha ru  key "+ key);
	 
	}
	 
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return null;
	}
	 
	@Override
	public void evict(Object key) {
		Cache myRedis = redisCacheManager.getCache(getName());
		if(myRedis!=null){
			logger.info("删除reids库===key:{}",key);
			myRedis.evict(key);
		}
		ehCacheManager=CacheManager.getCacheManager("ec");
		if(ehCacheManager!=null){
			net.sf.ehcache.Cache myEhcache = ehCacheManager.getCache(getName());
			logger.info("删除ehcache库===key:{}",key);
			if(myEhcache.isKeyInCache(key)){
				myEhcache.remove(key);
			}
	 
		}
	 
		System.out.println("删除  key "+ key);
	}
	 
	@Override
	public void clear() {
		Cache myRedis = redisCacheManager.getCache(getName());
		myRedis.clear();
		ehCacheManager=CacheManager.getCacheManager("ec");
		if(ehCacheManager!=null) {
			net.sf.ehcache.Cache myEhcache = ehCacheManager.getCache(getName());
			myEhcache.removeAll();
		}
	}
}
使用spring cache 注解使用缓存如下：(在首次调用这个方法时，自定义缓存的put,get方法总是打印2次插入数据，2次取数据，暂时不知道什么原因，若有哪位大神知道，欢迎评论指点。)

@Cacheable(value = "j2CacheRedis", key = "'role:id:'+#id",unless ="#result == null")
	//@Log(value = "获取数据并存入缓存")
	public Role getRole(String id) {
		// TODO Auto-generated method stub
		return roleDao.getRole(id);
	}
application.properties配置文件如下

#redis ------start-------
spring.redis.hostName=127.0.0.1
spring.redis.port=63791
spring.redis.timeOut=1000
spring.redis.maxIdle=10
spring.redis.maxWaitMillis=15000
spring.redis.testOnBorrow=true
spring.redis.testWhileIdle=false
ecache-shiro.xml配置文件如下(因本项目使用了shiro,所以Ehcache使用shiro的缓存配置文件)

<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false" name="ec">

<diskStore path="java.io.tmpdir"/> <!-- 缓存存放目录(此目录为放入系统默认缓存目录),也可以是”D:/cache“ java.io.tmpdir -->
    <defaultCache
            maxElementsInMemory="10000"
            eternal="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            overflowToDisk="true"
            maxElementsOnDisk="10000000"
            diskPersistent="false"
            diskExpiryThreadIntervalSeconds="120"
            memoryStoreEvictionPolicy="LRU"
    />

    <!--  -->
    <cache name="j2CacheRedis"
           maxElementsInMemory="1000"
           eternal="false"
           timeToIdleSeconds="120"
           timeToLiveSeconds="140"
           overflowToDisk="false"
           memoryStoreEvictionPolicy="LRU"/>
     
    <!--
  name：Cache的唯一标识
  maxElementsInMemory：内存中最大缓存对象数
  maxElementsOnDisk：磁盘中最大缓存对象数，若是0表示无穷大
  eternal：Element是否永久有效，一但设置了，timeout将不起作用
  overflowToDisk：配置此属性，当内存中Element数量达到maxElementsInMemory时，Ehcache将会Element写到磁盘中
  timeToIdleSeconds：设置Element在失效前的允许闲置时间。仅当element不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大
  timeToLiveSeconds：设置Element在失效前允许存活时间。最大时间介于创建时间和失效时间之间。仅当element不是永久有效时使用，默认是0.，也就是element存活时间无穷大
  diskPersistent：是否缓存虚拟机重启期数据
  diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒
  diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区
   memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）
  -->
</ehcache>  
整合过程遇到的问题：
1 创建cacheManager bean时失败
解决方法：可能你配置了多个cacheManager,确保不重名，在自定义的那个cacheManager上添加 @Primary注解
自定义的CacheManager 要实现CacheManager接口，
注意不要继承org.springframework.cache.support.AbstractCacheManager这个类来实现自己CacheManager
2 创建EhCacheCacheManager失败
解决方法：查看你的EhCacheCacheManager是否和shiro的EhCache缓存有冲突，
EhCache 2.5版本之上，jvm里面一般只能存在EhCache实例。
3 内部方法调用带有spring cache注解的方法 缓存失效
解决方法：spring cache 依赖于aop，使用AopContext.currentProxy().你的方法，
使用之前需先暴露代理对象添加注解 @EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
exposeProxy:暴露代理对象,proxyTargetClass强制使用cglib代理
————————————————
版权声明：本文为CSDN博主「啊大海全是水」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/zhaoyachao123/article/details/79657358