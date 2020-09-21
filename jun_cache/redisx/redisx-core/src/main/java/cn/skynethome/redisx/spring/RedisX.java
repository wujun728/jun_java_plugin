package cn.skynethome.redisx.spring;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.skynethome.redisx.FileUtil;
import cn.skynethome.redisx.SerializationAndCompressUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.spring]    
  * 文件名称:[RedisX]  
  * 描述:[单redis线程池 spring集成类]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月4日 下午2:04:26]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月4日 下午2:04:26]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisX
{

    private Logger logger = Logger.getLogger(RedisX.class);

    // Redis服务器IP
    private String ADDR_ARRAY = null;

    // Redis的端口号
    private int PORT = 0;

    // 访问密码
    private String AUTH = null;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private int MAX_ACTIVE = 0;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private int MAX_IDLE = 0;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int MAX_WAIT = 0;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private boolean TEST_ON_BORROW = false;

    // 超时时间
    private int TIMEOUT = 0;

    private String configPath = "properties/redis.properties"; // 默认配置

    private String ADDR;

    public String getADDR()
    {
        return ADDR;
    }

    public void setADDR(String aDDR)
    {
        ADDR = aDDR;
    }

    private boolean configFlag = false;

    public boolean isConfigFlag()
    {
        return configFlag;
    }

    public void setConfigFlag(boolean configFlag)
    {
        this.configFlag = configFlag;
    }

    public String getAUTH()
    {
        return AUTH;
    }

    public void setAUTH(String aUTH)
    {
        AUTH = aUTH;
    }

    public int getMAX_ACTIVE()
    {
        return MAX_ACTIVE;
    }

    public void setMAX_ACTIVE(int mAX_ACTIVE)
    {
        MAX_ACTIVE = mAX_ACTIVE;
    }

    public int getMAX_IDLE()
    {
        return MAX_IDLE;
    }

    public void setMAX_IDLE(int mAX_IDLE)
    {
        MAX_IDLE = mAX_IDLE;
    }

    public int getMAX_WAIT()
    {
        return MAX_WAIT;
    }

    public void setMAX_WAIT(int mAX_WAIT)
    {
        MAX_WAIT = mAX_WAIT;
    }

    public int getTIMEOUT()
    {
        return TIMEOUT;
    }

    public void setTIMEOUT(int tIMEOUT)
    {
        TIMEOUT = tIMEOUT;
    }

    public boolean isTEST_ON_BORROW()
    {
        return TEST_ON_BORROW;
    }

    public void setTEST_ON_BORROW(boolean tEST_ON_BORROW)
    {
        TEST_ON_BORROW = tEST_ON_BORROW;
    }

    private JedisPool jedisPool = null;

    public String getConfigPath()
    {
        return configPath;
    }

    public void setConfigPath(String configPath)
    {
        this.configPath = configPath;
    }

    public RedisX()
    {
        super();
    }

    private void initRedisConfigValue()
    {
        if (configFlag)
        {
            String master_server = FileUtil.getPropertyValue(configPath, "master_server");

            String arr[] = master_server.split(":");

            ADDR_ARRAY = arr[0];

            PORT = Integer.valueOf(arr[1]);

            AUTH = FileUtil.getPropertyValue(configPath, "auth");
            MAX_ACTIVE = FileUtil.getPropertyValueInt(configPath, "max_active");
            ;
            MAX_IDLE = FileUtil.getPropertyValueInt(configPath, "max_idle");
            ;
            MAX_WAIT = FileUtil.getPropertyValueInt(configPath, "max_wait");
            ;
            TIMEOUT = FileUtil.getPropertyValueInt(configPath, "timeout");
            ;
            TEST_ON_BORROW = FileUtil.getPropertyValueBoolean(configPath, "test_on_borrow");

            FileUtil.remove(configPath);
        }
        else
        {
            String arr[] = ADDR.split(":");

            ADDR_ARRAY = arr[0];

            PORT = Integer.valueOf(arr[1]);
        }

    }

    /** 
     * 初始化Redis连接池 
     */
    private void initialPool()
    {
        initRedisConfigValue();

        try
        {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if (StringUtils.isEmpty(AUTH))
            {
                jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT);
            }
            else
            {
                jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT, AUTH);
            }

        }
        catch (Exception e)
        {
            logger.error("First create JedisPool error : " + e);
            try
            {
                // 如果第一个IP异常，则访问第二个IP
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(MAX_ACTIVE);
                config.setMaxIdle(MAX_IDLE);
                config.setMaxWaitMillis(MAX_WAIT);
                config.setTestOnBorrow(TEST_ON_BORROW);
                if (StringUtils.isEmpty(AUTH))
                {
                    jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);

                }
                else
                {
                    jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT, AUTH);
                }

            }
            catch (Exception e2)
            {
                logger.error("Second create JedisPool error : " + e2);
            }
        }
    }

    /** 
     * 在多线程环境同步初始化 
     */
    private synchronized void poolInit()
    {
        if (jedisPool == null)
        {
            initialPool();
        }
    }

    /** 
     * 同步获取Jedis实例 
     * @return Jedis 
     */
    public synchronized Jedis getJedis()
    {
        if (jedisPool == null)
        {
            poolInit();
        }
        Jedis jedis = null;
        try
        {
            if (jedisPool != null)
            {
                jedis = jedisPool.getResource();
            }
        }
        catch (Exception e)
        {
            returnBrokenResource(jedis);
            logger.error("Get jedis error : " + e);
        }

        return jedis;
    }

    /** 
     * 释放jedis资源 
     * @param jedis 
     */
    public void returnResource(final Jedis jedis)
    {
        if (jedis != null && jedisPool != null)
        {
            jedisPool.returnResource(jedis);
        }
    }

    /** 
     * 出现异常的时候释放jedis资源 
     * @param jedis 
     */
    public void returnBrokenResource(final Jedis jedis)
    {
        if (null != jedis && null != jedisPool)
        {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /** 
     * 设置 String 
     * @param key 
     * @param value 
     */
    public String setString(String key, String value)
    {
        Jedis jedis = getJedis();
        String r_str = "nil";
        try
        {
            value = StringUtils.isEmpty(value) ? "" : value;
            r_str = jedis.set(key, value);
        }
        catch (Exception e)
        {
            returnBrokenResource(jedis);
            logger.error("Set key error : " + e);
        }
        finally
        {
            returnResource(jedis);
        }

        return r_str;
    }

    /** 
     * 设置 过期时间 
     * @param key 
     * @param seconds 以秒为单位 
     * @param value 
     */
    public String setString(String key, String value, int expirationTime)
    {
        Jedis jedis = getJedis();
        String r_str = "nil";
        try
        {
            value = StringUtils.isEmpty(value) ? "" : value;
            r_str = jedis.setex(key, expirationTime, value);
        }
        catch (Exception e)
        {
            returnBrokenResource(jedis);
            logger.error("Set keyex error : " + e);
        }
        finally
        {
            returnResource(jedis);
        }
        return r_str;
    }

    public Set<byte[]> getKeys(byte[] keys)
    {
        Jedis jedis = getJedis();
        Set<byte[]> sets = jedis.keys(keys);
        returnResource(jedis);
        return sets;
    }

    public Set<String> getKeys(String keys)
    {
        Jedis jedis = getJedis();
        Set<String> sets = jedis.keys(keys);
        returnResource(jedis);
        return sets;
    }

    public Set<byte[]> hkeys(byte[] hkeys)
    {
        Jedis jedis = getJedis();
        Set<byte[]> sets = jedis.hkeys(hkeys);
        returnResource(jedis);
        return sets;
    }

    public Set<String> hkeys(String hkeys)
    {
        Jedis jedis = getJedis();
        Set<String> sets = jedis.hkeys(hkeys);
        returnResource(jedis);
        return sets;
    }

    public List<byte[]> hvals(byte[] hvals)
    {
        Jedis jedis = getJedis();
        List<byte[]> lists = jedis.hvals(hvals);
        returnResource(jedis);
        return lists;
    }

    public List<String> hvals(String hvals)
    {
        Jedis jedis = getJedis();
        List<String> lists = jedis.hvals(hvals);
        returnResource(jedis);
        return lists;
    }

    /** 
     * 获取String值 
     * @param key 
     * @return value 
     */
    public String getString(String key)
    {
        Jedis jedis = getJedis();
        String r_str = null;
        if (jedis == null || !jedis.exists(key))
        {
            returnResource(jedis);
            r_str = null;
        }
        else
        {
            r_str = jedis.get(key);
            returnResource(jedis);
        }

        return r_str;
    }

    public String setObject(String key, Object obj, int expirationTime)
    {
        Jedis jedis = getJedis();
        String r_str = "nil";
        try
        {
            r_str = jedis.setex(key.getBytes(), expirationTime, SerializationAndCompressUtils.fastSerialize(obj));

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis);
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return r_str;

    }

    public String setObject(String key, Object obj)
    {
        Jedis jedis = getJedis();
        String r_str = "nil";
        try
        {
            r_str = jedis.set(key.getBytes(), SerializationAndCompressUtils.fastSerialize(obj));

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis);
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return r_str;

    }

    public Long del(String key)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        try
        {
            r_l = jedis.del(key);

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis);
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return r_l;

    }

    public Long del(byte[] key)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        try
        {
            r_l = jedis.del(key);

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis);
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return r_l;

    }

    public Long del(byte[]... key)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        try
        {
            r_l = jedis.del(key);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis);
            e.printStackTrace();
        }
        finally
        {
            returnResource(jedis);
        }
        return r_l;

    }

    /** 
     * 获取Object 
     * @param key 
     * @return 
     * @return value 
     */
    public <T> T getObject(String key, Class<T> classz)
    {
        Jedis jedis = getJedis();
        T t = null;
        if (jedis == null || !jedis.exists(key))
        {
            t = null;
            returnResource(jedis);
        }
        else
        {
            byte[] value = jedis.get(key.getBytes());
            try
            {

                t = classz.cast(SerializationAndCompressUtils.fastDeserialize(value));

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                returnBrokenResource(jedis);
                e.printStackTrace();
            }
            finally
            {

                returnResource(jedis);
            }
        }

        return t;
    }

    public Long hdel(String key, String field)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        if (jedis == null)
        {
            r_l = 0l;
        }
        else
        {
            try
            {

                r_l = jedis.hdel(key, field);

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                returnResource(jedis);
                jedis = null;
                e.printStackTrace();
            }
            finally
            {
                returnResource(jedis);

            }
        }

        return r_l;
    }

    public Map<String, String> hgetall(String key)
    {
        Jedis jedis = getJedis();
        Map<String, String> map = null;
        if (jedis == null)
        {
            map = null;
        }
        else
        {
            try
            {

                map = jedis.hgetAll(key);

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                returnResource(jedis);
                jedis = null;
                e.printStackTrace();
            }
            finally
            {
                returnResource(jedis);

            }
        }

        return map;

    }

    public Long hincrBy(String key, String field, long value)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        if (jedis == null)
        {
            r_l = 0l;
        }
        else
        {
            try
            {

                r_l = jedis.hincrBy(key, field, value);

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                returnResource(jedis);
                jedis = null;
                e.printStackTrace();
            }
            finally
            {
                returnResource(jedis);

            }
        }

        return r_l;
    }

    public boolean exists(byte[] key)
    {
        Jedis jedis = getJedis();
        boolean isExists = false;

        if (jedis == null)
        {
            isExists = false;
        }
        else
        {
            isExists = jedis.exists(key);
            returnResource(jedis);
        }

        return isExists;
    }

    public boolean exists(String key)
    {
        if (null == key)
        {
            return false;
        }

        return exists(key.getBytes());
    }

    public Long zadd(String key, double score, String member)
    {
        Jedis jedis = getJedis();
        Long r_l = 0l;
        if (jedis == null)
        {
            r_l = 0l;
        }
        else
        {
            r_l = jedis.zadd(key, score, member);
            returnResource(jedis);
        }

        return r_l;
    }

    public Set<String> zrange(String key, long start, long end)
    {

        Set<String> set = null;
        Jedis jedis = getJedis();
        if (jedis == null)
        {
            set = null;

        }
        else
        {
            set = jedis.zrange(key, start, end);
            returnResource(jedis);
        }

        return set;
    }

    public Double zscore(String key, String member)
    {

        Double d_ = 0d;
        Jedis jedis = getJedis();
        if (jedis == null)
        {
            d_ = 0d;

        }
        else
        {
            d_ = jedis.zscore(key, member);
            returnResource(jedis);
        }

        return d_;
    }

}