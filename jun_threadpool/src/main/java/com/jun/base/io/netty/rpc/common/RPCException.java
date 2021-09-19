package com.jun.base.io.netty.rpc.common;

public class RPCException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public RPCException(String message, Throwable cause) {
        super(message, cause);
    }
    public RPCException(String message) {
        super(message);
    }
    public RPCException(Throwable cause) {
        super(cause);
    }
}
