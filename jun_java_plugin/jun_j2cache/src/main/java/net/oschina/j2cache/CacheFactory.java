package net.oschina.j2cache;

import net.oschina.j2cache.broadcast.BroadcastType;
import net.oschina.j2cache.store.ehcache.EhCacheProvider;
import net.oschina.j2cache.store.redis.RedisCacheProvider;
import net.oschina.j2cache.utils.CacheCustoms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author FY
 */
public class CacheFactory implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    /** 配置文件：集群环境的组播同步网络配置文件 XML */
    private String cache_jgroup_conf_file = "/cache/cache/udp-newwork.xml";
    /** 配置文件：ehcache XML */
    private String cache_ehcache_conf_file = "/conf/cache/ehcache.xml";
    /** 配置文件：redis */
    private String cache_redis_conf_file = "/conf/cache/redis.properties";

    /** 是否开启了集群配置，默认是开启的 */
    private boolean useCluster = true;

    // 是否开启二级缓存：redis，默认关闭，仅使用ehcache实现
    private boolean openSecondCache = false;
    // 缓存广播类型，在集群模式先必须开启；若开启了二级缓存，请确定广播模式
    private BroadcastType cacheBroadcast;

    private CacheProvider provider_lv1;
    private CacheProvider provider_lv2;

    /**
     * 模板类调用，根据Cache LV key获取缓存的provider
     * @param lvKey 缓存等级，1-一级缓存，2-二级缓存
     * @return {@link CacheProvider}
     */
    public CacheProvider getProvider(byte lvKey) {
        switch (lvKey) {
            case CacheCustoms.CACHE_LV_1:
                return provider_lv1;
            case CacheCustoms.CACHE_LV_2:
                return provider_lv2;
            default:
                return provider_lv1;
        }
    }

    /**
     * 获得缓存的操作接口实例
     * @param lvKey 缓存等级，1-一级缓存，2-二级缓存
     * @param regionName 缓存的region name
     * @param autoCreate 是否为自动构建
     * @param listener 缓存事件监听器
     * @return {@link Cache}
     */
    public Cache getCache(byte lvKey, String regionName, boolean autoCreate, CacheExpiredListener listener) {
        return getProvider(lvKey).buildCache(regionName, autoCreate, listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (openSecondCache) {
            Assert.notNull(cache_redis_conf_file, "二级缓存已开启，其配置文件路径不能为 null.");
        }

        if (useCluster) {
            if (cacheBroadcast == null) {
                throw new IllegalArgumentException("集群模式下，必须设置缓存的 broadcast 类型");
            }
            switch (cacheBroadcast) {
                case JGROUPS_MULTICAST: {
                    Assert.notNull(cache_jgroup_conf_file, "组播的网络配置文件路径不能为 null.");
                    break;
                }
                case REDIS_PUBSUB: {
                    if (!openSecondCache) {
                        throw new IllegalArgumentException("未开启二级缓存实现，不支持该 broadcast 类型");
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException("不支持的广播类型 : " + cacheBroadcast);
            }

        }

        Assert.notNull(cache_ehcache_conf_file, "一级缓存ehcache的配置文件路径不能为 null.");

        // 做初始化
        // 1.初始化一级 cache 的 provider
        this.provider_lv1 = new EhCacheProvider();
        this.provider_lv1.start(cache_ehcache_conf_file);
        // 2.初始化二级cache 的 provider
        if (openSecondCache) {
            this.provider_lv2 = new RedisCacheProvider();
            this.provider_lv2.start(cache_redis_conf_file);
        }

        // 3.初始化工作完成
        logger.info("缓存初始化完成.");
    }

    // ---------------- getter/setter ---------------------

    public String getCache_jgroup_conf_file() {
        return cache_jgroup_conf_file;
    }

    public void setCache_jgroup_conf_file(String cache_jgroup_conf_file) {
        this.cache_jgroup_conf_file = cache_jgroup_conf_file;
    }

    public String getCache_ehcache_conf_file() {
        return cache_ehcache_conf_file;
    }

    public void setCache_ehcache_conf_file(String cache_ehcache_conf_file) {
        this.cache_ehcache_conf_file = cache_ehcache_conf_file;
    }

    public String getCache_redis_conf_file() {
        return cache_redis_conf_file;
    }

    public void setCache_redis_conf_file(String cache_redis_conf_file) {
        this.cache_redis_conf_file = cache_redis_conf_file;
    }

    public boolean isOpenSecondCache() {
        return openSecondCache;
    }

    public void setOpenSecondCache(boolean openSecondCache) {
        this.openSecondCache = openSecondCache;
    }

    public BroadcastType getCacheBroadcast() {
        return cacheBroadcast;
    }

    public void setCacheBroadcast(BroadcastType cacheBroadcast) {
        this.cacheBroadcast = cacheBroadcast;
    }

    public boolean isUseCluster() {
        return useCluster;
    }

    public void setUseCluster(boolean useCluster) {
        this.useCluster = useCluster;
    }
}
