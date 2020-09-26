package com.kancy.emailplus.spring.boot.aop;

import com.kancy.emailplus.spring.boot.properties.EmailplusProperties;
import com.kancy.emailplus.spring.boot.properties.NoticeProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RamBucketEmailNoticeTrigger
 *
 * @author kancy
 * @date 2020/2/22 21:23
 */
public class RamBucketEmailNoticeTrigger implements EmailNoticeTrigger {
    private static final Logger log = LoggerFactory.getLogger(RamBucketEmailNoticeTrigger.class);
    /**
     * 本地令牌桶缓存
     */
    private static final Map<String, Bucket> CACHE = new ConcurrentHashMap();

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
        Bucket bucket = CACHE.computeIfAbsent(String.format("email:notice:%s", name), k -> createNewBucket(name));

        log.debug("email:notice: {} , TokenBucket Available Tokens: {}" , name, bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            // 在容忍的范围内
            return false;
        } else {
            return true;
        }
    }

    private Bucket createNewBucket(String name) {
        NoticeProperties noticeProperties = emailplusProperties.getEmailNotices().get(name);
        long capacity = noticeProperties.getCapacity();
        long refillTokens = noticeProperties.getRefillTokens();
        Duration refillDuration = noticeProperties.getRefillDuration();

        // refillDuration时间内允许发生某场景refillTokens次
        Refill refill = Refill.greedy(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }
}
