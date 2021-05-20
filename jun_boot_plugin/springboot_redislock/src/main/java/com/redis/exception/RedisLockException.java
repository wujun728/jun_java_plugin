package com.redis.exception;

/**
 * @author
 * @version V1.0
 * @Description
 * @date 2018-01-29 16:24
 **/
public class RedisLockException extends Exception{
    public RedisLockException(String message) {
        super(message);
    }

    public RedisLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
