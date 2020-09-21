package cn.skynethome.redisx.spring;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.skynethome.redisx.FileUtil;
import cn.skynethome.redisx.cacheclient.RedisxClient;
import cn.skynethome.redisx.common.Constants;
import cn.skynethome.redisx.common.RedisXAioHandler;
import cn.skynethome.redisx.common.RedisXCacheBean;
import cn.skynethome.redisx.common.SerializationAndCompressUtils;
import cn.skynethome.redisx.common.ms.ShardedMasterSlaveJedis;
import cn.skynethome.redisx.common.ms.ShardedMasterSlaveJedisSentinelPool;
import cn.skynethome.redisx.j2cache.Cache;
import cn.skynethome.redisx.j2cache.RedisXEhcache;
import cn.skynethome.redisx.spring.i.RedisX;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.spring]    
  * 文件名称:[RedisXSharedSentinel]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月4日 下午2:47:39]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月4日 下午2:47:39]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisXSharedSentinel extends RedisX
{
    private Logger logger = Logger.getLogger(RedisXSharedSentinel.class);

    // redis_sentinel服务器IP
    private String ADDR_ARRAY = null; // 192.168.0.1:63781,192.168.0.1:63782

    private String MASTER_NAME = null; // 192.168.0.1:63781,192.168.0.1:63782

    // redis_sentinel的端口号
    // private int PORT =
    // FileUtil.getPropertyValueInt("properties/redis_sentinel.properties",
    // "port");

    // 访问密码
    private String AUTH = null;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private int MAX_ACTIVE = 0;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private int MAX_IDLE = 0;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int MAX_WAIT = 0;

    // 超时时间
    private int TIMEOUT = 0;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private boolean TEST_ON_BORROW = false;

    private boolean TEST_ON_RETURN = false;

    private boolean TEST_ON_CREATE = false;

    private boolean TEST_WHILE_IDLE = false;

    private int MIN_IDLE = 0;

    private long idleTimeMillis = 1800000;

    private int numTestsPerEvictionRun = 5;

    private long softMinEvictableIdleTimeMillis = 60000;

    private long timeBetweenEvictionRunsMillis = 3000;

    private Pool<ShardedMasterSlaveJedis> shardedMasterSlaveJedisPool = null;

    private boolean configFlag = false;

    private String configPath = "properties/redis_shared_sentinel.properties";

    private boolean open_level_one_cache;

    private boolean ehcache_shared = false;

    private String ehcache_server = "127.0.0.1:8888";

    private Cache redisxCache = new RedisXEhcache().getCache("redisXSharedSentinel", true);

    private RedisxClient redisxClient = null;

    public RedisXSharedSentinel()
    {
        super();

    }

    public boolean isEhcache_shared()
    {
        return ehcache_shared;
    }

    public void setEhcache_shared(boolean ehcache_shared)
    {
        this.ehcache_shared = ehcache_shared;
    }

    public String getEhcache_server()
    {
        return ehcache_server;
    }

    public void setEhcache_server(String ehcache_server)
    {
        this.ehcache_server = ehcache_server;
    }

    public boolean isOpen_level_one_cache()
    {
        return open_level_one_cache;
    }

    public void setOpen_level_one_cache(boolean open_level_one_cache)
    {
        this.open_level_one_cache = open_level_one_cache;
    }

    public boolean isTEST_ON_RETURN()
    {
        return TEST_ON_RETURN;
    }

    public void setTEST_ON_RETURN(boolean tEST_ON_RETURN)
    {
        TEST_ON_RETURN = tEST_ON_RETURN;
    }

    public boolean isTEST_ON_CREATE()
    {
        return TEST_ON_CREATE;
    }

    public void setTEST_ON_CREATE(boolean tEST_ON_CREATE)
    {
        TEST_ON_CREATE = tEST_ON_CREATE;
    }

    public String getADDR_ARRAY()
    {
        return ADDR_ARRAY;
    }

    public void setADDR_ARRAY(String aDDR_ARRAY)
    {
        ADDR_ARRAY = aDDR_ARRAY;
    }

    public String getMASTER_NAME()
    {
        return MASTER_NAME;
    }

    public void setMASTER_NAME(String mASTER_NAME)
    {
        MASTER_NAME = mASTER_NAME;
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

    public long getTIMEOUT()
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

    public boolean isTEST_WHILE_IDLE()
    {
        return TEST_WHILE_IDLE;
    }

    public void setTEST_WHILE_IDLE(boolean tEST_WHILE_IDLE)
    {
        TEST_WHILE_IDLE = tEST_WHILE_IDLE;
    }

    public int getMIN_IDLE()
    {
        return MIN_IDLE;
    }

    public void setMIN_IDLE(int mIN_IDLE)
    {
        MIN_IDLE = mIN_IDLE;
    }

    public long getIdleTimeMillis()
    {
        return idleTimeMillis;
    }

    public void setIdleTimeMillis(long idleTimeMillis)
    {
        this.idleTimeMillis = idleTimeMillis;
    }

    public int getNumTestsPerEvictionRun()
    {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun)
    {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public long getSoftMinEvictableIdleTimeMillis()
    {
        return softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis)
    {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public long getTimeBetweenEvictionRunsMillis()
    {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis)
    {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public boolean isConfigFlag()
    {
        return configFlag;
    }

    public void setConfigFlag(boolean configFlag)
    {
        this.configFlag = configFlag;
    }

    public String getConfigPath()
    {
        return configPath;
    }

    public void setConfigPath(String configPath)
    {
        this.configPath = configPath;
    }

    private void initRedisConfigValue()
    {
        if (configFlag)
        {
            ADDR_ARRAY = FileUtil.getPropertyValue(configPath, "server"); // 192.168.0.1:63781,192.168.0.1:63782

            MASTER_NAME = FileUtil.getPropertyValue(configPath, "master_name"); // 192.168.0.1:63781,192.168.0.1:63782

            AUTH = FileUtil.getPropertyValue(configPath, "auth");

            MAX_ACTIVE = FileUtil.getPropertyValueInt(configPath, "max_active");

            MAX_IDLE = FileUtil.getPropertyValueInt(configPath, "max_idle");

            MAX_WAIT = FileUtil.getPropertyValueInt(configPath, "max_wait");

            TIMEOUT = FileUtil.getPropertyValueInt(configPath, "timeout");

            TEST_ON_BORROW = FileUtil.getPropertyValueBoolean(configPath, "test_on_borrow");

            TEST_ON_RETURN = FileUtil.getPropertyValueBoolean(configPath, "test_on_return");

            TEST_ON_CREATE = FileUtil.getPropertyValueBoolean(configPath, "test_on_create");

            TEST_WHILE_IDLE = FileUtil.getPropertyValueBoolean(configPath, "test_while_idle");

            MIN_IDLE = FileUtil.getPropertyValueInt(configPath, "min_idle");

            idleTimeMillis = FileUtil.getPropertyValueLong(configPath, "idleTimeMillis");

            numTestsPerEvictionRun = FileUtil.getPropertyValueInt(configPath, "numTestsPerEvictionRun");

            softMinEvictableIdleTimeMillis = FileUtil.getPropertyValueInt(configPath, "softMinEvictableIdleTimeMillis");

            timeBetweenEvictionRunsMillis = FileUtil.getPropertyValueInt(configPath, "timeBetweenEvictionRunsMillis");

            open_level_one_cache = FileUtil.getPropertyValueBoolean(configPath, "open_level_one_cache");

            ehcache_shared = FileUtil.getPropertyValueBoolean(configPath, "ehcache_shared");

            ehcache_server = FileUtil.getPropertyValue(configPath, "ehcache_server");

           

            FileUtil.remove(configPath);
        }
        
        if (ehcache_shared)
        {
            if (!StringUtils.isEmpty(ehcache_server) && null != redisxCache)
            {
                String add[] = ehcache_server.split(":");
                String ip = add[0];
                int port = Integer.valueOf(add[1]);
                redisxClient = new RedisxClient(ip, port, null, new RedisXAioHandler(redisxCache, this));
                redisxClient.sendJoinGroup(Constants.GROUPID);
            }
            else
            {
                try
                {
                    throw new Exception("first level cache or ehcache server config error.");
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 初始化Redis连接池
     */
    private void initialPool()
    {

        initRedisConfigValue();

        JedisPoolConfig config = new JedisPoolConfig();

        // 最大空闲连接数, 默认8个
        config.setMaxIdle(MAX_ACTIVE);

        // 最大连接数, 默认8个
        config.setMaxTotal(MAX_IDLE);

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        config.setMaxWaitMillis(MAX_WAIT);

        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(idleTimeMillis);

        // 最小空闲连接数, 默认0
        config.setMinIdle(MIN_IDLE);

        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);

        // 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
        // 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);

        // 在获取连接的时候检查有效性, 默认false

        // 在空闲时检查有效性, 默认false
        config.setTestWhileIdle(TEST_WHILE_IDLE);

        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(TEST_ON_BORROW);

        config.setTestOnReturn(TEST_ON_RETURN);

        config.setTestOnCreate(TEST_ON_CREATE);

        Set<String> sentinels = new LinkedHashSet<String>();
        if (!StringUtils.isEmpty(ADDR_ARRAY))
        {
            String[] address = ADDR_ARRAY.split(",");
            for (String add : address)
            {
                sentinels.add(add);
            }
        }
        // sentinels.add("127.0.0.1:63792");

        Set<String> masterNames = new LinkedHashSet<String>();

        if (-1 != MASTER_NAME.indexOf(","))
        {
            String arr[] = MASTER_NAME.split(",");
            for (String masterName : arr)
            {
                masterNames.add(masterName);
            }

        }
        else
        {
            masterNames.add(MASTER_NAME);
        }

        shardedMasterSlaveJedisPool = new ShardedMasterSlaveJedisSentinelPool(masterNames, sentinels, config, TIMEOUT,
                AUTH);

        // ShardedMasterSlaveJedis shardedMasterSlaveJedis =
        // shardedshardedMasterSlaveJedisPool.getResource();

        // MasterSlaveJedis masterSlaveJedis =
        // shardedMasterSlaveJedisPool.getResource();
        // masterSlaveJedis.auth(AUTH);
        // //>>> masterSlaveJedis = MasterSlaveJedis
        // {master=192.168.137.101:6379, slaves=[192.168.137.101:6380,
        // 192.168.137.101:6381]}
        // System.out.println(">>> masterSlaveJedis = " + masterSlaveJedis);
        //
        // masterSlaveJedis.set("nowTime", "2015-03-16 15:34:55"); // The
        // underlying actually call the master.set("nowTime", "2015-03-16
        // 15:34:55");
        //
        // LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
        //
        // String slaveHolder1 = "myslave1";
        // Jedis slave1 = masterSlaveJedis.opsForSlave(slaveHolder1); // if no
        // any slave found, opsForSlave() will return master as a slave to be
        // use
        // System.out.println(">>> nowTime = " + slave1.get("nowTime")); //>>>
        // nowTime = 2015-03-16 15:34:55
        //
        // String slaveHolder2 = "myslave1";
        // Jedis slave2 = masterSlaveJedis.opsForSlave(slaveHolder2);
        // System.out.println(">>> nowTime = " + slave2.get("nowTime")); //>>>
        // nowTime = 2015-03-16 15:34:55
        //
        // System.out.println(slave1.equals(slave2)); // must be true if
        // slaveHolder1 equals slaveHolder2
        //
        // shardedMasterSlaveJedisPool.returnResource(masterSlaveJedis);

    }

    /**
     * 在多线程环境同步初始化
     */
    protected synchronized void poolInit()
    {
        if (shardedMasterSlaveJedisPool == null)
        {
            initialPool();
        }
    }

    /**
     * 同步获取Jedis实例
     * 
     * @return Jedis
     */
    private synchronized ShardedMasterSlaveJedis getJedis()
    {
        if (shardedMasterSlaveJedisPool == null)
        {
            poolInit();
        }
        ShardedMasterSlaveJedis jedis = null;
        try
        {
            if (shardedMasterSlaveJedisPool != null)
            {
                jedis = shardedMasterSlaveJedisPool.getResource();
            }
        }
        catch (Exception e)
        {
            returnResource(jedis);
            logger.error("Get jedis error : " + e);
        }
        finally
        {

        }

        return jedis;
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    private void returnResource(final ShardedMasterSlaveJedis jedis)
    {
        if (jedis != null)
        {
            jedis.close();
        }
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    @Deprecated
    private void returnBrokenResource(final ShardedMasterSlaveJedis jedis)
    {
        if (jedis != null && null != shardedMasterSlaveJedisPool)
        {
            shardedMasterSlaveJedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 设置 String
     * 
     * @param key
     * @param value
     */
    public String setString(String key, String value)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
        String r_str = "nil";
        try
        {
            value = StringUtils.isEmpty(value) ? "" : value;
            shardedMasterSlaveJedis = getJedis();
            r_str = shardedMasterSlaveJedis.set(key, value);

            if (open_level_one_cache && "OK".equals(r_str))
            {
                redisxCache.put(key, value);

                if (ehcache_shared)
                {
                    redisxClient.send(SerializationAndCompressUtils
                            .fastSerialize(new RedisXCacheBean(Constants.SET, key, Constants.STRING)));
                }
            }

        }
        catch (Exception e)
        {
            logger.error("Set key error : " + e);
        }
        finally
        {
            returnResource(shardedMasterSlaveJedis);
        }

        return r_str;
    }

    /**
     * 设置 过期时间
     * 
     * @param key
     * @param seconds
     *            以秒为单位
     * @param value
     */
    public String setString(String key, String value, int expirationTime)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
        String r_str = "nil";
        try
        {
            value = StringUtils.isEmpty(value) ? "" : value;
            shardedMasterSlaveJedis = getJedis();
            r_str = shardedMasterSlaveJedis.setex(key, expirationTime, value);

            if (open_level_one_cache && "OK".equals(r_str))
            {
                redisxCache.put(key, value);

                if (ehcache_shared)
                {
                    redisxClient.send(SerializationAndCompressUtils
                            .fastSerialize(new RedisXCacheBean(Constants.SET, key, Constants.STRING)));
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Set keyex error : " + e);
        }
        finally
        {
            returnResource(shardedMasterSlaveJedis);
        }

        return r_str;
    }

    public Set<byte[]> getKeys(byte[] keys)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Set<byte[]> set_ = shardedMasterSlaveJedis.getShard(keys).opsForSlave().keys(keys);
        returnResource(shardedMasterSlaveJedis);
        return set_;
    }

    public Set<String> getKeys(String keys)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Set<String> set_ = shardedMasterSlaveJedis.getShard(keys).opsForSlave().keys(keys);
        returnResource(shardedMasterSlaveJedis);
        return set_;
    }

    public Set<byte[]> hkeys(byte[] hkeys)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Set<byte[]> set_ = shardedMasterSlaveJedis.getShard(hkeys).opsForSlave().hkeys(hkeys);
        returnResource(shardedMasterSlaveJedis);
        return set_;
    }

    public Set<String> hkeys(String hkeys)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Set<String> set_ = shardedMasterSlaveJedis.getShard(hkeys).opsForSlave().hkeys(hkeys);
        returnResource(shardedMasterSlaveJedis);
        return set_;

    }

    public Collection<byte[]> hvals(byte[] hvals)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Collection<byte[]> collection = shardedMasterSlaveJedis.getShard(hvals).opsForSlave().hvals(hvals);
        returnResource(shardedMasterSlaveJedis);
        return collection;
    }

    public List<String> hvals(String hvals)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        List<String> list = shardedMasterSlaveJedis.getShard(hvals).opsForSlave().hvals(hvals);
        returnResource(shardedMasterSlaveJedis);
        return list;
    }

    /**
     * 获取String值
     * 
     * @param key
     * @return value
     */
    public String getString(String key)
    {
        poolInit();
        
        Object obj = null;
        String r_str = null;

        if (open_level_one_cache)
        {
            obj = redisxCache.get(key);
        }

        if (null != obj && obj instanceof String)
        {
            r_str = String.valueOf(obj);

        }
        else
        {
            ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();

            if (shardedMasterSlaveJedis == null || !shardedMasterSlaveJedis.exists(key))
            {
                r_str = null;
            }
            else
            {
                r_str = shardedMasterSlaveJedis.getShard(key).opsForSlave().get(key);

                if (!StringUtils.isEmpty(r_str))
                {
                    redisxCache.put(key, r_str);

                    // if(ehcache_shared)
                    // {
                    // try
                    // {
                    // redisxClient.send(SerializationAndCompressUtils.fastSerialize(new
                    // RedisXCacheBean(Constants.SET,key,Constants.STRING)));
                    // }
                    // catch (JRedisCacheException e)
                    // {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }

                }

                
            }
            
            if(null != shardedMasterSlaveJedis)
            {
                returnResource(shardedMasterSlaveJedis);
            }

        }

        return r_str;
    }

    public String setObject(String key, Object obj, int expirationTime)
    {
        String r_str = null;

        if (null == obj)
        {
            r_str = "nil";
        }
        else
        {
            ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
            try
            {
                shardedMasterSlaveJedis = getJedis();
                byte[] bytes = SerializationAndCompressUtils.fastSerialize(obj);
                r_str = shardedMasterSlaveJedis.setex(key.getBytes(), expirationTime, bytes);

                if (open_level_one_cache && "OK".equals(r_str))
                {
                    redisxCache.put(key, obj);

                    if (ehcache_shared)
                    {
                        redisxClient.send(SerializationAndCompressUtils
                                .fastSerialize(new RedisXCacheBean(Constants.SET, key, Constants.OBJECT)));
                    }
                }

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }
        }

        return r_str;

    }

    public String setObject(String key, Object obj)
    {
        String r_str = null;

        if (null == obj)
        {
            r_str = "nil";
        }
        else
        {
            ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
            
            try
            {

                shardedMasterSlaveJedis = getJedis();
                byte[] bytes = SerializationAndCompressUtils.fastSerialize(obj);
                r_str = shardedMasterSlaveJedis.set(key.getBytes(), bytes);

                if (open_level_one_cache && "OK".equals(r_str))
                {
                    redisxCache.put(key, obj);

                    if (ehcache_shared)
                    {
                        redisxClient.send(SerializationAndCompressUtils
                                .fastSerialize(new RedisXCacheBean(Constants.SET, key, Constants.OBJECT)));
                    }

                }

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }
        }

        return r_str;

    }

    public long del(String key)
    {
        long r_l = 0l;
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
        try
        {
            shardedMasterSlaveJedis = getJedis();
            r_l = shardedMasterSlaveJedis.del(key);

            if (open_level_one_cache && r_l > 0)
            {
                redisxCache.evict(key);

                if (ehcache_shared)
                {
                    redisxClient.send(SerializationAndCompressUtils
                            .fastSerialize(new RedisXCacheBean(Constants.DEL, key, Constants.STRING)));
                }

            }

        }
        catch (Exception e)
        {
            // return jedis object to pll and set jedis is null.
            e.printStackTrace();
        }
        finally
        {

            returnResource(shardedMasterSlaveJedis);

        }
        return r_l;

    }

    public long del(byte[] key)
    {
        long r_l = 0l;
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
        try
        {
            shardedMasterSlaveJedis = getJedis();
            r_l = shardedMasterSlaveJedis.del(key);

            if (open_level_one_cache && r_l > 0)
            {
                String s_key = String.valueOf(key);
                redisxCache.evict(s_key);

                if (ehcache_shared)
                {
                    redisxClient.send(SerializationAndCompressUtils
                            .fastSerialize(new RedisXCacheBean(Constants.DEL, s_key, Constants.STRING)));
                }

            }

        }
        catch (Exception e)
        {
            // return jedis object to pll and set jedis is null.
            e.printStackTrace();
        }
        finally
        {

            returnResource(shardedMasterSlaveJedis);

        }
        return r_l;

    }

    /**
     * 获取Object
     * 
     * @param key
     * @return
     * @return value
     */
    public <T> T getObject(String key, Class<T> classz)
    {

        poolInit();
        
        T t = null;
        Object obj = null;

        if (open_level_one_cache)
        {
            obj = redisxCache.get(key);
        }

        if (null != obj && classz.isInstance(obj))
        {
            t = classz.cast(obj);
        }
        else
        {
            ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
            try
            {

                shardedMasterSlaveJedis = getJedis();
                if (shardedMasterSlaveJedis == null || !shardedMasterSlaveJedis.exists(key.getBytes()))
                {
                    t = null;
                }
                else
                {
                    byte[] value = shardedMasterSlaveJedis.get(key.getBytes());

                    if (null == value)
                    {
                        t = null;
                    }
                    else
                    {
                        t = classz.cast(SerializationAndCompressUtils.fastDeserialize(value));

                        if (null != t)
                        {
                            redisxCache.put(key, t);

                            // if(ehcache_shared)
                            // {
                            // redisxClient.send(SerializationAndCompressUtils.fastSerialize(new
                            // RedisXCacheBean(Constants.SET,key,Constants.OBJECT)));
                            //
                            // }
                        }

                    }
                }

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }

        }

        return t;
    }

    public Object getObject(String key)
    {

        poolInit();
        
        Object obj = null;

        if (open_level_one_cache)
        {
            obj = redisxCache.get(key);
        }

        if (null != obj)
        {

        }
        else
        {
            ShardedMasterSlaveJedis shardedMasterSlaveJedis = null;
            try
            {

                shardedMasterSlaveJedis = getJedis();
                if (shardedMasterSlaveJedis == null || !shardedMasterSlaveJedis.exists(key.getBytes()))
                {
                    
                }
                else
                {
                    byte[] value = shardedMasterSlaveJedis.get(key.getBytes());

                    if (null == value)
                    {
                        obj = null;
                    }
                    else
                    {
                        obj = SerializationAndCompressUtils.fastDeserialize(value);

                        if (null != obj)
                        {
                            redisxCache.put(key, obj);

                            // if(ehcache_shared)
                            // {
                            // redisxClient.send(SerializationAndCompressUtils.fastSerialize(new
                            // RedisXCacheBean(Constants.SET,key,Constants.OBJECT)));
                            //
                            // }
                        }

                    }
                }

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }

        }

        return obj;
    }

    public long hdel(String key, String field)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        long r_l = 0l;
        if (shardedMasterSlaveJedis == null)
        {
            r_l = 0l;
        }
        else
        {
            try
            {
                r_l = shardedMasterSlaveJedis.hdel(key, field);

                if (open_level_one_cache && r_l > 0)
                {
                    redisxCache.evict(String.valueOf(key));

                    if (ehcache_shared)
                    {
                        redisxClient.send(SerializationAndCompressUtils
                                .fastSerialize(new RedisXCacheBean(Constants.DEL, key, Constants.STRING)));
                    }
                }

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }
        }
        return r_l;
    }

    public Map<String, String> hgetall(String key)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        Map<String, String> map = null;
        if (shardedMasterSlaveJedis == null)
        {
            map = null;
        }
        else
        {
            try
            {

                map = shardedMasterSlaveJedis.hgetAll(key);

            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }
        }

        return map;
    }

    public long hincrBy(String key, String field, long value)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        long r_l = 0l;
        if (shardedMasterSlaveJedis == null)
        {
            r_l = 0l;
        }
        else
        {
            try
            {
                r_l = shardedMasterSlaveJedis.hincrBy(key, field, value);
            }
            catch (Exception e)
            {
                // return jedis object to pll and set jedis is null.
                e.printStackTrace();
            }
            finally
            {

                returnResource(shardedMasterSlaveJedis);

            }
        }

        return r_l;
    }

    public boolean exists(byte[] key)
    {

        poolInit();

        boolean isExists = false;

        if (open_level_one_cache)
        {
            isExists = redisxCache.exists(String.valueOf(key));
        }

        if (isExists)
        {
            isExists = true;
        }
        else
        {

            ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();

            if (shardedMasterSlaveJedis == null)
            {
                isExists = false;
            }
            else
            {
                isExists = shardedMasterSlaveJedis.exists(key);
                returnResource(shardedMasterSlaveJedis);
            }
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

    public long zadd(String key, double score, String member)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        long r_l = 0l;
        if (shardedMasterSlaveJedis == null)
        {
            r_l = 0l;
        }
        else
        {
            r_l = shardedMasterSlaveJedis.zadd(key, score, member);
            returnResource(shardedMasterSlaveJedis);
        }

        return r_l;
    }

    public Set<String> zrange(String key, long start, long end)
    {

        Set<String> set = null;
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        if (shardedMasterSlaveJedis == null)
        {
            set = null;

        }
        else
        {
            set = shardedMasterSlaveJedis.zrange(key, start, end);
            returnResource(shardedMasterSlaveJedis);
        }

        return set;
    }

    public double zscore(String key, String member)
    {

        double d_ = 0d;
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();
        if (shardedMasterSlaveJedis == null)
        {
            d_ = 0d;

        }
        else
        {
            d_ = shardedMasterSlaveJedis.zscore(key, member);
            returnResource(shardedMasterSlaveJedis);
        }

        return d_;
    }

    public long publish(String channel, String member)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();

        long r_ = 0l;
        if (null != shardedMasterSlaveJedis)
        {
            r_ = shardedMasterSlaveJedis.publish(channel, member);
            returnResource(shardedMasterSlaveJedis);

        }
        else
        {

        }

        return r_;
    }

    public long publish(byte[] channel, byte[] member)
    {
        ShardedMasterSlaveJedis shardedMasterSlaveJedis = getJedis();

        long r_ = 0l;
        if (null != shardedMasterSlaveJedis)
        {
            r_ = shardedMasterSlaveJedis.publish(channel, member);
            returnResource(shardedMasterSlaveJedis);

        }
        else
        {

        }

        return r_;

    }

    public void destroy()
    {
        if (null != shardedMasterSlaveJedisPool)
        {
            shardedMasterSlaveJedisPool.destroy();

        }

        if (null != redisxCache)
        {
            redisxCache.destroy();
        }
    }

    public int getActiveConnections()
    {
        if (null != shardedMasterSlaveJedisPool)
        {
            return shardedMasterSlaveJedisPool.getNumActive();

        }
        else
        {
            return 0;
        }

    }

    public int getIdleConnections()
    {
        if (null != shardedMasterSlaveJedisPool)
        {
            return shardedMasterSlaveJedisPool.getNumIdle();

        }
        else
        {
            return 0;
        }

    }

}
