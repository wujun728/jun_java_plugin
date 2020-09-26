package net.oschina.j2cache.broadcast;

import net.oschina.j2cache.store.redis.RedisCacheProvider;
import net.oschina.j2cache.utils.CacheCustoms;
import net.oschina.j2cache.CacheBroadcastChannel;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.util.List;

/**
 * @author Wujun
 */
public class RedisBroadcastChannel extends BinaryJedisPubSub implements CacheExpiredListener, CacheBroadcastChannel {
    private static final Logger logger = LoggerFactory.getLogger(RedisBroadcastChannel.class);

    private CacheFactory factory;
    private String clusterName;

    private final Thread threadSubscribe;
//    private static final RedisBroadcastChannel subscribe = new RedisBroadcastChannel();

    public RedisBroadcastChannel(final String clusterName, final CacheFactory factory) {
        this.factory = factory;
        this.clusterName = clusterName;

        try {
            long startTime = System.currentTimeMillis();
            threadSubscribe = new Thread(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = RedisCacheProvider.getResource();
                    jedis.subscribe(RedisBroadcastChannel.this, SafeEncoder.encode(clusterName));
                    RedisCacheProvider.returnResource(jedis);
                }
            });
            threadSubscribe.start();

            logger.info("成功创建缓存广播通道(Redis pub/sub) : {}, 耗时 : {}", clusterName, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            throw new CacheException("初始化Redis Subscribe线程失败.", e);
        }

    }

    @Override
    public void onDeleteCacheKey(String region, Object key) throws CacheException {
        if (key instanceof List) {
            factory.getProvider(CacheCustoms.CACHE_LV_1).buildCache(region, true, this).evict((List) key);
        } else {
            factory.getProvider(CacheCustoms.CACHE_LV_1).buildCache(region, true, this).evict(key);
        }
    }

    @Override
    public void sendCmdBroadcast(byte optKey, String region, Object key) throws CacheException {
        Command cmd = new Command(optKey, region, key);

        Jedis jedis = RedisCacheProvider.getResource();
        try {
            jedis.publish(SafeEncoder.encode(clusterName), cmd.toBuff());
        } catch (Exception e) {
            logger.error("发送 代码为 [{}] 的 Redis Pub/Sub 事件消息失败 -> region:{}, key:{}", optKey, region, key, e);
        } finally {
            RedisCacheProvider.returnResource(jedis);
        }
    }

    @Override
    public void notifyElementExpired(String region, Object key) {
        logger.debug("缓存数据过期 -> region:{}, key:{}", region, key);

        // 1.清除二级缓存
        if (factory.isOpenSecondCache()) {
            if (key instanceof List) {
                factory.getProvider(CacheCustoms.CACHE_LV_2).buildCache(region, false, null).evict((List) key);
            } else {
                factory.getProvider(CacheCustoms.CACHE_LV_2).buildCache(region, false, null).evict(key);
            }
        }

        // 2.推送清除缓存的广播消息
        if (factory.isUseCluster() && BroadcastType.REDIS_PUBSUB.equals(factory.getCacheBroadcast()))
            sendCmdBroadcast(CacheCustoms.OPT_DELTED_KEY, region, key);
    }

    /**
     * 消息接受处理
     * @param channel 缓存的通道名称
     * @param message 消息数据
     */
    @Override
    public void onMessage(byte[] channel, byte[] message) {

        // 过滤无效消息
        if (message == null || message.length <= 0) {
            logger.warn("Redis subscribe message is empty!");
            return ;
        }

        try {
            Command cmd = Command.parse(message);
            if (cmd == null) {
                return ;
            }

            switch (cmd.getOperator()) {
                case CacheCustoms.OPT_DELTED_KEY:
                    onDeleteCacheKey(cmd.getRegion(), cmd.getKey());
                    break;
                default:
                    logger.warn("尚未支持的消息类型 : {}", cmd.getOperator());
            }
        } catch (Exception e) {
            logger.error("未能正确处理接收到的JGroup消息.", e);
        }

    }
}
