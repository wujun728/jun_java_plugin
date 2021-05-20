package com.ibeetl.admin.console.exception;

import com.ibeetl.admin.core.util.PlatformException;

/**
 * 描述: 资源不存在异常
 *
 * @author Wujun
 */
public class NoResourceException extends PlatformException {
    public NoResourceException() {
        super();
    }

    public NoResourceException(String message) {
        super(message);
    }
}
