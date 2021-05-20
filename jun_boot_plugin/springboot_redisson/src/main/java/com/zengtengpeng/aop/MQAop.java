package com.zengtengpeng.aop;

import com.zengtengpeng.annotation.MQPublish;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MQ发送消息AOP
 */
@Aspect
public class MQAop {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(mq)")
    public void aspect(MQPublish mq) {
    }

    @Around("aspect(mq)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, MQPublish mq) {
        try {
            Object obj = proceedingJoinPoint.proceed();
            RTopic topic = redissonClient.getTopic(mq.name());
            topic.publish(obj);
            return obj;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}
