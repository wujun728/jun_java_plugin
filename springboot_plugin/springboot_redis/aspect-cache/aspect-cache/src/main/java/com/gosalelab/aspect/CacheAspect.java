package com.gosalelab.aspect;

import com.gosalelab.helper.CacheHelper;
import com.gosalelab.annotation.CacheInject;
import com.gosalelab.constants.CacheConstants;
import com.gosalelab.constants.CacheOpType;
import com.gosalelab.generator.CacheKeyGeneratorHolder;
import com.gosalelab.properties.CacheProperties;
import com.gosalelab.provider.CacheProviderHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * use cache annotation
 *
 * @author Wujun
 */
@Component
@Aspect
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CacheKeyGeneratorHolder keyGeneratorHolder;
    private final CacheProviderHolder cacheProviderHolder;
    private final CacheProperties cacheProperties;

    @Autowired
    public CacheAspect(CacheKeyGeneratorHolder keyGeneratorHolder, CacheProviderHolder cacheProviderHolder, CacheProperties cacheProperties) {
        this.keyGeneratorHolder = keyGeneratorHolder;
        this.cacheProviderHolder = cacheProviderHolder;
        this.cacheProperties = cacheProperties;
    }

    @Around("com.gosalelab.aspect.CommonJoinPointConfig.cacheInjectAspectExecution()&&@annotation(annotation)")
    public Object interceptor(ProceedingJoinPoint joinPoint, CacheInject annotation) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] arguments = joinPoint.getArgs();

        Type genericReturnType = method.getGenericReturnType();

        Object result = null;

        String key = "";

        String keyExpressions = StringUtils.isEmpty(annotation.pre())
                ? annotation.key() : "'" + annotation.pre() + "'+" + annotation.key();

        try {
            if (annotation.opType() == CacheOpType.WRITE) {

                result = joinPoint.proceed();
                key = CacheHelper.getCacheKey(keyGeneratorHolder, cacheProperties,
                        keyExpressions, arguments, result);
                CacheHelper.putCache(cacheProviderHolder, cacheProperties, key, result, annotation);

            } else if (annotation.opType() == CacheOpType.READ_ONLY) {
                key = CacheHelper.getCacheKey(keyGeneratorHolder, cacheProperties,
                        keyExpressions, arguments, null);
                result = CacheHelper.getCache(cacheProviderHolder, cacheProperties, key, genericReturnType);
            } else {

                key = CacheHelper.getCacheKey(keyGeneratorHolder, cacheProperties,
                        keyExpressions, arguments, null);
                // get cache from cache
                result = CacheHelper.getCache(cacheProviderHolder, cacheProperties, key, genericReturnType);

                if (result == null) {
                    result = joinPoint.proceed();
                }

                CacheHelper.putCache(cacheProviderHolder, cacheProperties, key, result, annotation);
            }

            if (result == null) {
                result = joinPoint.proceed();
            }

        } catch (Exception e) {
            logger.error("get cache of key " + key + ":", e);
        }

        return result;
    }


}
