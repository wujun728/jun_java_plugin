package com.tanghd.cache.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtil {

    private JedisPool masterPool;
    private JedisPool slavePool;

    public JedisPool getMasterPool() {
        return masterPool;
    }

    public JedisPool getSlavePool() {
        return slavePool;
    }

    public void setMasterPool(JedisPool masterPool) {
        this.masterPool = masterPool;
    }

    public void setSlavePool(JedisPool slavePool) {
        this.slavePool = slavePool;
    }

    public byte[] getAndReturn(int dbIndex, final byte[] key) {
        Jedis jedis = null;
        JedisPool jedisPool = null == slavePool ? masterPool : slavePool;
        try {
            jedis = jedisPool.getResource();
            if (dbIndex != 0) {
                jedis.select(dbIndex);
            }
            return jedis.get(key);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            throw e;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public String setAndReturn(int dbIndex, final byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        JedisPool jedisPool = null;
        try {
            jedisPool = masterPool;
            jedis = jedisPool.getResource();

            if (dbIndex != 0) {
                jedis.select(dbIndex);
            }
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
            throw e;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }
    
    /**
     * 
     * @param dbIndex
     * @param key
     * @return -2 key not exists ; -1 not timeout
     */
    public Long ttl(int dbIndex, String key) {
        long time = -3;
        JedisPool pool = masterPool;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(dbIndex);
            time = jedis.ttl(key);
        } catch (Exception e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
        return time;
    }

    public long setnx(int dbIndex, String key, String value,int expire) {
        long result = 0;
        JedisPool pool = masterPool;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(dbIndex);
            result = jedis.setnx(key, value);
            if(1 == result){
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
        return result;
    }

    public void delete(int dbIndex, String key) {
        Jedis jedis = null;
        try {
            jedis = masterPool.getResource();
            jedis.select(dbIndex);
            jedis.del(key);
        } catch (Exception e) {
            if (null != jedis) {
                masterPool.returnBrokenResource(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (null != jedis) {
                masterPool.returnResource(jedis);
            }
        }
    }

}
