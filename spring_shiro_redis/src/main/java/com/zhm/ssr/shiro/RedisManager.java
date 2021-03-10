package com.zhm.ssr.shiro;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by zhm on 2015/7/10.
 */
public class RedisManager {

    private String host = "127.0.0.1";

    private int port = 6379;

    // 0 - never expire
    private int expire = 0;

    //timeout for jedis try to connect to redis server, not expire time! In milliseconds
    private int timeout = 0;

    private String password = "";

    private static JedisPool jedisPool = null;
    private Ehcache ehCache = null;
    private CacheManager ehcacheManager=null;

    public RedisManager() {

    }

    protected InputStream getCacheManagerConfigFileInputStream() {
        String configFile = "classpath:ehcache.xml";
        try {
            return ResourceUtils.getInputStreamForPath(configFile);
        } catch (IOException e) {
            throw new ConfigurationException("Unable to obtain input stream for cacheManagerConfigFile [" +
                    configFile + "]", e);
        }
    }
    /**
     * 初始化方法
     */
    public void init() {
        if (jedisPool == null) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
            }

        }
        if(ehCache == null){
            ehcacheManager = CacheManager.create();
            if(ehcacheManager==null){
                ehcacheManager = new CacheManager(getCacheManagerConfigFileInputStream());
            }
            ehCache = ehcacheManager.getCache("shirocache");
        }
    }

    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {

        byte[] value = null;
        Jedis jedis = jedisPool.getResource();
        try {
            Element element = ehCache.get(new String(key));
            if(element==null){
                value = jedis.get(key);
                Element putElement = new Element(new String(key), value);
                ehCache.put(putElement);
            }
            else{
                value = (byte[])element.getObjectValue();
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        /**
         * shiro session onChange就会调用该方法，刷新页面也会调用。
         * 第一次登陆会重复写很多遍
         */
        Element element = ehCache.get(new String(key));
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            Element putElement = new Element(new String(key), value);
            ehCache.put(putElement);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            Element putElement = new Element(new String(key), value);
            ehCache.put(putElement);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * del
     *
     * @param key
     */
    public void del(byte[] key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
            ehCache.remove(new String(key));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * flush
     */
    public void flushDB() {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.flushDB();
            ehCache.removeAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * size
     */
    public Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = jedisPool.getResource();
        try {
            dbSize = jedis.dbSize();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return dbSize;
    }

    /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = jedisPool.getResource();
        try {
            keys = jedis.keys(pattern.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
        return keys;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public CacheManager getEhcacheManager(){
        return this.ehcacheManager;
    }
}
