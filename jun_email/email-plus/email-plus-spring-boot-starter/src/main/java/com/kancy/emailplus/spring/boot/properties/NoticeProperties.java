package com.kancy.emailplus.spring.boot.properties;

import java.time.Duration;

/**
 * NoticeProperties
 *
 * @author kancy
 * @date 2020/2/22 21:44
 */
public class NoticeProperties {
    /**
     * 桶的最大容量，即能装载 Token 的最大数量
     */
    private long capacity = 30;
    /**
     * 每次 Token 补充量
     */
    private long refillTokens = 10;
    /**
     * 补充 Token 的时间间隔
     */
    private Duration refillDuration = Duration.ofMinutes(10);
    /**
     * 邮件通知的key
     */
    private String emailKey;
    /**
     * 出触发器的beanName
     */
    private String trigger = "ramBucketEmailNoticeTrigger";

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(long refillTokens) {
        this.refillTokens = refillTokens;
    }

    public Duration getRefillDuration() {
        return refillDuration;
    }

    public void setRefillDuration(Duration refillDuration) {
        this.refillDuration = refillDuration;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }
}
