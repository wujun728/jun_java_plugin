package com.caland.common.web.session.cache;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedDangaCache implements SessionCache, InitializingBean {
	private MemCachedClient client;
//	private String[] servers = {"192.168.100.201:11211","192.168.100.150:11211"};
	private String[] servers = {"192.168.2.128:11211"};
//	private Integer[] weights = {1,6};
	private Integer[] weights = {1};

	@SuppressWarnings("unchecked")
	public HashMap<String, Serializable> getSession(String root) {
		return (HashMap<String, Serializable>) client.get(root);
	}

	public void setSession(String root, Map<String, Serializable> session,
			int exp) {
		client.set(root, session, new Date(System.currentTimeMillis() + exp* 60 * 1000));
	}

	public Serializable getAttribute(String root, String name) {
		HashMap<String, Serializable> session = getSession(root);
		return session != null ? session.get(name) : null;
	}

	public void setAttribute(String root, String name, Serializable value,
			int exp) {
		HashMap<String, Serializable> session = getSession(root);
		if (session == null) {
			session = new HashMap<String, Serializable>();
		}
		session.put(name, value);
		Date expDate = new Date(System.currentTimeMillis() + exp * 60 * 1000);
		client.set(root, session, expDate);
	}

	public void clear(String root) {
		client.delete(root);
	}

	public boolean exist(String root) {
		return client.keyExists(root);
	}

	public void afterPropertiesSet() throws Exception {
		client = new MemCachedClient();
		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();

		// set the servers and the weights
		pool.setServers(servers);
		pool.setWeights(weights);

		// set some basic pool settings
		// 5 initial, 5 min, and 250 max conns
		// and set the max idle time for a conn
		// to 6 hours
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);

		// set the sleep for the maint thread
		// it will wake up every x seconds and
		// maintain the pool size
		pool.setMaintSleep(30);

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don't set a connect timeout
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		// initialize the connection pool
		pool.initialize();

		// lets set some compression on for the client
		// compress anything larger than 64k
		client.setCompressEnable(true);
		client.setCompressThreshold(64 * 1024);
	}

	public String[] getServers() {
		return servers;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}

	public Integer[] getWeights() {
		return weights;
	}

	public void setWeights(Integer[] weights) {
		this.weights = weights;
	}
	@Test
//	@Ignore
	public void testMemcached(){

		MemCachedClient c = new MemCachedClient();
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		pool.initialize();
		c.setCompressEnable(true);
		c.setCompressThreshold(64 * 1024);
		String key = "t11";//区分大小写
		Object object = c.get(key);
		System.out.println(object);
//		String value = "lance";
//		int exp = 1;
//		c.set(key, value);//t11 : lance
//		boolean keyExists1 = c.keyExists(key);
//		System.out.println("1存在吗?" + keyExists1);
//		Object s = c.get(key);
//		System.out.println(s);
//		c.set(key, value);
//		boolean keyExists2 = c.keyExists(key);
//		System.out.println("2存在吗?" + keyExists2);
		
	}
	
	@Test
	public void testRedis(){
//		   JedisPool pool;  
//		    Jedis jedis;  
//		        pool = new JedisPool(new JedisPoolConfig(), "172.16.100.184");  
		  
//		        jedis = pool.getResource();  
//		        jedis.auth("password");  
//		Jedis jedis = new Jedis("192.168.100.150");  
//		String keys = "foo";  
		// 删数据  
//		jedis.del(keys);
		// 存数据  
//		jedis.set(keys, "zhangsan");
		// 取数据  
//		String value = jedis.get(keys);
//		System.out.println(value); 
		 
		
		//使用Jedis池。池化实现Jedis使访问redis的效率更高。
		//初始化池
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxActive(100);
//		config.setMaxIdle(10);
//		config.setMaxWait(100);
//		config.setTestOnBorrow(true);
//		config.setTestWhileIdle(true);
//		config.setMinEvictableIdleTimeMillis(1000);
//		config.setTimeBetweenEvictionRunsMillis(1000);
//		config.setNumTestsPerEvictionRun(1000);
//		new JedisPool(config, "192.168.100.150", 6379);
		 //从池中取jedis对象
//		Jedis jedis = pool.getResource();
		// 释放jedis对象回到池
//		pool.returnResource(jedis);
		 
		/*
		 
		//Jedis主要API
		//连接相关API
		//默认使用0号数据库，这里可以选择
		jedis.select(1);
		//清空当前数据库
		jedis.flushDB();
		//清空所有数据库
		jedis.flushAll();
		 //key相关API
		String key = "zhang";
		//key的有效期3s
		jedis.expire(key, 3);
		//重命名key
		jedis.rename("zhang", "zhang1");
		//是否存在key，返回boolean
		jedis.exists(key);
		//遍历key
		Set<String> keys = jedis.keys("*");
		 //String相关API
		//设置key-value
		jedis.set("ye", "liang");
		//根据key获得value
		System.out.println(jedis.get("ye"));
		//删除key
		jedis.del("ye");
		 //Map相关API
		//将map存入redis
		jedis.hset("student:1", "NUM", "1");
		jedis.hset("student:1", "NAME", "zhangsan");
		jedis.hset("student:2", "NUM", "2");
		jedis.hset("student:2", "NAME", "lisi");
		//删除某个map
		jedis.hdel("student:1", "NUM");
		//获取map
		System.out.println(jedis.hget("student:1", "NAME"));
		//遍历map
		Set<String> fields = jedis.hkeys("student:2");
		for(String field : fields){
		    System.out.println(field+":"+jedis.hget("student:2", field));
		}
		//List相关API
		//添加数据  
		jedis.lpush("lists", "aa");  
		jedis.lpush("lists", "bb");  
		jedis.lpush("lists", "cc");  
		//list长度
		System.out.println(jedis.llen("lists"));
		//获得第一至第三个元素
		System.out.println(jedis.lrange("lists", 0, 2));  
		//修改第一个元素
		jedis.lset("lists", 0, "dd");  
		//获取第一个元素 
		System.out.println(jedis.lindex("lists", 0));  
		//删除从表头开始value为bb的一个元素
		System.out.println(jedis.lrem("lists", 1, "bb"));  
		//删除区间以外的数据  
		System.out.println(jedis.ltrim("lists", 0, 1));
		 //Set相关API
		//添加数据  
		jedis.sadd("sets", "aa");  
		jedis.sadd("sets", "bb");  
		jedis.sadd("sets", "cc");  
		//判断value是否在列表中  
		System.out.println(jedis.sismember("sets", "bb"));  
		//整个列表值  
		System.out.println(jedis.smembers("sets"));  
		//删除指定元素  
		System.out.println(jedis.srem("sets", "bb"));
		//SortedSet相关API 
		//添加数据  
		jedis.zadd("zset", 11, "aa");  
		jedis.zadd("zset", 12, "bb");  
		jedis.zadd("zset", 13, "cc");  
		jedis.zadd("zset", 14, "dd");  
		//元素个数  
		System.out.println(jedis.zcard("zset"));  
		//元素下标  
		System.out.println(jedis.zscore("zset", "cc"));  
		//整个集合值  
		System.out.println(jedis.zrange("zset", 0, -1));  
		//删除元素  
		System.out.println(jedis.zrem("zset", "cc"));
		//score范围内的元素个数
		System.out.println(jedis.zcount("zset", 11, 14));
		*/
	}
	
}
