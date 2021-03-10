package com.demo.weixin.exception;

/**
 * @author Wujun
 * @description 微信异常
 * @date 2017/7/31
 * @since 1.0
 */
public class WeixinException extends Exception {
    private static final long serialVersionUID = 5957539807621801551L;

    public static final int ARGUMENT_ERROR_CODE = -1;
    public static final String ARGUMENT_ERROR_MESSAGE = "参数缺失";

    public static final int NOT_QY_USER_CODE = -100;
    public static final String NOT_QY_USER_MESSAGE = "非企业成员用户，请联系管理员";


    public static final int ERROR_SEVER_CODE = -300;
    public static final String ERROR_SERVER_MESSAGE = "服务器异常,请重试";


    // 服务器异常
    public static final WeixinException ERROR_SEVER_EXCEPTION = new WeixinException(ERROR_SEVER_CODE, ERROR_SERVER_MESSAGE);

    private int errorCode;

    private String errorMessage;

    public WeixinException() {
    }

    public WeixinException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public WeixinException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.initCause(cause);
    }

    public WeixinException(String message, Throwable cause, int errorCode, String errorMessage) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
