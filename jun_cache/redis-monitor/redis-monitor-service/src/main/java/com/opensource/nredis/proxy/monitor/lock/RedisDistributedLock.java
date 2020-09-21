/**
 * 
 */
package com.opensource.nredis.proxy.monitor.lock;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import com.opensource.nredis.proxy.monitor.quartz.utils.SpringContextUtil;
import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
/**
 * @author liubing
 *
 */
public class RedisDistributedLock extends DistributedLock {
	
	/**
     * lock的key
     */
    private String key;

    /**
     * lock的心跳时间(毫秒)
     * <p>
     * 必须满足(heartbeatTime <= timeout*1000)
     */
    private long heartbeatTime;

    /**
     * lock的自然超时时间(秒)
     */
    private int timeout;

    /**
     * 版本号时间，作为获取锁的客户端操作的依据
     */
    private long versionTime;


    private ShardedJedisPool shardedJedisPool;
	 /**
     * 
     * @param key
     * @param timeout
     */
    public RedisDistributedLock(String key, int timeout) {
        this(key, timeout * 1000, timeout);
    }

  
    /**
     * @param key
     * @param heartbeatTime
     * @param timeout
     */
    public RedisDistributedLock(String key, long heartbeatTime, int timeout) {

        this.key = key;
        this.heartbeatTime = heartbeatTime;
        this.timeout = timeout;
        shardedJedisPool=(ShardedJedisPool) SpringContextUtil.getBean("shardedJedisPool");
    }
	/* (non-Javadoc)
	 * @see com.wanda.restful.framework.redis.lock.DistributedLock#lock()
	 */
	@Override
	protected boolean lock() throws Exception{
		// 1. 通过setnx试图获取一个lock,setnx成功，则成功获取一个锁
		
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			 	boolean setnx = setnx(key, buildVal(), timeout);
		        // 2.锁获取成功返回true,锁获取失败并且快速失败的为true则直接返回false
		        if (setnx ) {
		            return setnx;
		        }
		        // 3.setnx失败，说明锁仍然被其他对象保持，检查其是否已经超时，未超时，则直接返回失败
		        long oldValue = getLong(jedis.get(key));
		        if (oldValue > System.currentTimeMillis()) {
		            return false;
		        }
		        // 4.已经超时,则获取锁
		        long getSetValue = getLong(jedis.getSet(key, buildVal()));
		        // 5.key在当前方法执行过程中失效(即oldValue返回了0或者getSetValue返回了0)，故可再进行一次竞争
		        if (getSetValue == 0) {
		            // 6.true代表竞争成功，false则已被其他进程获取
		            return setnx(key, buildVal(), timeout);
		        }
		        // 7.已被其他进程获取(已被其他进程通过getset设置新值)
		        if (getSetValue != oldValue) {
		            return false;
		        }
		        // 8.获取成功，续租过期时间
		        if (jedis.expire(key, timeout)==1) {
		            return true;
		        }
		        // 9.续租失败,key可能失效了，再获取一次
		        return setnx(key, buildVal(), timeout);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
       
	}

	/* (non-Javadoc)
	 * @see com.wanda.restful.framework.redis.lock.DistributedLock#check()
	 */
	@Override
	public boolean check() throws Exception{
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			long getVal = getLong(jedis.get(key));
	        return System.currentTimeMillis() < getVal && versionTime == getVal;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.wanda.restful.framework.redis.lock.DistributedLock#heartbeat()
	 */
	@Override
	public boolean heartbeat() throws Exception{
		// 1. 避免操作非自己获取得到的锁
		
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			return check() && getLong(jedis.getSet(key, buildVal())) != 0;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
	}

	/* (non-Javadoc)
	 * @see com.wanda.restful.framework.redis.lock.DistributedLock#unLock()
	 */
	@Override
	public boolean unLock() throws Exception{
		// 1. 避免删除非自己获取得到的锁
		ShardedJedis jedis = shardedJedisPool.getResource();
		try{
			 return check() && jedis.del(key)!=0;
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
       
	}
	
	/**
     * if value==null || value=="" <br>
     * &nbsp return 0 <br>
     * else <br>
     * &nbsp return Long.valueOf(value)
     * 
     * @param value
     * @return
     */
    private long getLong(String value) {
        return StringUtil.isEmpty(value) ? 0 : Long.valueOf(value);
    }

    /**
     * 生成val,当前系统时间+心跳时间
     * 
     * @return System.currentTimeMillis() + heartbeatTime + 1
     */
    private String buildVal() {
        versionTime = System.currentTimeMillis() + heartbeatTime + 1;
        return String.valueOf(versionTime);
    }
    
    private Boolean setnx(String key, String value, int seconds) throws Exception{
		ShardedJedis jedis = shardedJedisPool.getResource();
    	try{
    		
    		long objects=jedis.setnx(key, value);
    		long result=jedis.expire(key, seconds);
    		if(objects==1&&result==1){
    			return true;
    		}
    		
    	}catch(Exception e){
    		return false;
    	}finally{
    		shardedJedisPool.returnResourceObject(jedis);
    	}
    	
    	return false;
    }
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the heartbeatTime
	 */
	public long getHeartbeatTime() {
		return heartbeatTime;
	}

	/**
	 * @param heartbeatTime the heartbeatTime to set
	 */
	public void setHeartbeatTime(long heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	
}
