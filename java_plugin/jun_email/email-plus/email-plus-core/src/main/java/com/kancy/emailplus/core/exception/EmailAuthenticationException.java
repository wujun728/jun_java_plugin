package com.kancy.emailplus.core.exception;

/**
 * EmailAuthenticationException
 *
 * @author Wujun
 * @date 2020/2/19 21:48
 */
public class EmailAuthenticationException extends EmailException {

    /**
     * Constructor for EmailException.
     *
     * @param msg the detail message
     */
    public EmailAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for EmailException.
     *
     * @param msg   the detail message
     * @param cause the root cause from the mail API in use
     */
    public EmailAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
