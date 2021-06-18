package com.ibeetl.admin.console.exception;

import com.ibeetl.admin.core.util.PlatformException;

/**
 * 描述:  已删除异常
 *
 * @author Wujun
 */
public class DeletedException extends PlatformException {
    public DeletedException() {
        super();
    }

    public DeletedException(String message) {
        super(message);
    }
}
