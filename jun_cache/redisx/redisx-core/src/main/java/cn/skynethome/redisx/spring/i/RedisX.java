package cn.skynethome.redisx.spring.i;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class RedisX
{
    
    public RedisX()
    {
       
    }
    
    public abstract void setConfigFlag(boolean configFlag);
    
    public abstract void setConfigPath(String configPath);
    
    public abstract String setString(String key, String value);

    public abstract String setString(String key, String value, int expirationTime);

    public abstract Set<byte[]> getKeys(byte[] keys);

    public abstract Set<String> getKeys(String keys);

    public abstract Set<byte[]> hkeys(byte[] hkeys);

    public abstract Set<String> hkeys(String hkeys);

    public abstract Collection<byte[]> hvals(byte[] hvals);
    
    public abstract List<String> hvals(String hvals);

    public abstract String getString(String key);

    public abstract String setObject(String key, Object obj, int expirationTime);

    public abstract String setObject(String key, Object obj);

    public abstract long del(String key);

    public abstract long del(byte[] key);

    public abstract <T> T getObject(String key, Class<T> classz);
    
    public abstract Object getObject(String key);

    public abstract long hdel(String key, String field);

    public abstract Map<String, String> hgetall(String key);

    public abstract long hincrBy(String key, String field, long value);

    public abstract boolean exists(byte[] key);

    public abstract boolean exists(String key);

    public abstract long zadd(String key, double score, String member);

    public abstract Set<String> zrange(String key, long start, long end);

    public abstract double zscore(String key, String member);

    public abstract long publish(String channel, String member);

    public abstract long publish(byte[] channel, byte[] member);

    public abstract void destroy();

    public abstract int getActiveConnections();

    public abstract int getIdleConnections();
    
    protected abstract void poolInit();

}
