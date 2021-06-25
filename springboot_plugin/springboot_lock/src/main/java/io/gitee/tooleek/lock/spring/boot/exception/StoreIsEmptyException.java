package io.gitee.tooleek.lock.spring.boot.exception;

/**
 * 仓库为空的异常
 *
 * @author Wujun
 */
public class StoreIsEmptyException extends RuntimeException {

    private static final long serialVersionUID = -8975192014996569811L;

    public StoreIsEmptyException(String msg) {
        super(msg);
    }

}
