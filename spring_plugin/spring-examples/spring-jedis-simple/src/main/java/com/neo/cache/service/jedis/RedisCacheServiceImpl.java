package com.neo.cache.service.jedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.neo.cache.service.RedisCacheService;
import com.neo.cache.util.SerializingUtil;

/**
 * Jedis缓存管理
 * @author neo
 */
@Service("redisCacheService")
public class RedisCacheServiceImpl implements RedisCacheService{
	
	private static final Logger logger = Logger.getLogger(RedisCacheServiceImpl.class);
	
	private static final String JEDIS_SET_RETURN_OK = "OK";
	
	@Resource
    protected JedisPool jedisPool;
	
    /**
     * 同步获取Jedis实例
     * @return Jedis
     */
    public synchronized  Jedis getJedis() {  
        Jedis jedis = null;
        try {  
            if (jedisPool != null) {  
                jedis = jedisPool.getResource(); 
            }
        } catch (Exception e) {  
            logger.error("Get jedis from jedisPool,  error : ",e);
        }
        return jedis;
    }  
     
	@Override
	public Object getCache(Serializable cacheKey) {
		Jedis jedis =getJedis();
		Object obj=null;
		try {  
			obj=SerializingUtil.deserialize((byte[]) jedis.get(SerializingUtil.serialize(cacheKey)));
        } catch (Exception e) {  
            logger.error("getCache,  error cacheKey=: "+cacheKey,e);
        }finally{
        	jedis.close();
		}
		return obj;
	}

	@Override
	public boolean putCache(Serializable cacheKey, Object objValue,
			int expiration) {
		Jedis jedis =getJedis();
		try {  
			String result = jedis.setex(SerializingUtil.serialize(cacheKey), expiration, SerializingUtil.serialize(objValue));
			if (StringUtils.equals(JEDIS_SET_RETURN_OK, result)) {
				return true;
			}       
		} catch (Exception e) {  
            logger.error("putCache,  error : cacheKey="+cacheKey+" ,objValue="+objValue+" expiration="+expiration,e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public void removeCache(Serializable cacheKey) {
		Jedis jedis =getJedis();
		try {  
			jedis.del(SerializingUtil.serialize(cacheKey));
        } catch (Exception e) {  
            logger.error("removeCache,  error cacheKey=: "+cacheKey,e);
        }finally{
        	jedis.close();
		}
	}

	@Override
	public boolean putListCache(Serializable cacheKey, Object objValue) {
		Jedis jedis =getJedis();
		try {  
			Long num = jedis.rpush(SerializingUtil.serialize(cacheKey), SerializingUtil.serialize(objValue));
			if (num > 0) {
				return true;
			}        
		} catch (Exception e) {  
            logger.error("putListCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		
		return false;
	}

	@Override
	public boolean putListCache(Serializable cacheKey, Object objValue, int index) {
		Jedis jedis =getJedis();
		try {  
			String result = jedis.lset(SerializingUtil.serialize(cacheKey), index, SerializingUtil.serialize(objValue));
			if (StringUtils.equals(JEDIS_SET_RETURN_OK, result)) {
				return true;
			}    
		} catch (Exception e) {  
            logger.error("putListCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public List<Object> getListCache(Serializable cacheKey, int start, int end) {
		Jedis jedis =getJedis();
		try {  
			List<byte[]> list = jedis.lrange(SerializingUtil.serialize(cacheKey), start, end);
			if (null != list && list.size() > 0) {
				List<Object> objList = new ArrayList<Object>();
				for (byte[] b : list) {
					objList.add(SerializingUtil.deserialize(b));
				}
				return objList;
			}
		} catch (Exception e) {  
            logger.error("getListCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return null;
	}

	@Override
	public List<Object> getListCache(Serializable cacheKey) {
		return getListCache(cacheKey, 0, -1);
	}
	
	@Override
	public boolean trimListCache(Serializable cacheKey, int start, int end) {
		Jedis jedis =getJedis();
		try {  
			String result = jedis.ltrim(SerializingUtil.serialize(cacheKey), start, end);
			if (StringUtils.equals(JEDIS_SET_RETURN_OK, result)) {
				return true;
			}
		} catch (Exception e) {  
            logger.error("trimListCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public boolean putMapCache(Serializable cacheKey, Map<Object, Object> map) {
		Jedis jedis =getJedis();
		try {  
			if (null != map && !map.isEmpty()) {
				Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
				for (Entry<Object, Object> entry: map.entrySet()) {
				    byteMap.put(SerializingUtil.serialize(entry.getKey()), SerializingUtil.serialize(entry.getValue()));
				}
				String result = jedis.hmset(SerializingUtil.serialize(cacheKey), byteMap);
				if (StringUtils.equals(JEDIS_SET_RETURN_OK, result)) {
					return true;
				}
				return true;
			}
		} catch (Exception e) {  
            logger.error("putMapCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public boolean deleteMapCache(Serializable cacheKey, Serializable mapKey) {
		Jedis jedis =getJedis();
		try {  
			Long result = jedis.hdel(SerializingUtil.serialize(cacheKey), SerializingUtil.serialize(mapKey));
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {  
            logger.error("deleteMapCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public Object getMapValueCache(Serializable cacheKey, Serializable mapKey) {
		Jedis jedis =getJedis();
		try {  
			List<byte[]> list = jedis.hmget(SerializingUtil.serialize(cacheKey), SerializingUtil.serialize(mapKey));
			if (null != list && list.size() > 0) {
				return SerializingUtil.deserialize(list.get(0));
			}
		} catch (Exception e) {  
            logger.error("getMapValueCache,  error : ",e);
        }finally{
        	jedis.close();
		}
		return null;
	}
	
	@Override
	public boolean exists(Serializable cacheKey) {
		Jedis jedis =getJedis();
		try {  
			return jedis.exists(SerializingUtil.serialize(cacheKey));
		} catch (Exception e) {  
            logger.error("exists,  error cacheKey=: "+cacheKey,e);
        }finally{
        	jedis.close();
		}
		return false;
	}

	@Override
	public boolean setKeyExpire(String cacheKey, int expiration) {
		Jedis jedis =getJedis();
		try {  
			Long result = jedis.expire(SerializingUtil.serialize(cacheKey), expiration);
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {  
            logger.error("setKeyExpire,  error : cacheKey="+cacheKey+" expiration="+expiration,e);
        }finally{
        	jedis.close();
		}
		return false;
	}
	
}
