package com.github.sd4324530.fastrpc.core.exception;

/**
 * @author peiyu
 */
public class FastrpcException extends RuntimeException {
    public FastrpcException() {
    }

    public FastrpcException(String message) {
        super(message);
    }

    public FastrpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastrpcException(Throwable cause) {
        super(cause);
    }

    public FastrpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
