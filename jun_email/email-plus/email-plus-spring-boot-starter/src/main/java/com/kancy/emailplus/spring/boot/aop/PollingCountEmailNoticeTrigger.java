package com.kancy.emailplus.spring.boot.aop;

import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.properties.NoticeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询计数电子邮件通知触发器
 * PollingCountEmailNoticeTrigger
 *
 * @author Wujun
 * @date 2020/2/23 18:11
 */
public class PollingCountEmailNoticeTrigger implements EmailNoticeTrigger{
    private static final Logger log = LoggerFactory.getLogger(PollingCountEmailNoticeTrigger.class);
    /**
     * 本地缓存
     */
    private static final Map<String, AtomicInteger> CACHE = new ConcurrentHashMap<>();

    @Resource
    private EmailplusProperties emailplusProperties;

    /**
     * 是否触发
     *
     * @param name
     * @return
     */
    @Override
    public boolean isTrigger(String name) {
        NoticeProperties noticeProperties = emailplusProperties.getEmailNotices().get(name);
        long capacity = noticeProperties.getCapacity();
        AtomicInteger atomicInteger = CACHE.computeIfAbsent(String.format("email:notice:%s", name), k -> createCounter(name));
        synchronized (atomicInteger){
            int count = atomicInteger.incrementAndGet();
            log.debug("email:notice: {} , Atomic pollingCount now is: {}" , name, count);
            if (count >= capacity){
                // 触发后，重置
                atomicInteger.set(0);
                return true;
            }
        }
        return false;
    }

    private AtomicInteger createCounter(String name) {
        return new AtomicInteger(0);
    }
}
