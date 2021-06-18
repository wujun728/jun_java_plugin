package io.gitee.tooleek.lock.spring.boot.exception;

/**
 * 验证错误异常
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:32
 */
public class LockValidateException extends RuntimeException {

    private static final long serialVersionUID = -6925556550996662677L;

    public LockValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockValidateException(Throwable cause) {
        super(cause);
    }

    public LockValidateException(String message) {
        super(message);
    }

    public LockValidateException() {
        super();
    }
}
