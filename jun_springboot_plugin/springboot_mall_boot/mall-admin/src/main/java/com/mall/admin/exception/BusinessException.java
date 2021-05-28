package com.mall.admin.exception;

import com.mall.common.utils.ResultCode;

/**
 * 业务异常
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/26 22:28
 */
public class BusinessException extends RuntimeException {

    private ResultCode resultCode;

    public BusinessException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public BusinessException(String message){
        super(message);
    }

    public BusinessException( Throwable cause){
        super(cause);
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
