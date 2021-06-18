package com.jun.plugin.fastlock;

/**
 * @author Wujun
 */
public class FastLockException extends RuntimeException {

    public FastLockException(String message) {
        super(message);
    }

    public FastLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
