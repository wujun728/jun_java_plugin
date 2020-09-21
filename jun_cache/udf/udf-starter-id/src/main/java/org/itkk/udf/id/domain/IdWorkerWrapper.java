package org.itkk.udf.id.domain;

import lombok.Data;
import org.itkk.udf.id.IdWorker;

/**
 * IdWorkerWrapper
 */
@Data
public class IdWorkerWrapper {

    /**
     * 数据中心ID ( 0 - 31 )
     */
    private long datacenterId;

    /**
     * 机器ID ( 0 - 31 )
     */
    private long workerId;

    /**
     * ID生成器
     */
    private IdWorker idWorker;

    /**
     * 缓存键
     */
    private String cacheKey;

    /**
     * 缓存值
     */
    private CacheValue cacheValue;
}
