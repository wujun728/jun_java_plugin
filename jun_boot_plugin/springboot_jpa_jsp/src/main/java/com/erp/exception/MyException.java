package com.erp.exception;

public class MyException  extends Exception{

private String errorCode;
    public MyException(String message) {
        super(message);

    }

    public MyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
