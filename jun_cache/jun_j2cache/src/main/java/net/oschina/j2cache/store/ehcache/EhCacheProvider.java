package net.oschina.j2cache.store.ehcache;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheProvider;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author FY
 */
public class EhCacheProvider implements CacheProvider {
    private static final Logger logger = LoggerFactory.getLogger(EhCacheProvider.class);
    /** ehcahce的配置文件路径 */
    private String config_path = "/ehcache.xml";

    private CacheManager ehcacheManager;
    private ConcurrentHashMap<String, EhCache> cacheInstances;

    @Override
    public String name() {
        return "ehcache";
    }

    @Override
    public Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException {
        EhCache ehCache = cacheInstances.get(name());

        if (ehCache == null && autoCreate) {
            try {
                synchronized (cacheInstances) {
                    ehCache = cacheInstances.get(regionName);
                    if (ehCache == null) {
                        net.sf.ehcache.Cache cache = ehcacheManager.getCache(regionName);
                        if (cache == null) {
                            logger.warn("找不到缓存配置的 {}，将使用默认配置", regionName);
                            ehcacheManager.addCache(regionName);
                            cache = ehcacheManager.getCache(regionName);
                            logger.debug("start EhCache region : {}", regionName);
                        }

                        ehCache = new EhCache(cache, listener);
                        cacheInstances.put(regionName, ehCache);

                    }
                }
            } catch (net.sf.ehcache.CacheException e) {
                throw new CacheException(e);
            }
        }

        return ehCache;
    }

    @Override
    public void start(Properties props) throws CacheException {
        if (ehcacheManager != null) {
            logger.warn("EhCache 已经启动");
            return ;
        }

        URL xml = getClass().getClassLoader().getParent().getResource(config_path);
        if (xml == null) {
            xml = getClass().getResource(config_path);
        }
        if (xml == null) {
            throw new CacheException(String.format("没有找到EhCache的配置文件 : %s", config_path));
        }

        // 初始化
        ehcacheManager = new CacheManager();
        cacheInstances = new ConcurrentHashMap<String, EhCache>();

    }

    public void start(final String confFilePath) throws CacheException {
        if (ehcacheManager != null) {
            logger.warn("EhCache 已经启动");
            return ;
        }

        URL xml = getClass().getClassLoader().getParent().getResource(confFilePath);
        if (xml == null) {
            xml = getClass().getResource(confFilePath);
        }
        if (xml == null) {
            throw new CacheException(String.format("没有找到EhCache的配置文件 : %s", confFilePath));
        }

        this.config_path = confFilePath;

        // 初始化
        ehcacheManager = new CacheManager(xml);
        cacheInstances = new ConcurrentHashMap<String, EhCache>();
    }

    @Override
    public void stop() {
        if (ehcacheManager != null) {
            ehcacheManager.shutdown();
            ehcacheManager = null;
        }
    }
}
