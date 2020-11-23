package com.kancy.emailplus.core.exception;

/**
 * TemplateException
 *
 * @author Wujun
 * @date 2020/2/22 16:25
 */
public class TemplateException extends RuntimeException {
    public TemplateException(String message) {
        super(message);
    }

    public TemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateException(Throwable cause) {
        super(cause);
    }
}
