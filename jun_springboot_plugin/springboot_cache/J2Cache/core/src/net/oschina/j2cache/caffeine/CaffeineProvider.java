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
package net.oschina.j2cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import net.oschina.j2cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine cache provider
 *
 * @author Wujun
 */
public class CaffeineProvider implements CacheProvider {

    private final static Logger log = LoggerFactory.getLogger(CaffeineProvider.class);

    private final static String PREFIX_REGION = "region.";
    private final static String DEFAULT_REGION = "default";
    private ConcurrentHashMap<String, CaffeineCache> caches = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CacheConfig> cacheConfigs = new ConcurrentHashMap<>();

    @Override
    public String name() {
        return "caffeine";
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
    public Cache buildCache(String region, CacheExpiredListener listener) {
        return caches.computeIfAbsent(region, v -> {
            CacheConfig config = cacheConfigs.get(region);
            if (config == null) {
                config = cacheConfigs.get(DEFAULT_REGION);
                if (config == null)
                    throw new CacheException(String.format("Undefined [default] caffeine cache"));

                log.warn(String.format("Caffeine cache [%s] not defined, using default.", region));
            }
            return newCaffeineCache(region, config.size, config.expire, listener);
        });
    }

    @Override
    public Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
        CaffeineCache cache = caches.computeIfAbsent(region, v -> {
            CacheConfig config = cacheConfigs.get(region);
            if(config != null && config.expire != timeToLiveInSeconds)
                throw new IllegalArgumentException(String.format("Region [%s] TTL %d not match with %d", region, config.expire, timeToLiveInSeconds));

            if(config == null) {
                config = cacheConfigs.get(DEFAULT_REGION);
                if (config == null)
                    throw new CacheException(String.format("Undefined caffeine cache region name = %s", region));
            }

            log.info(String.format("Started caffeine region [%s] with TTL: %d", region, timeToLiveInSeconds));
            return newCaffeineCache(region, config.size, timeToLiveInSeconds, listener);
        });

        if(cache != null && cache.ttl() != timeToLiveInSeconds)
            throw new IllegalArgumentException(String.format("Region [%s] TTL %d not match with %d", region, cache.ttl(), timeToLiveInSeconds));

        return cache;
    }

    @Override
    public void removeCache(String region) {
        cacheConfigs.remove(region);
        caches.remove(region);
    }

    /**
     * 返回对 Caffeine cache 的 封装
     * @param region region name
     * @param size   max cache object size in memory
     * @param expire cache object expire time in millisecond
     *               if this parameter set to 0 or negative numbers
     *               means never expire
     * @param listener  j2cache cache listener
     * @return CaffeineCache
     */
    private CaffeineCache newCaffeineCache(String region, long size, long expire, CacheExpiredListener listener) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        caffeine = caffeine.maximumSize(size)
            .removalListener((k,v, cause) -> {
                //程序删除的缓存不做通知处理，因为上层已经做了处理
                if(cause != RemovalCause.EXPLICIT && cause != RemovalCause.REPLACED)
                    listener.notifyElementExpired(region, (String)k);
            });
        if (expire > 0) {
            caffeine = caffeine.expireAfterWrite(expire, TimeUnit.SECONDS);
        }
        com.github.benmanes.caffeine.cache.Cache<String, Object> loadingCache = caffeine.build();
        return new CaffeineCache(loadingCache, size, expire);
    }

    /**
     * <p>配置示例</p>
     * <ul>
     * <li>caffeine.region.default = 10000,1h</li>
     * <li>caffeine.region.Users = 10000,1h</li>
     * <li>caffeine.region.Blogs = 80000,30m</li>
     * </ul>
     * @param props current configuration settings.
     */
    @Override
    public void start(Properties props) {
        for(String region : props.stringPropertyNames()) {
            if(!region.startsWith(PREFIX_REGION))
                continue ;
            String s_config = props.getProperty(region).trim();
            region = region.substring(PREFIX_REGION.length());
            this.saveCacheConfig(region, s_config);
        }
        //加载 Caffeine 独立配置文件
        String propertiesFile = props.getProperty("properties");
        if(propertiesFile != null && propertiesFile.trim().length() > 0) {
            try (InputStream stream = getClass().getResourceAsStream(propertiesFile)) {
                Properties regionsProps = new Properties();
                regionsProps.load(stream);
                for(String region : regionsProps.stringPropertyNames()) {
                    String s_config = regionsProps.getProperty(region).trim();
                    this.saveCacheConfig(region, s_config);
                }
            } catch (IOException e) {
                log.error("Failed to load caffeine regions define " + propertiesFile, e);
            }
        }
    }

    private void saveCacheConfig(String region, String region_config) {
        CacheConfig cfg = CacheConfig.parse(region_config);
        if(cfg == null)
            log.warn(String.format("Illegal caffeine cache config [%s=%s]", region, region_config));
        else
            cacheConfigs.put(region, cfg);
    }

    @Override
    public void stop() {
        caches.clear();
        cacheConfigs.clear();
    }

    /**
     * 缓存配置
     */
    private static class CacheConfig {

        private long size = 0L;
        private long expire = 0L;

        public static CacheConfig parse(String cfg) {
            CacheConfig cacheConfig = null;
            String[] cfgs = cfg.split(",");
            if(cfgs.length == 1) {
                cacheConfig = new CacheConfig();
                String sSize = cfgs[0].trim();
                cacheConfig.size = Long.parseLong(sSize);
            }
            else if(cfgs.length == 2) {
                cacheConfig = new CacheConfig();
                String sSize = cfgs[0].trim();
                String sExpire = cfgs[1].trim();
                cacheConfig.size = Long.parseLong(sSize);
                char unit = Character.toLowerCase(sExpire.charAt(sExpire.length()-1));
                cacheConfig.expire = Long.parseLong(sExpire.substring(0, sExpire.length() - 1));
                switch(unit){
                    case 's'://seconds
                        break;
                    case 'm'://minutes
                        cacheConfig.expire *= 60;
                        break;
                    case 'h'://hours
                        cacheConfig.expire *= 3600;
                        break;
                    case 'd'://days
                        cacheConfig.expire *= 86400;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown expire unit:" + unit);
                }
            }
            return cacheConfig;
        }

        @Override
        public String toString() {
            return String.format("[SIZE:%d,EXPIRE:%d]", size, expire);
        }

    }

}
