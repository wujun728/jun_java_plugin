package com.feng.cache.redis;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;


public class RedisCacheFactoryBean implements Cache {

  private RedisTemplate<String, Object> redisTemplate;
  private String name;
  private long liveTime;

  @Override
  public Object getNativeCache() {
    return this.redisTemplate;
  }

  @Override
  public ValueWrapper get(Object key) {
    final String keyf = (String) key;
    Object object = null;
    object = redisTemplate.execute(new RedisCallback<Object>() {
      public Object doInRedis(RedisConnection connection) throws DataAccessException {
        byte[] key = keyf.getBytes();
        connection.select(1);
        byte[] value = connection.get(key);
        if (value == null) {
          return null;
        }
        return toObject(value);
      }
    });
    return (object != null ? new SimpleValueWrapper(object) : null);
  }

  @Override
  public void put(Object key, Object value) {
    final String keyf = (String) key;
    final Object valuef = value;

    redisTemplate.execute(new RedisCallback<Long>() {
      public Long doInRedis(RedisConnection connection) throws DataAccessException {
        byte[] keyb = keyf.getBytes();
        byte[] valueb = toByteArray(valuef);
        connection.select(1);
        connection.set(keyb, valueb);
        if (liveTime > 0) {
          connection.expire(keyb, liveTime);
        }
        return 1L;
      }
    });
  }

  /**
   * 描述 : <Object转byte[]>. <br>
   * <p>
   * <使用方法说明>
   * </p>
   * 
   * @param obj
   * @return
   */
  private byte[] toByteArray(Object obj) {
    byte[] bytes = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(obj);
      oos.flush();
      bytes = bos.toByteArray();
      oos.close();
      bos.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return bytes;
  }

  /**
   * 描述 : <byte[]转Object>. <br>
   * <p>
   * <使用方法说明>
   * </p>
   * 
   * @param bytes
   * @return
   */
  private Object toObject(byte[] bytes) {
    Object obj = null;
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bis);
      obj = ois.readObject();
      ois.close();
      bis.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    return obj;
  }

  @Override
  public void evict(Object key) {
    final String keyf = (String) key;
    redisTemplate.execute(new RedisCallback<Long>() {
      public Long doInRedis(RedisConnection connection) throws DataAccessException {
        return connection.del(keyf.getBytes());
      }
    });
  }

  @Override
  public void clear() {
    redisTemplate.execute(new RedisCallback<String>() {
      public String doInRedis(RedisConnection connection) throws DataAccessException {
        connection.flushDb();
        return "ok";
      }
    });
  }

	public RedisTemplate<String, Object> getRedisTemplate() {
	    return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
  	public String getName() {
		return this.name;
	}
	
	public long getLiveTime() {
		return liveTime;
	}
	
	public void setLiveTime(long liveTime) {
		this.liveTime = liveTime;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}
}