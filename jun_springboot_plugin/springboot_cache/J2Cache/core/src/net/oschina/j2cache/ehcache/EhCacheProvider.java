/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com), wendal.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.ehcache;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheProvider;
import net.sf.ehcache.CacheManager;

/**
 * EhCache 2.x 缓存管理器的封装，用来管理多个缓存区域
 *
 * @author Wujun
 * @author Wujun
 */
public class EhCacheProvider implements CacheProvider {

	private final static Logger log = LoggerFactory.getLogger(EhCacheProvider.class);

	public final static String KEY_EHCACHE_NAME = "name";
	public final static String KEY_EHCACHE_CONFIG_XML = "configXml";

	private CacheManager manager;
	private ConcurrentHashMap<String, EhCache> caches;

	@Override
	public String name() {
		return "ehcache";
	}

	@Override
	public int level() {
		return CacheObject.LEVEL_1;
	}

	@Override
	public Collection<CacheChannel.Region> regions() {
		Collection<CacheChannel.Region> regions = new ArrayList<>();
		caches.forEach((k,c) -> regions.add(new CacheChannel.Region(k, c.size(), c.ttl())));
		return regions;
	}

    /**
     * Builds a Cache.
     *
     * @param regionName the regionName of the cache. Must match a cache configured in ehcache.xml
     * @param listener cache listener
     * @return a newly built cache will be built and initialised
     */
    @Override
    public EhCache buildCache(String regionName, CacheExpiredListener listener) {
    	return caches.computeIfAbsent(regionName, v -> {
			net.sf.ehcache.Cache cache = manager.getCache(regionName);
			if (cache == null) {
				log.warn("Could not find configuration [" + regionName + "]; using defaults.");
				manager.addCache(regionName);
				cache = manager.getCache(regionName);
				log.info("started Ehcache region: " + regionName);
			}
			return new EhCache(cache, listener);
		});
    }

	@Override
	public EhCache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
		EhCache ehcache = caches.computeIfAbsent(region, v -> {
			//配置缓存
			CacheConfiguration cfg = manager.getConfiguration().getDefaultCacheConfiguration().clone();
			cfg.setName(region);
			if(timeToLiveInSeconds > 0) {
				cfg.setTimeToLiveSeconds(timeToLiveInSeconds);
				cfg.setTimeToIdleSeconds(timeToLiveInSeconds);
			}

			net.sf.ehcache.Cache cache = new net.sf.ehcache.Cache(cfg);
			manager.addCache(cache);

			log.info(String.format("Started Ehcache region [%s] with TTL: %d", region, timeToLiveInSeconds));

			return new EhCache(cache, listener);
		});

		if (ehcache.ttl() != timeToLiveInSeconds)
			throw new IllegalArgumentException(String.format("Region [%s] TTL %d not match with %d", region, ehcache.ttl(), timeToLiveInSeconds));

		return ehcache;
	}

	@Override
	public void removeCache(String region) {
		caches.remove(region);
		manager.removeCache(region);
	}

	/**
	 * init ehcache config
	 *
	 * @param props current configuration settings.
	 */
	public void start(Properties props) {
		if (manager != null) {
            log.warn("Attempt to restart an already started EhCacheProvider.");
            return;
        }
		
		// 如果指定了名称,那么尝试获取已有实例
		String ehcacheName = (String)props.get(KEY_EHCACHE_NAME);
		if (ehcacheName != null && ehcacheName.trim().length() > 0)
			manager = CacheManager.getCacheManager(ehcacheName);
		if (manager == null) {
			// 指定了配置文件路径? 加载之
			if (props.containsKey(KEY_EHCACHE_CONFIG_XML)) {
				URL url = getClass().getResource(props.getProperty(KEY_EHCACHE_CONFIG_XML));
				manager = CacheManager.newInstance(url);
			} else {
				// 加载默认实例
				manager = CacheManager.getInstance();
			}
		}
        caches = new ConcurrentHashMap<>();
	}

	/**
	 * Callback to perform any necessary cleanup of the underlying cache implementation.
	 */
	public void stop() {
		if (manager != null) {
            manager.shutdown();
            caches.clear();
            manager = null;
        }
	}

}
