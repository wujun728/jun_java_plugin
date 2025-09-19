package net.maku.framework.common.exception;

/**
 * 服务器异常
 * 用于服务层抛出的业务异常
 */
public class ServerException extends RuntimeException {

    /**
     * 构造函数
     * @param message 异常消息
     */
    public ServerException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * @param message 异常消息
     * @param cause 异常原因
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}