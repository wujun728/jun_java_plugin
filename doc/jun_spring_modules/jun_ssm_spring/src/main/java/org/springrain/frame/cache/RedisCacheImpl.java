package org.springrain.frame.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springrain.frame.util.SerializeUtils;

public class RedisCacheImpl implements ICache {
	public RedisCacheImpl() {

	}
	// -1 - never expire
    private Long expire = 1800000L;
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String del(final byte[] key) throws Exception {
		redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.del(key);
				return null;
			}
		});
		return null;
	}

	@Override
	public void set(final byte[] key,  final byte[] value, final Long expireMillisecond)
			throws Exception {
		  redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(final RedisConnection connection)
					throws DataAccessException {
				
				Expiration ex=null;
				if(expireMillisecond==null){
					ex=Expiration.milliseconds(getExpire());
				}else {
					ex=Expiration.milliseconds(expireMillisecond);
				}
				
				connection.set(key, value,ex,null);
				return true;
			}
		});
		
	}
	
	
	@Override
	public void set(byte[] key, byte[] value) throws Exception {
		 set(key, value, null);
	}

	@Override
	public Boolean setNX(byte[] key, byte[] value) throws Exception {
		return setNX(key, value, null);
	}

	@Override
	public Boolean setNX(byte[] key, byte[] value, Long expireMillisecond) throws Exception {

		 return  redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(final RedisConnection connection)
						throws DataAccessException {
					
					Expiration ex=null;
					if(expireMillisecond==null){
						ex=Expiration.milliseconds(getExpire());
					}else {
						ex=Expiration.milliseconds(expireMillisecond);
					}
					
					Boolean setNX = connection.setNX(key, value);
					if(!setNX) {
						return setNX;
					}
					
					Boolean pExpire = connection.pExpire(key, ex.getExpirationTime());
					
					return true;
				}
			});
	
	}

	

	@Override
	public Object get(final byte[] key) throws Exception {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] bs = connection.get(key);
				return SerializeUtils.unserialize(bs);
			}
		});

	}


	
	@Override
	public Set keys(final byte[] keys) throws Exception {
		return redisTemplate.execute(new RedisCallback<Set>() {
			
			@Override
			public Set doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> setByte = connection.keys(keys);
				if (CollectionUtils.isEmpty(setByte)) {
					return null;
				}
				Set set = new HashSet();
				for (byte[] key : setByte) {
					byte[] bs = connection.get(key);
					set.add(SerializeUtils.unserialize(bs));
				}

				return set;

			}
		});
	}


	
	@Override
	public Set hKeys(final byte[] key) throws Exception {
		return (Set) redisTemplate.execute(new RedisCallback<Set>() {
			
			@Override
			public Set doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> hKeys = connection.hKeys(key);
				if(CollectionUtils.isEmpty(hKeys)){
					return null;
				}
				Set set=new HashSet();
				for(byte[] bs:hKeys){
					set.add(SerializeUtils.unserialize(bs));
				}
			return set;
			}
		});

	}

	@Override
	public Boolean  hSet(final byte[] key,final byte[] mapkey, final byte[] value, Long expire)
			throws Exception {
	
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				Boolean hSet = connection.hSet(key, mapkey, value);
				return hSet;
			}
		});
	}

	@Override
	public Object hGet(final byte[] key, final byte[] mapkey) throws Exception {
		return redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] hGet = connection.hGet(key, mapkey);
				return SerializeUtils.unserialize(hGet);

			}
		});
	}
	
	
	@Override
	public Long hDel(final byte[] key, final byte[] mapkey) throws Exception {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long hDel = connection.hDel(key, mapkey);
				return hDel;

			}
		});
	}
	
	
	@Override
	public Long hLen(final byte[] key) throws Exception {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long len = connection.hLen(key);
				
				return len;

			}
		});
	}

	
	@Override
	public Long dbSize() throws Exception {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long len = connection.dbSize();
				
				return len;

			}
		});
	}

	@Override
	public void flushDb() throws Exception {
		 redisTemplate.execute(new RedisCallback<Long>() {
			 @Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				  connection.flushDb();
				return null;

			}
		});
	}

	
	@Override
	public List hVals(final byte[] key) throws Exception {
		return redisTemplate.execute(new RedisCallback<List>() {
			@Override
			public List doInRedis(RedisConnection connection)
					throws DataAccessException {
				 List<byte[]> hVals = connection.hVals(key);
				
				 if(CollectionUtils.isEmpty(hVals)){
					 return null;
				 }
				 List list=new ArrayList();
				 
				 for(byte[] bs:hVals){
					 list.add(SerializeUtils.unserialize(bs));
				 }
				return list;

			}
		});
	}


	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}



	

}
