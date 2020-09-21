package cn.skynethome.redisx.common.ms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[RedisClusterPool]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 下午5:28:48]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 下午5:28:48]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class JedisClusterPool extends Pool<JedisCluster> {

	private Logger logger = LoggerFactory.getLogger(JedisClusterPool.class);
	
	protected List<String> _hostAndPorts;
	
	protected GenericObjectPoolConfig poolConfig;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;
	
	protected int maxRedirections = DEFAULT_REDIRECTIONS;
	
	private static int DEFAULT_REDIRECTIONS = 10;

	protected int database = Protocol.DEFAULT_DATABASE;
	
	private volatile RedisClusterJedisFactory factory;

	public JedisClusterPool(List<String> _hostAndPorts, final GenericObjectPoolConfig poolConfig) {
		this(_hostAndPorts, DEFAULT_REDIRECTIONS,poolConfig,Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_DATABASE);
	}

	public JedisClusterPool(List<String> _hostAndPorts) {
		this(_hostAndPorts, DEFAULT_REDIRECTIONS,new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_DATABASE);
	}

	public JedisClusterPool(List<String> _hostAndPorts,final int maxRedirections) {
		this(_hostAndPorts,maxRedirections, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT);
	}

	public JedisClusterPool( List<String> _hostAndPorts, final int maxRedirections,final GenericObjectPoolConfig poolConfig, int timeout) {
		this(_hostAndPorts,maxRedirections, poolConfig, timeout, Protocol.DEFAULT_DATABASE);
	}



	public JedisClusterPool(List<String> _hostAndPorts,final int maxRedirections,final GenericObjectPoolConfig poolConfig) {
		this(_hostAndPorts,maxRedirections, poolConfig, Protocol.DEFAULT_TIMEOUT);
	}


	public JedisClusterPool(List<String> _hostAndPorts,final int maxRedirections,final GenericObjectPoolConfig poolConfig, int timeout, final int database) {
		this._hostAndPorts = _hostAndPorts;
		this.maxRedirections = maxRedirections;
		this.poolConfig = poolConfig;
	    this.timeout = timeout;
	    this.database = database;
	    initRedisClutersPool();
	}
	
	protected void initRedisClutersPool() {
		initPool(_hostAndPorts);
	}
	

	
	
	protected synchronized void initPool(List<String> _hostAndPorts){
		if(_hostAndPorts != null && _hostAndPorts.size() > 0){

			Set<HostAndPort> hostAndPorts  = new LinkedHashSet<HostAndPort>();
			for (String hostAndPort : _hostAndPorts) {
				final HostAndPort hap = toHostAndPort(Arrays.asList(hostAndPort.split(":")));
				hostAndPorts.add(hap);
			}
			
			if(factory == null){
				factory = new RedisClusterJedisFactory(hostAndPorts,poolConfig,timeout,maxRedirections);
				initPool(poolConfig, factory);
			}else{
				factory.setHostAndPorts(hostAndPorts);
				factory.setPoolConfig(poolConfig);
				factory.setMaxRedirections(maxRedirections);
				factory.setTimeout(timeout);
				internalPool.clear();
			}
			
			logger.info("Create jedis cluster pool for {}", hostAndPorts);
		}
	}
	
	@Override
	public JedisCluster getResource() {
			JedisCluster  jedisCluster = super.getResource();
			return jedisCluster;
	}

	public void returnBrokenResource(final JedisCluster resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	public void returnResource(final JedisCluster resource) {
		if (resource != null) {
				returnResourceObject(resource);
				returnBrokenResource(resource);
		}
	}
	

	protected HostAndPort toHostAndPort(List<String> hostAndPort){
		return new HostAndPort(hostAndPort.get(0), Integer.parseInt(hostAndPort.get(1)));
	}
	
	/**
	 * RedisClusterJedis工厂类
	 * 
	 * @author	  	pengpeng
	 * @date	  	2015年3月14日 上午10:09:00
	 * @version  	1.0
	 */
	protected static class RedisClusterJedisFactory implements PooledObjectFactory<JedisCluster> {
		
		private Set<HostAndPort> hostAndPorts;
		private GenericObjectPoolConfig poolConfig;
		private int timeout;
		private int maxRedirections;
		
		
		protected RedisClusterJedisFactory(Set<HostAndPort> hostAndPorts,GenericObjectPoolConfig poolConfig,int timeout,int maxRedirections) {
			
		   this.hostAndPorts = hostAndPorts;
		   this.poolConfig = poolConfig;
		   this.timeout = timeout;
		   this.maxRedirections = maxRedirections;
		}
		
		
		
		public Set<HostAndPort> getHostAndPorts() {
			return hostAndPorts;
		}



		public void setHostAndPorts(Set<HostAndPort> hostAndPorts) {
			this.hostAndPorts = hostAndPorts;
		}



		public GenericObjectPoolConfig getPoolConfig() {
			return poolConfig;
		}



		public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
			this.poolConfig = poolConfig;
		}

		


		public int getTimeout() {
			return timeout;
		}



		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}



		public int getMaxRedirections() {
			return maxRedirections;
		}



		public void setMaxRedirections(int maxRedirections) {
			this.maxRedirections = maxRedirections;
		}

		@Override
		public PooledObject<JedisCluster> makeObject() throws Exception {
			// TODO Auto-generated method stub
			JedisCluster jedisCluster = new JedisCluster(hostAndPorts, timeout, maxRedirections,poolConfig);
			return new DefaultPooledObject<JedisCluster>(jedisCluster);
		}
		@Override
		public void destroyObject(PooledObject<JedisCluster> p) throws Exception {
			// TODO Auto-generated method stub
			p.getObject().close();
		}
		
		@Override
		public boolean validateObject(PooledObject<JedisCluster> p) {
			
			JedisCluster jedisCluster = p.getObject();
			
			if(null != jedisCluster)
			{
				return true;
			}else
			{
				return false;
			}
			
	    }
		@Override
		public void activateObject(PooledObject<JedisCluster> p) throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void passivateObject(PooledObject<JedisCluster> p) throws Exception {
			// TODO Auto-generated method stub
			
		}

	}
	
}
