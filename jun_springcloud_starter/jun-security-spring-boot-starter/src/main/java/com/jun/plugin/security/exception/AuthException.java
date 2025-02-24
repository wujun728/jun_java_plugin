package com.jun.plugin.security.exception;


public abstract class AuthException extends RuntimeException {
    private static final long serialVersionUID = 2413958299445359500L;

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }
}
