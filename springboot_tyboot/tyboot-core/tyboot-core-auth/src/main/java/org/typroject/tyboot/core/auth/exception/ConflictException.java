package org.typroject.tyboot.core.auth.exception;

import org.typroject.tyboot.core.foundation.exception.BaseException;

public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(message, ConflictException.class.getSimpleName(), "服务器处理请求时发生了冲突.");
        this.httpStatus = 409;
    }
}
