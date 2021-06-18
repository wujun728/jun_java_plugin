package com.alibaba.boot.dubbo.ratelimiting;

import java.util.Date;

/**
 * Repository storing data used by the gateway's rate limiting filter.
 */
public interface RateLimitingRepository {

    void incrementCounter(String id, String timeUnit, Date time);

    long getCounter(String id, String timeUnit, Date time);

}
