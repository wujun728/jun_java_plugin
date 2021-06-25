package net.oschina.j2cache;

/**
 * 缓存通信通道接口类
 *
 * @author FY
 */
public interface CacheBroadcastChannel {

    void onDeleteCacheKey(String region, Object key) throws CacheException;

    /**
     * 发送缓存变更的广播
     * @param optKey cache opt key
     * @param region cache region
     * @param key cache key
     * @throws CacheException 抛出异常
     */
    void sendCmdBroadcast(byte optKey, String region, Object key) throws CacheException;

}
