package org.typroject.tyboot.core.foundation.exception;

/**
 * 异常基础类，所有自定义异常都集成与此类。以便输出http状态。
 * Created by 子杨 on 2017/4/20.
 */
public class BaseException extends RuntimeException{

    protected int httpStatus;
    protected String errorCode;
    protected String devMessage;

    public BaseException(String message, String errorCode,String devMessage)
    {
        super(message);
        this.errorCode = errorCode;
        this.devMessage = devMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDevMessage()
    {
        return devMessage;
    }


}
