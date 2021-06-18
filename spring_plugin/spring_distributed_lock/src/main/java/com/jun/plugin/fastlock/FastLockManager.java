package com.jun.plugin.fastlock;

/**
 * @author peiyu
 */
public interface FastLockManager {

    FastLock getLock(String key);

    FastLock getLock(String key, long timeout);

}
