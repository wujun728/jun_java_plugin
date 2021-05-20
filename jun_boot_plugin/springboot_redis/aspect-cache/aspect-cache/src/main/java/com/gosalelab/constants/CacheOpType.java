package com.gosalelab.constants;

/**
 * enum of cache operation type
 *
 * @author Wujun
 */
public enum CacheOpType {
    /**
     * read from data source and write to cache, this is default cache operation.
     */
    READ_WRITE,
    /**
     * only write cache
     */
    WRITE,
    /**
     * ony read cache
     */
    READ_ONLY,
}
