package com.jun.plugin.fastlock;

/**
 * @author peiyu
 */
public class FastLockException extends RuntimeException {

    public FastLockException(String message) {
        super(message);
    }

    public FastLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
