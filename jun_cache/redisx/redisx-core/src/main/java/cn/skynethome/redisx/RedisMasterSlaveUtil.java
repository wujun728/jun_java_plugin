package cn.skynethome.redisx;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.skynethome.redisx.spring.RedisXMasterSlave;
import cn.skynethome.redisx.spring.i.RedisX;

/**
 * 项目名称:[redisx] 
 * 包:[cn.skynethome.redisx] 
 * 文件名称:[RedisMasterSlaveUtil] 
 * 描述:[Redis
 * 工具类-RedisMasterSlave] 
 * 创建人:[陆文斌] 
 * 创建时间:[2016年12月5日 下午5:37:53] 
 * 修改人:[陆文斌]
 * 修改时间:[2016年12月5日 下午5:37:53] 
 * 修改备注:[说明本次修改内容] 
 * 版权所有:luwenbin006@163.com
 * 版本:[v1.0]
 */
public class RedisMasterSlaveUtil
{

    private static RedisX redisX = null;

    public synchronized static RedisX getRedisX()
    {
        if (null == redisX)
        {
            redisX = new RedisXMasterSlave();
            redisX.setConfigFlag(true);
            redisX.setConfigPath("classpath:properties/redis_master_salve.properties");
        }

        return redisX;
    }

    /**
     * 设置 String
     * 
     * @param key
     * @param value
     */
    public static String setString(String key, String value)
    {
        return getRedisX().setString(key, value);
    }

    /**
     * 设置 过期时间
     * 
     * @param key
     * @param seconds
     *            以秒为单位
     * @param value
     */
    public static String setString(String key, String value, int expirationTime)
    {
        return getRedisX().setString(key, value, expirationTime);
    }

    public static Set<byte[]> getKeys(byte[] keys)
    {
        return getRedisX().getKeys(keys);
    }

    public static Set<String> getKeys(String keys)
    {
        return getRedisX().getKeys(keys);
    }

    public static Set<byte[]> hkeys(byte[] hkeys)
    {
        return getRedisX().hkeys(hkeys);
    }

    public static Set<String> hkeys(String hkeys)
    {
        return getRedisX().hkeys(hkeys);

    }

    public static Collection<byte[]> hvals(byte[] hvals)
    {
        return getRedisX().hvals(hvals);
    }

    public static List<String> hvals(String hvals)
    {
        return getRedisX().hvals(hvals);
    }

    /**
     * 获取String值
     * 
     * @param key
     * @return value
     */
    public static String getString(String key)
    {
        return getRedisX().getString(key);
    }

    public static String setObject(String key, Object obj, int expirationTime)
    {
        return getRedisX().setObject(key, obj, expirationTime);

    }

    public static String setObject(String key, Object obj)
    {
        return getRedisX().setObject(key, obj);

    }

    public static Long del(String key)
    {
        return getRedisX().del(key);

    }

    public static Long del(byte[] key)
    {
        return getRedisX().del(key);

    }

    public static boolean exists(byte[] key)
    {
        return getRedisX().exists(key);
    }

    public static boolean exists(String key)
    {
        return getRedisX().exists(key);
    }

    /**
     * 获取Object
     * 
     * @param key
     * @return
     * @return value
     */
    public static <T> T getObject(String key, Class<T> classz)
    {
        return getRedisX().getObject(key, classz);
    }
    
    public static Object getObject(String key)
    {
        return getRedisX().getObject(key);
    }

    public static Long hdel(String key, String field)
    {
        return getRedisX().hdel(key, field);
    }

    public static Map<String, String> hgetall(String key)
    {
        return getRedisX().hgetall(key);
    }

    public static Long hincrBy(String key, String field, long value)
    {
        return getRedisX().hincrBy(key, field, value);
    }

    public static Long zadd(String key, double score, String member)
    {
        return getRedisX().zadd(key, score, member);
    }

    public static Set<String> zrange(String key, long start, long end)
    {

        return getRedisX().zrange(key, start, end);
    }

    public static Double zscore(String key, String member)
    {

        return getRedisX().zscore(key, member);
    }
    public static int getActiveConnections()
    {
         return getRedisX().getActiveConnections();  
    }
    
    public static int getIdleConnections()
    {
       return getRedisX().getIdleConnections();
    }
}