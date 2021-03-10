package com.gosalelab.provider;

import com.gosalelab.constants.CacheConstants;
import com.gosalelab.exception.CacheProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Wujun
 */
@Component
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheProviderHolder {

    private final Map<String, CacheProvider> cacheProviders;

    @Autowired
    public CacheProviderHolder(Map<String, CacheProvider> cacheProviders) {
        this.cacheProviders = cacheProviders;
    }

    public CacheProvider findCacheProvider(String type) {
        String name = type.toLowerCase() + CacheProvider.class.getSimpleName();

        CacheProvider provider = cacheProviders.get(name);

        if (provider == null) {
            throw new CacheProviderException("CacheProvider class[" + name + "] does not exist.");
        }
        return provider;
    }
}
