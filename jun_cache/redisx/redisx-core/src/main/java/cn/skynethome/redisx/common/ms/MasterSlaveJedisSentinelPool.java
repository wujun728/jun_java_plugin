package cn.skynethome.redisx.common.ms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[MasterSlaveJedisSentinelPool]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:37:33]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:37:33]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class MasterSlaveJedisSentinelPool extends Pool<MasterSlaveJedis> {

	private Logger logger = LoggerFactory.getLogger(MasterSlaveJedisSentinelPool.class);
	
	protected String masterName;
	
	protected Set<String> sentinels;
	
	protected GenericObjectPoolConfig poolConfig;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;
	
	protected String password;

	protected int database = Protocol.DEFAULT_DATABASE;
	
	protected volatile MasterSlaveHostAndPort currentHostMasterSlave;
	
	//protected ConcurrentHashMap<int, V> 
	
	protected Set<MasterSlaveListener> masterSlaveListeners = new HashSet<MasterSlaveListener>();
	
	private volatile MasterSlaveJedisFactory factory;
	
	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, final GenericObjectPoolConfig poolConfig) {
		this(masterName, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels) {
		this(masterName, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, String password) {
		this(masterName, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
	}

	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
		this(masterName, sentinels, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final int timeout) {
		this(masterName, sentinels, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final String password) {
		this(masterName, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
	}

	/**
	 * Master-Slave must be use the same config
	 */
	public MasterSlaveJedisSentinelPool(String masterName, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
		this.masterName = masterName;
		this.sentinels = sentinels;
		this.poolConfig = poolConfig;
	    this.timeout = timeout;
	    this.password = password;
	    this.database = database;
		
	    initSentinelPool();
		initSentinelLiseners();
	}
	
	protected void initSentinelPool() {
		MasterSlaveHostAndPort masterSlaveHostAndPort = sentinelGetMasterSlaves();
		initPool(masterSlaveHostAndPort);
	}
	
	protected MasterSlaveHostAndPort sentinelGetMasterSlaves() {
		HostAndPort master = null;
		Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
		logger.info("Trying to find Master-Slaves from available sentinels...");

		for (String sentinel : sentinels) {
			final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
			logger.info("Connecting to sentinel " + hap);

			Jedis jedis = null;
			try {
				jedis = new Jedis(hap.getHost(), hap.getPort());
				
				List<String> masterAddr = jedis.sentinelGetMasterAddrByName(masterName);
				List<Map<String,String>> slaveAddrs = jedis.sentinelSlaves(masterName);
				
				if (masterAddr == null || masterAddr.size() != 2) {
					logger.warn("Can not get master addr, master name: {}. Sentinel: {}.", masterName, hap);
			        continue;
			    }
				
				if (slaveAddrs == null || slaveAddrs.isEmpty()) {
					logger.warn("Can not get slave addr, master name: {}. Sentinel: {}.", masterName, hap);
					continue;
				}
				
				master = toHostAndPort(masterAddr);

				for(Map<String,String> slave : slaveAddrs){
					if("slave".equals(slave.get("flags"))){ //is normal worked slave at now
						slaves.add(toHostAndPort(Arrays.asList(slave.get("ip"), slave.get("port"))));
					}
				}
				MasterSlaveHostAndPort masterSlaveHostAndPort = new MasterSlaveHostAndPort(masterName, master, slaves);
				logger.info("Found Master-Slaves : {}", masterSlaveHostAndPort);
				return masterSlaveHostAndPort;
			} catch (JedisConnectionException e) {
				logger.warn("Cannot connect to sentinel running @ {}. Trying next one.", hap);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}
		return null;
	}
	
	protected void initSentinelLiseners() {
		if(currentHostMasterSlave != null){
			logger.info("Starting Sentinel listeners...");
			for (String sentinel : sentinels) {
				try {
					final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
					MasterSlaveListener masterSlaveListener = new MasterSlaveListener(masterName, hap.getHost(), hap.getPort());
					masterSlaveListeners.add(masterSlaveListener);
					masterSlaveListener.start();
				} catch(Exception e) {
					logger.error("Starting Starting Sentinel listeners caught a exception: " + e.getMessage(), e);
				}
			}
		}
	}
	
	protected synchronized void initPool(MasterSlaveHostAndPort masterSlaveHostAndPort){
		if(masterSlaveHostAndPort != null && !masterSlaveHostAndPort.equals(currentHostMasterSlave)){
			currentHostMasterSlave = masterSlaveHostAndPort;
			
			JedisShardInfo masterShard = toJedisShardInfo(masterSlaveHostAndPort.getMaster(), masterSlaveHostAndPort.getMasterName());
			List<JedisShardInfo> slaveShards = new ArrayList<JedisShardInfo>();
			for(HostAndPort slave : masterSlaveHostAndPort.getSlaves()){
				JedisShardInfo slaveShard = toJedisShardInfo(slave, null);
				slaveShards.add(slaveShard);
			}
			if(factory == null){
				factory = new MasterSlaveJedisFactory(masterShard, slaveShards, Hashing.MURMUR_HASH, null);
				initPool(poolConfig, factory);
			}else{
				factory.setMasterShard(masterShard);
				factory.setSlaveShards(slaveShards);
				// although we clear the pool, we still have to check the
		        // returned object
		        // in getResource, this call only clears idle instances, not
		        // borrowed instances
				internalPool.clear();
			}
			
			logger.info("Create Master-Slaves jedis pool for {}", currentHostMasterSlave);
		}
	}
	
	protected JedisShardInfo toJedisShardInfo(HostAndPort hostAndPort, String name) {
		JedisShardInfo shard = new JedisShardInfo(hostAndPort.getHost(), hostAndPort.getPort(), timeout, name);
		shard.setPassword(password);
		return shard;
	}
	
	public MasterSlaveJedis getResource() {
		while (true) {
			MasterSlaveJedis  masterSlaveJedis = super.getResource();
			masterSlaveJedis.setDataSource(this);
			// get a reference because it can change concurrently
			final MasterSlaveHostAndPort reference = currentHostMasterSlave;
			final MasterSlaveHostAndPort connectionDesc = masterSlaveJedis.getConnectionDesc();
			if (connectionDesc.equals(reference)) {
				// connected to the correct master
				return masterSlaveJedis;
			} else {
				returnBrokenResource(masterSlaveJedis);
			}
		}
	}

	public void returnBrokenResource(final MasterSlaveJedis resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	public void returnResource(final MasterSlaveJedis resource) {
		if (resource != null) {
			// get a reference because it can change concurrently
			final MasterSlaveHostAndPort reference = currentHostMasterSlave;
			final MasterSlaveHostAndPort connectionDesc = resource.getConnectionDesc();
			if (connectionDesc.equals(reference)) {
				// connected to the correct master
				resource.resetState();
				returnResourceObject(resource);
			} else {
				returnBrokenResource(resource);
			}
		}
	}
	
	public void destroy() {
		for (MasterSlaveListener m : masterSlaveListeners) {
			m.shutdown();
		}
		super.destroy();
	}
	
	public MasterSlaveHostAndPort getCurrentHostMasterSlave() {
		return currentHostMasterSlave;
	}

	protected HostAndPort toHostAndPort(List<String> hostAndPort){
		return new HostAndPort(hostAndPort.get(0), Integer.parseInt(hostAndPort.get(1)));
	}
	
	protected class MasterSlaveListener extends Thread {

		protected String masterName;
		protected String host;
		protected int port;
		protected long subscribeRetryWaitTimeMillis = 5000;
		protected Jedis sentinelJedis;
		protected AtomicBoolean running = new AtomicBoolean(false);

		protected MasterSlaveListener() {
		}

		public MasterSlaveListener(String masterName, String host, int port) {
			this.masterName = masterName;
			this.host = host;
			this.port = port;
		}

		public MasterSlaveListener(String masterName, String host, int port, long subscribeRetryWaitTimeMillis) {
			this(masterName, host, port);
			this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
		}

		public void run() {
			running.set(true);
			while (running.get()) {
				logger.debug(">>> Runing!");
				sentinelJedis = new Jedis(host, port);
				try {
					sentinelJedis.subscribe(new JedisPubSub() {
						public void onMessage(String channel, String message) {
							logger.info("Sentinel {} published: {} {}", host + ":" + port, channel, message);
							if("+sdown".equals(channel)){ //has slave offline
								String[] messages = message.split(" ");
								if(messages.length == 8){
									if("slave".equals(messages[0])){
										if(masterName.equals(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.error("Found unavailable redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, masterName, masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on +sdown for master name {}, but our master name is {}!", messages[5], masterName);
										}
									}else{
										logger.error("Invalid message received on Sentinel {} on channel +sdown: {}", host + ":" + port, message);
									}
								}
							}
							if("-sdown".equals(channel)){ //has slave online
								String[] messages = message.split(" ");
								if(messages.length == 8){
									if("slave".equals(messages[0])){
										if(masterName.equals(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.info("Found available redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, masterName, masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on -sdown for master name {}, but our master name is {}!", messages[5], masterName);
										}
									}else{
										logger.error("Invalid message received on Sentinel {} on channel -sdown: {}", host + ":" + port, message);
									}
								}
							}
							if("+switch-master".equals(channel)){ //master has been switched
								String[] messages = message.split(" ");
								if(messages.length == 5){
									if(masterName.equals(messages[0])){
										String oldMasterIp = messages[1];
										String oldMasterPort = messages[2];
										String newMasterIp = messages[3];
										String newMasterPort = messages[4];
										logger.info("Switch master {} from [{}] to [{}]", masterName, oldMasterIp + ":" + oldMasterPort, newMasterIp + ":" + newMasterPort);
										initSentinelPool();
									}else{
										logger.error("Ignoring message on +switch-master for master name {}, but our master name is {}!", messages[5], masterName);
									}
								}else{
									logger.error("Invalid message received on Sentinel {} on channel +switch-master: {}", host + ":" + port, message);
								}
							}
							if("+slave".equals(channel)){ //has new slave joined
								String[] messages = message.split(" ");
								if(messages.length == 8){
									if("slave".equals(messages[0])){
										if(masterName.equals(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.error("Found available redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, masterName, masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on +slave for master name {}, but our master name is {}!", messages[5], masterName);
										}
									}else{
										logger.error("Invalid message received on Sentinel {} on channel +slave: {}", host + ":" + port, message);
									}
								}
							}
						}
					}, "+switch-master", "+sdown", "-sdown", "+slave");
				} catch (JedisConnectionException e) {
					if (running.get()) {
						logger.error("Lost connection to Sentinel at {}. Sleeping {}ms and retrying.", host + ":" + port, subscribeRetryWaitTimeMillis);
						try {
							Thread.sleep(subscribeRetryWaitTimeMillis);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						logger.error("Listener stop running and unsubscribing from Sentinel at {}.", host + ":" + port);
					}
				}
			}
		}

		public void shutdown() {
			try {
				logger.info("Shutting down listener on {}.", host + ":" + port);
				running.set(false);
				// This isn't good, the Jedis object is not thread safe
				sentinelJedis.disconnect();
			} catch (Exception e) {
				logger.error("Caught exception while shutting down: " + e.getMessage());
			}
		}
	}

	/**
	 * MasterSlaveJedis工厂类
	 * 
	 * @author	  	pengpeng
	 * @date	  	2015年3月14日 上午10:09:00
	 * @version  	1.0
	 */
	protected static class MasterSlaveJedisFactory implements PooledObjectFactory<MasterSlaveJedis> {
		private JedisShardInfo masterShard;
		private List<JedisShardInfo> slaveShards;
		private Hashing algo;
		private Pattern keyTagPattern;

		public MasterSlaveJedisFactory(JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, Hashing algo, Pattern keyTagPattern) {
			this.masterShard = masterShard;
			this.slaveShards = slaveShards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}

		public void setMasterShard(JedisShardInfo masterShard) {
			this.masterShard = masterShard;
		}

		public void setSlaveShards(List<JedisShardInfo> slaveShards) {
			this.slaveShards = slaveShards;
		}

		public PooledObject<MasterSlaveJedis> makeObject() throws Exception {
			MasterSlaveJedis masterSlaveJedis = new MasterSlaveJedis(masterShard, slaveShards, algo, keyTagPattern);
			return new DefaultPooledObject<MasterSlaveJedis>(masterSlaveJedis);
		}

		public void destroyObject(PooledObject<MasterSlaveJedis> pooledMasterSlaveJedis)
				throws Exception {
			pooledMasterSlaveJedis.getObject().disconnect();
		}

		public boolean validateObject(PooledObject<MasterSlaveJedis> pooledMasterSlaveJedis) {
			try {
				MasterSlaveJedis masterSlaveJedis = pooledMasterSlaveJedis.getObject();
				if(!"PONG".equals(masterSlaveJedis.ping())){
					return false;
				}
				for (Jedis slaveJedis : masterSlaveJedis.getAllShards()) {
					if (!"PONG".equals(slaveJedis.ping())) {
						return false;
					}
				}
				return true;
			} catch (Exception ex) {
				return false;
			}
		}

		public void activateObject(PooledObject<MasterSlaveJedis> p) throws Exception {
			
		}

		public void passivateObject(PooledObject<MasterSlaveJedis> p) throws Exception {

		}
	}
	
}
