package cn.skynethome.redisx.common.ms;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[MasterSlaveJedisPool]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:37:26]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:37:26]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class MasterSlaveJedisPool extends Pool<MasterSlaveJedis> {

	private Logger logger = LoggerFactory.getLogger(MasterSlaveJedisPool.class);
	
	protected String masterName="master";
	
	protected HostAndPort hostAndPortMaster;
	
	protected Set<HostAndPort> hostAndPortSalves;
	
	protected GenericObjectPoolConfig poolConfig;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;
	
	protected String password;

	protected int database = Protocol.DEFAULT_DATABASE;
	
	protected volatile MasterSlaveHostAndPort currentHostMasterSlave;
	
	//protected ConcurrentHashMap<int, V> 
	
	private volatile MasterSlaveJedisFactory factory;
	
	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves, final GenericObjectPoolConfig poolConfig) {
		this(hostAndPortMaster, hostAndPortSalves, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves) {
		this(hostAndPortMaster, hostAndPortSalves, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves, String password) {
		this(hostAndPortMaster, hostAndPortSalves, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
	}

	public MasterSlaveJedisPool( HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves, final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
		this(hostAndPortMaster, hostAndPortSalves, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves, final GenericObjectPoolConfig poolConfig, final int timeout) {
		this(hostAndPortMaster, hostAndPortSalves, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
	}

	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves, final GenericObjectPoolConfig poolConfig, final String password) {
		this(hostAndPortMaster, hostAndPortSalves, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
	}

	/**
	 * Master-Slave must be use the same config
	 */
	public MasterSlaveJedisPool(HostAndPort hostAndPortMaster, Set<HostAndPort> hostAndPortSalves,final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
		this.hostAndPortMaster = hostAndPortMaster;
		this.hostAndPortSalves = hostAndPortSalves;
		this.poolConfig = poolConfig;
	    this.timeout = timeout;
	    this.password = password;
	    this.database = database;
	    initMasterSlavePool();
	}
	
	protected void initMasterSlavePool() {
		MasterSlaveHostAndPort masterSlaveHostAndPort = getMasterSlaves();
		initPool(masterSlaveHostAndPort);
	}
	
	protected MasterSlaveHostAndPort getMasterSlaves() {
		HostAndPort master = null;
		logger.info("Trying to add Master-Slaves from params...");
		master = hostAndPortMaster;
		MasterSlaveHostAndPort masterSlaveHostAndPort = new MasterSlaveHostAndPort(masterName, master, this.hostAndPortSalves);
		logger.info("add Master and Slaves is successfully...");
		return masterSlaveHostAndPort;
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
	
	public MasterSlaveHostAndPort getCurrentHostMasterSlave() {
		return currentHostMasterSlave;
	}

	protected HostAndPort toHostAndPort(List<String> hostAndPort){
		return new HostAndPort(hostAndPort.get(0), Integer.parseInt(hostAndPort.get(1)));
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
