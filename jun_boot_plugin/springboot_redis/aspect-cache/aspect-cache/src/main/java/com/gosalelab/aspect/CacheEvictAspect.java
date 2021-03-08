package com.gosalelab.aspect;

import com.gosalelab.helper.CacheHelper;
import com.gosalelab.annotation.CacheEvict;
import com.gosalelab.constants.CacheConstants;
import com.gosalelab.generator.CacheKeyGeneratorHolder;
import com.gosalelab.properties.CacheProperties;
import com.gosalelab.provider.CacheProvider;
import com.gosalelab.provider.CacheProviderHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * evict aspect
 *
 * @author Wujun
 */
@Component
@Aspect
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheEvictAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final CacheKeyGeneratorHolder keyGeneratorHolder;

    private final CacheProviderHolder cacheProviderHolder;

    private final CacheProperties cacheProperties;

    @Autowired
    public CacheEvictAspect(CacheKeyGeneratorHolder keyGeneratorHolder, CacheProviderHolder cacheProviderHolder,
                            CacheProperties cacheProperties) {
        this.keyGeneratorHolder = keyGeneratorHolder;
        this.cacheProviderHolder = cacheProviderHolder;
        this.cacheProperties = cacheProperties;
    }

    @Around("com.gosalelab.aspect.CommonJoinPointConfig.cacheEvictAspectExecution()&&@annotation(annotation)")
    public Object interceptor(ProceedingJoinPoint joinPoint, CacheEvict annotation) throws Throwable {
        // arguments list
        Object[] arguments = joinPoint.getArgs();
        // cache key
        String key = "";

        String keyExpressions = StringUtils.isEmpty(annotation.pre())
                ? annotation.key() : "'" + annotation.pre() + "'+" + annotation.key();

        try {
            key = CacheHelper.getCacheKey(keyGeneratorHolder, cacheProperties,
                    keyExpressions, arguments, null);
            getCacheProvider().del(key);
        } catch (Exception e) {
            log.error("evict cache fail：" + key, e);
        }

        return joinPoint.proceed();
    }

    /**
     * get cache provider
     *
     * @return CacheProvider
     */
    private CacheProvider getCacheProvider() {
        return cacheProviderHolder.findCacheProvider(cacheProperties.getProvider());
    }
}
