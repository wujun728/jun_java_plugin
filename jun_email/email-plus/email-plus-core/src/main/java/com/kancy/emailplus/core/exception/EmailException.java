package com.kancy.emailplus.core.exception;

/**
 * EmailException
 *
 * @author kancy
 * @date 2020/2/19 21:33
 */
public class EmailException extends RuntimeException {

    /**
     * Constructor for EmailException.
     *
     * @param msg the detail message
     */
    public EmailException(String msg) {
        super(msg);
    }

    /**
     * Constructor for EmailException.
     *
     * @param msg   the detail message
     * @param cause the root cause from the mail API in use
     */
    public EmailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}