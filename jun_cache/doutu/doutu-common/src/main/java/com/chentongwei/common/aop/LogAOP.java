package com.chentongwei.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP拦截Service层，记录慢日志。
 *
 * @author 15620646321@163.com 2017-5-15 16:38:52
 */
@Aspect
@Component
public class LogAOP {

    private static final Logger LOG = LoggerFactory.getLogger("slowLog");//慢日志

    private static final String CUT = "execution (* com.chentongwei.service.*.impl.*ServiceImpl.*(..))";//切点表达式

    @Around(CUT)
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Object result = null;
        long start = System.currentTimeMillis();
        try {
            result = pjp.proceed(args);
        } catch (Exception e) {
            throw e;
        }
        //慢日志
        long end = System.currentTimeMillis();
        if (end - start >= 1000) {//大于一秒记录
            LOG.info("method [" + pjp.getSignature().toString() +"] cost " + (end - start) + "ms ---------------");
        }
        return result;
    }

}