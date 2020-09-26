package net.oschina.j2cache;

import java.io.Serializable;
import java.time.Instant;

/**
 * 写入缓存数据的封装
 *
 * @author Wujun
 */
public class CacheBox implements Serializable {
    /** 缓存写入的时间 */
    private Long timestamp = Instant.now().getEpochSecond();
    /** 缓存数据 */
    private Object value;
    /** 有效期：0-永久 */
    private Integer expired = 0;

    public CacheBox(Object value, Integer expired) {
        this.value = value;
        this.expired = expired;
    }

    public CacheBox(Object value) {
        this(value, 0);
    }

    public CacheBox() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }
}
