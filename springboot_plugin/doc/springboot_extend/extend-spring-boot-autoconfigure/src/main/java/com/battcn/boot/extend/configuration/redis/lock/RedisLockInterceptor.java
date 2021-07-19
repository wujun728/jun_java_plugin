package com.battcn.boot.extend.configuration.redis.lock;


import com.battcn.boot.extend.configuration.redis.RedisKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * @author Levin
 * @since 2018/12/24 0024
 */
@Slf4j
@Aspect
@ConditionalOnProperty(prefix = REDIS_LOCK_INTERCEPTOR, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class RedisLockInterceptor {

    @Resource
    private RedisLockHelper redisLockHelper;
    @Resource
    private RedisKeyGenerator redisKeyGenerator;


    @Around("execution(public * *(..)) && @annotation(com.battcn.boot.extend.configuration.redis.lock.RedisLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisLock lock = method.getAnnotation(RedisLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key prefix don't null...");
        }
        final String lockKey = redisKeyGenerator.generate(lock.prefix(), lock.delimiter(), pjp);
        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockHelper.lock(lockKey, value, lock.expire(), lock.timeUnit());
            if (log.isDebugEnabled()) {
                log.debug("Redis lock key is [{}] and status is [{}]", lockKey, success);
            }
            if (!success) {
                throw new RuntimeException(lock.message());
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                log.error("[server exception]", throwable);
                throw new RuntimeException("server exception");
            }
        } finally {
            redisLockHelper.unlock(lockKey, value);
        }
    }
}