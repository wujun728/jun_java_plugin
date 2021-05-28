package com.gosalelab.configs;

import com.gosalelab.constants.CacheConstants;
import com.gosalelab.properties.CacheProperties;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;
import java.time.Duration;

/**
 * @author Wujun
 */
@Configuration
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "provider", havingValue = CacheConstants.CACHE_EHCACHE_NAME)
public class EHCacheConfig {

    private static Logger logger = LoggerFactory.getLogger(EHCacheConfig.class);

    private final CacheProperties properties;

    @Autowired
    public EHCacheConfig(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX + ".ehcache", name = "use-xml-file-config", havingValue = "false", matchIfMissing = true)
    public CacheManager cacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        cacheManager.createCache(properties.getEhcache().getDefaultCacheName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.heap(properties.getEhcache().getMaxEntriesLocalHeap()))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(CacheConstants.TIME_TO_IDLE_EXPIRATION)))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(CacheConstants.TIME_TO_LIVE_EXPIRATION)))
                        .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(properties.getEhcache().getOffHeap(), MemoryUnit.MB)
                        )
        );

        return cacheManager;
    }

    /**
     * Use XML to config ehcache
     *
     * @return CacheManager instance
     */
    @Bean
    @ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX + ".ehcache", name = "use-xml-file-config", havingValue = "true")
    public CacheManager xmlCacheManager() {
        final URL configFileUrl = this.getClass().getResource(File.separator +
                properties.getEhcache().getEhcacheFileName());
        org.ehcache.config.Configuration xmlConfig = new XmlConfiguration(configFileUrl);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        cacheManager.init();
        return cacheManager;
    }

}
