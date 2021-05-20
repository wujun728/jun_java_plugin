/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
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

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.CacheProvider;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * EhCache 3.x 缓存管理器的封装，用来管理多个缓存区域
 *
 * @author Wujun
 */
public class EhCacheProvider3 implements CacheProvider {

    private final static Logger log = LoggerFactory.getLogger(EhCacheProvider3.class);

    private final static String DEFAULT_TPL = "default";
    private CacheManager manager;
    private ConcurrentHashMap<String, EhCache3> caches = new ConcurrentHashMap<>();
    private long defaultHeapSize = 1000;

    @Override
    public String name() {
        return "ehcache3";
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

    @Override
    public EhCache3 buildCache(String region, CacheExpiredListener listener) {
        return caches.computeIfAbsent(region, v -> {
            org.ehcache.Cache cache = manager.getCache(region, String.class, Serializable.class);
            if (cache == null) {
                CacheConfiguration defaultCacheConfig = manager.getRuntimeConfiguration().getCacheConfigurations().get(DEFAULT_TPL);
                CacheConfiguration<String, Serializable> cacheCfg = CacheConfigurationBuilder.newCacheConfigurationBuilder(defaultCacheConfig).build();
                cache = manager.createCache(region, cacheCfg);
                log.info("Could not find configuration [" + region + "]; using defaults.");
            }
            return new EhCache3(region, cache, listener);
        });
    }

    @Override
    public EhCache3 buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
        EhCache3 ehcache = caches.computeIfAbsent(region, v -> {
            CacheConfiguration<String, Object> conf = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class, Object.class, ResourcePoolsBuilder.heap(defaultHeapSize))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(timeToLiveInSeconds, TimeUnit.SECONDS)))
                    .build();
            org.ehcache.Cache cache = manager.createCache(region, conf);
            log.info(String.format("Started Ehcache region [%s] with TTL: %d", region, timeToLiveInSeconds));
            return new EhCache3(region, cache, listener);
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

    @Override
    public void start(Properties props) {
        String sDefaultHeapSize = props.getProperty("defaultHeapSize");
        try {
            this.defaultHeapSize = Long.parseLong(sDefaultHeapSize);
        }catch(Exception e) {
            log.warn(String.format("Failed to read ehcache3.defaultHeapSize = %s , use default %d", sDefaultHeapSize, defaultHeapSize));
        }
        String configXml = props.getProperty("configXml");
        if(configXml == null || configXml.trim().length() == 0)
            configXml = "/ehcache3.xml";
        URL myUrl = getClass().getResource(configXml);
        Configuration xmlConfig = new XmlConfiguration(myUrl);
        manager = CacheManagerBuilder.newCacheManager(xmlConfig);
        manager.init();
    }

    @Override
    public void stop() {
        if (manager != null) {
            manager.close();
            caches.clear();
            manager = null;
        }
    }

}
