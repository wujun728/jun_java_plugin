package com.github.sd4324530.fastlock;

/**
 * @author peiyu
 */
public interface FastLockManager {

    FastLock getLock(String key);

    FastLock getLock(String key, long timeout);

}
