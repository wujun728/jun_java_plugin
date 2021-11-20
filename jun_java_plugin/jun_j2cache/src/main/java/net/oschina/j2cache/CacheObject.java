package net.oschina.j2cache;

import java.io.Serializable;

/**
 * 缓存数据内容，包括region/key/value/lv
 */
public class CacheObject<V extends Serializable> implements Serializable {

    /** 缓存的RegionName */
    private String region;
    /** 缓存传入的key */
    private Object key;
    /** 缓存数据 */
    private Object value;
    /** 缓存等级，1-一级缓存，2-二级缓存 */
    private byte lv;
    /** 缓存写入时间戳: s */
    private Long createTime;
    /** 有效期: s */
    private Integer expired;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public byte getLv() {
        return lv;
    }

    public void setLv(byte lv) {
        this.lv = lv;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    // ----------- Ext method --------------

    public void setUp(CacheBox cb) {
        if (cb != null) {
            this.createTime = cb.getTimestamp();
            this.expired = cb.getExpired();
            this.value = cb.getValue();
        }
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    @Override
    public String toString() {
        return "CacheObject{" +
                "region='" + region + '\'' +
                ", key=" + key +
                ", value=" + value +
                ", lv=" + lv +
                ", createTime=" + createTime +
                ", expired=" + expired +
                '}';
    }
}
