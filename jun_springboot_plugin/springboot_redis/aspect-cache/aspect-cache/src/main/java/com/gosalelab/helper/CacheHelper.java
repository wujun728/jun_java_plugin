package com.gosalelab.helper;

import com.gosalelab.annotation.CacheInject;
import com.gosalelab.generator.CacheKeyGeneratorHolder;
import com.gosalelab.generator.KeyGenerator;
import com.gosalelab.properties.CacheProperties;
import com.gosalelab.provider.CacheProvider;
import com.gosalelab.provider.CacheProviderHolder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author Wujun
 */
public class CacheHelper {

    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }
        Class cl = obj.getClass();
        if (cl.isArray()) {
            int len = Array.getLength(obj);
            return len == 0;
        }
        if (obj instanceof Collection) {
            Collection tempCol = (Collection) obj;
            return tempCol.isEmpty();
        }
        if (obj instanceof Map) {
            Map tempMap = (Map) obj;
            return tempMap.isEmpty();
        }
        return false;
    }

    private static KeyGenerator getKeyGenerator(CacheKeyGeneratorHolder keyGeneratorHolder, String keyGenerator) {
        return keyGeneratorHolder.findKeyGenerator(keyGenerator);
    }

    private static CacheProvider getCacheProvider(CacheProviderHolder cacheProviderHolder, String cacheProvider) {
        return cacheProviderHolder.findCacheProvider(cacheProvider);
    }

    public static String getCacheKey(CacheKeyGeneratorHolder keyGeneratorHolder,
                                     CacheProperties cacheProperties,
                                     String key,
                                     Object[] arguments, Object returnVal) {
        return getKeyGenerator(keyGeneratorHolder, cacheProperties.getKeyGenerator())
                .getKey(key, arguments, returnVal);
    }

    public static Object getCache(CacheProviderHolder cacheProviderHolder,
                                  CacheProperties cacheProperties,
                                  String key, Type genericReturnType) {
        return CacheHelper.getCacheProvider(cacheProviderHolder, cacheProperties.getProvider()).get(key, genericReturnType);
    }

    public static void putCache(CacheProviderHolder cacheProviderHolder,
                                 CacheProperties cacheProperties,
                                 String key, Object result, CacheInject annotation) {
        if (!StringUtils.isEmpty(key)) {
            CacheHelper.getCacheProvider(cacheProviderHolder, cacheProperties.getProvider()).put(key, result,
                    annotation.expire() > 0 ? annotation.expire() : cacheProperties.getExpireTime());
        }
    }

}
