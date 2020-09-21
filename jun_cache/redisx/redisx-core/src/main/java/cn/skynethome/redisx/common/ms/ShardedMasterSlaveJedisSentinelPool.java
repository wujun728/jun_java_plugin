package cn.skynethome.redisx.common.ms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  * 文件名称:[ShardedMasterSlaveJedisSentinelPool]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:37:58]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:37:58]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class ShardedMasterSlaveJedisSentinelPool extends Pool<ShardedMasterSlaveJedis> {

	private Logger logger = LoggerFactory.getLogger(ShardedMasterSlaveJedisSentinelPool.class);
	
	protected Set<String> masterNames;
	
	protected Set<String> sentinels;
	
	protected GenericObjectPoolConfig poolConfig;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;
	
	protected String password;

	protected int database = Protocol.DEFAULT_DATABASE;
	
	protected volatile Set<MasterSlaveHostAndPort> currentHostMasterSlaves;
	
	protected Set<ShardedMasterSlaveListener> shardedMasterSlaveListeners = new HashSet<ShardedMasterSlaveListener>();
	
	private volatile ShardedMasterSlaveJedisFactory factory;
	
	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig) {
		this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels) {
		this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, String password) {
		this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
	}

	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
		this(masterNames, sentinels, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
	}

	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final int timeout) {
		this(masterNames, sentinels, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
	}

	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final String password) {
		this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
	}

	/**
	 * Master-Slave must be use the same config
	 */
	public ShardedMasterSlaveJedisSentinelPool(Set<String> masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
		if(!(masterNames instanceof LinkedHashSet)){
			throw new IllegalArgumentException("Parameter 'masterNames' must be typeof java.util.LinkedHashSet.");
		}
		this.masterNames = masterNames;
		this.sentinels = sentinels;
		this.poolConfig = poolConfig;
	    this.timeout = timeout;
	    this.password = password;
	    this.database = database;
		
	    initSentinelPool();
		initSentinelLiseners();
	}
	
	protected void initSentinelPool() {
		Set<MasterSlaveHostAndPort> masterSlaveHostAndPorts = sentinelGetMasterSlaves();
		initPool(masterSlaveHostAndPorts);
	}
	
	protected Set<MasterSlaveHostAndPort> sentinelGetMasterSlaves() {
		Set<MasterSlaveHostAndPort> masterSlaveHostAndPorts = new LinkedHashSet<MasterSlaveHostAndPort>();
		logger.info("Trying to find Sharded-Master-Slaves from available sentinels...");

		for (String sentinel : sentinels) {
			final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
			logger.info("Connecting to sentinel " + hap);

			Jedis jedis = null;
			try {
				jedis = new Jedis(hap.getHost(), hap.getPort());
				for(String masterName : masterNames){
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
					
					HostAndPort master = toHostAndPort(masterAddr);
					Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
					for(Map<String,String> slave : slaveAddrs){
						if("slave".equals(slave.get("flags"))){ //is normal worked slave at now
							slaves.add(toHostAndPort(Arrays.asList(slave.get("ip"), slave.get("port"))));
						}
					}
					MasterSlaveHostAndPort masterSlaveHostAndPort = new MasterSlaveHostAndPort(masterName, master, slaves);
					logger.info("Found sharded master-slaves : {}", masterSlaveHostAndPort);
					masterSlaveHostAndPorts.add(masterSlaveHostAndPort);
				}
				return masterSlaveHostAndPorts;
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
		if(currentHostMasterSlaves != null){
			logger.info("Starting Sentinel listeners...");
			for (String sentinel : sentinels) {
				try {
					final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
					ShardedMasterSlaveListener shardedMasterSlaveListener = new ShardedMasterSlaveListener(masterNames, hap.getHost(), hap.getPort());
					shardedMasterSlaveListeners.add(shardedMasterSlaveListener);
					shardedMasterSlaveListener.start();
				} catch(Exception e) {
					logger.error("Starting Sentinel listeners caught a exception: " + e.getMessage(), e);
				}
			}
		}
	}
	
	protected synchronized void initPool(Set<MasterSlaveHostAndPort> masterSlaveHostAndPorts){
		if(masterSlaveHostAndPorts != null && !masterSlaveHostAndPorts.equals(currentHostMasterSlaves)){
			currentHostMasterSlaves = masterSlaveHostAndPorts;
			
			List<MasterSlaveJedisShardInfo> shards = new ArrayList<MasterSlaveJedisShardInfo>();
			for(MasterSlaveHostAndPort masterSlaveHostAndPort : masterSlaveHostAndPorts){
				JedisShardInfo masterShard = toJedisShardInfo(masterSlaveHostAndPort.getMaster(), masterSlaveHostAndPort.getMasterName());
				List<JedisShardInfo> slaveShards = new ArrayList<JedisShardInfo>();
				for(HostAndPort slave : masterSlaveHostAndPort.getSlaves()){
					JedisShardInfo slaveShard = toJedisShardInfo(slave, masterSlaveHostAndPort.getMasterName());
					slaveShards.add(slaveShard);
				}
				shards.add(new MasterSlaveJedisShardInfo(masterSlaveHostAndPort.getMasterName(), masterShard, slaveShards));
			}
			
			if(factory == null){
				factory = new ShardedMasterSlaveJedisFactory(shards, Hashing.MURMUR_HASH, null);
				initPool(poolConfig, factory);
			}else{
				factory.setShards(shards);
				// although we clear the pool, we still have to check the
		        // returned object
		        // in getResource, this call only clears idle instances, not
		        // borrowed instances
				internalPool.clear();
			}
			
			logger.info("Create Sharded-Master-Slaves jedis pool for {}", currentHostMasterSlaves);
		}
	}
	
	protected JedisShardInfo toJedisShardInfo(HostAndPort hostAndPort, String masterName) {
		JedisShardInfo shard = new JedisShardInfo(hostAndPort.getHost(), hostAndPort.getPort(), timeout, masterName);
		shard.setPassword(password);
		return shard;
	}
	
	public ShardedMasterSlaveJedis getResource() {
		while (true) {
			ShardedMasterSlaveJedis shardedMasterSlaveJedis = super.getResource();
			shardedMasterSlaveJedis.setDataSource(this);

			// get a reference because it can change concurrently
			final Set<MasterSlaveHostAndPort> reference = currentHostMasterSlaves;
			final Set<MasterSlaveHostAndPort> connectionDesc = shardedMasterSlaveJedis.getConnectionDesc();
			if (connectionDesc.equals(reference)) {
				// connected to the correct master
				return shardedMasterSlaveJedis;
			} else {
				returnBrokenResource(shardedMasterSlaveJedis);
			}
		}
	}

	public void returnBrokenResource(final ShardedMasterSlaveJedis resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	public void returnResource(final ShardedMasterSlaveJedis resource) {
		if (resource != null) {
			// get a reference because it can change concurrently
			final Set<MasterSlaveHostAndPort> reference = currentHostMasterSlaves;
			final Set<MasterSlaveHostAndPort> connectionDesc = resource.getConnectionDesc();
			if (connectionDesc.equals(reference)) {
				resource.resetState();
				returnResourceObject(resource);
			} else {
				returnBrokenResource(resource);
			}
		}
	}
	
	public void destroy() {
		for (ShardedMasterSlaveListener m : shardedMasterSlaveListeners) {
			m.shutdown();
		}
		super.destroy();
	}
	
	public Set<MasterSlaveHostAndPort> getCurrentHostMasterSlaves() {
		return currentHostMasterSlaves;
	}

	protected HostAndPort toHostAndPort(List<String> hostAndPort){
		return new HostAndPort(hostAndPort.get(0), Integer.parseInt(hostAndPort.get(1)));
	}
	
	protected class ShardedMasterSlaveListener extends Thread {

		protected Set<String> masterNames;
		protected String host;
		protected int port;
		protected long subscribeRetryWaitTimeMillis = 5000;
		protected Jedis sentinelJedis;
		protected AtomicBoolean running = new AtomicBoolean(false);

		protected ShardedMasterSlaveListener() {
		}

		public ShardedMasterSlaveListener(Set<String> masterNames, String host, int port) {
			this.masterNames = masterNames;
			this.host = host;
			this.port = port;
		}

		public ShardedMasterSlaveListener(Set<String> masterNames, String host, int port, long subscribeRetryWaitTimeMillis) {
			this(masterNames, host, port);
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
										if(masterNames.contains(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.error("Found unavailable redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, messages[5], masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on +sdown for master name {}, but our master name is {}!", messages[5], masterNames);
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
										if(masterNames.contains(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.info("Found available redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, messages[5], masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on -sdown for master name {}, but our master name is {}!", messages[5], masterNames);
										}
									}else{
										logger.error("Invalid message received on Sentinel {} on channel -sdown: {}", host + ":" + port, message);
									}
								}
							}
							if("+switch-master".equals(channel)){ //master has been switched
								String[] messages = message.split(" ");
								if(messages.length == 5){
									if(masterNames.contains(messages[0])){
										String oldMasterIp = messages[1];
										String oldMasterPort = messages[2];
										String newMasterIp = messages[3];
										String newMasterPort = messages[4];
										logger.info("Switch master {} from [{}] to [{}]", messages[0], oldMasterIp + ":" + oldMasterPort, newMasterIp + ":" + newMasterPort);
										initSentinelPool();
									}else{
										logger.error("Ignoring message on +switch-master for master name {}, but our master name is {}!", messages[5], masterNames);
									}
								}else{
									logger.error("Invalid message received on Sentinel {} on channel +switch-master: {}", host + ":" + port, message);
								}
							}
							if("+slave".equals(channel)){ //has new slave joined
								String[] messages = message.split(" ");
								if(messages.length == 8){
									if("slave".equals(messages[0])){
										if(masterNames.contains(messages[5])){
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.error("Found available redis slave[{}] for master[{}@{}]", slaveIp + ":" + slavePort, messages[5], masterIp + ":" + masterPort);
											initSentinelPool();
										}else{
											logger.error("Ignoring message on +slave for master name {}, but our master name is {}!", messages[5], masterNames);
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
	 * ShardedMasterSlaveJedis工厂类
	 * 
	 * @author	  	pengpeng
	 * @date	  	2015年3月14日 上午10:09:00
	 * @version  	1.0
	 */
	protected class ShardedMasterSlaveJedisFactory implements PooledObjectFactory<ShardedMasterSlaveJedis> {
		private List<MasterSlaveJedisShardInfo> shards;
		private Hashing algo;
		private Pattern keyTagPattern;

		public ShardedMasterSlaveJedisFactory(List<MasterSlaveJedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}

		public void setShards(List<MasterSlaveJedisShardInfo> shards) {
			this.shards = shards;
		}

		public PooledObject<ShardedMasterSlaveJedis> makeObject() throws Exception {
			ShardedMasterSlaveJedis shardedMasterSlaveJedis = new ShardedMasterSlaveJedis(shards, algo, keyTagPattern);
			return new DefaultPooledObject<ShardedMasterSlaveJedis>(shardedMasterSlaveJedis);
		}

		public void destroyObject(PooledObject<ShardedMasterSlaveJedis> pooledShardedMasterSlaveJedis)
				throws Exception {
			pooledShardedMasterSlaveJedis.getObject().disconnect();
		}

		public boolean validateObject(PooledObject<ShardedMasterSlaveJedis> pooledObject) {
			try {
				ShardedMasterSlaveJedis pooledShardedMasterSlaveJedis = pooledObject.getObject();
				if(pooledShardedMasterSlaveJedis.ping()){
					return true;
				}
				return false;
			} catch (Exception ex) {
				return false;
			}
		}

		public void activateObject(PooledObject<ShardedMasterSlaveJedis> p) throws Exception {
			
		}

		public void passivateObject(PooledObject<ShardedMasterSlaveJedis> p) throws Exception {

		}
	}
	
}
