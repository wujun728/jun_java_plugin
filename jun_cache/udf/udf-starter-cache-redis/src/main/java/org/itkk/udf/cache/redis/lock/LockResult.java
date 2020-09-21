package org.itkk.udf.cache.redis.lock;

import lombok.Data;
import lombok.ToString;

/**
 * LockResult
 */
@Data
@ToString
public class LockResult {
    /**
     * lock
     */
    private Boolean lock = false;

    /**
     * value
     */
    private String value;
}
