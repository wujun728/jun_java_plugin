package com.osmp.web.system.cache.entity;

import java.io.Serializable;

/**
 * @author wangqiang
 *
 */
public class CacheInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6795829709167273361L;

    /**
     * cache个数
     */
    private Long size;

    /**
     * cache内存占用
     */
    private Long memorySize;

    /**
     * cache命中次数
     */
    private Long hitCount;

    /**
     * cache错失数
     */
    private Long missCount;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Long memorySize) {
        this.memorySize = memorySize;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public Long getMissCount() {
        return missCount;
    }

    public void setMissCount(Long missCount) {
        this.missCount = missCount;
    }

    @Override
    public String toString() {
        return "CacheInfo [size=" + size + ", memorySize=" + memorySize + ", hitCount=" + hitCount + ", missCount="
                + missCount + "]";
    }

}
