package com.redis.proxy.net.exceptions;

/**
 * 
 * @author zhanggaofeng
 */
public class RedisException extends Exception {
  public RedisException() {
    super();
  }

  public RedisException(Throwable cause) {
    super(cause);
  }

  public RedisException(String message) {
    super(message);
  }

  public RedisException(String message, Throwable cause) {
    super(message, cause);
  }

  protected RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
