package com.zh.springbootmongodb.base.exception;

import com.zh.springbootmongodb.base.enums.AppResultCode;
import lombok.Data;

/**
 * @author Wujun
 * @date 2019/6/5
 */
@Data
public class BusinessException extends RuntimeException {

    private int code;

    private String msg;

    private AppResultCode appResultCode;

    public BusinessException() {
        super();
    }

    public BusinessException(AppResultCode appResultCode) {
        super(appResultCode.getMsg());
        this.code = appResultCode.getCode();
        this.msg = appResultCode.getMsg();
        this.appResultCode = appResultCode;
    }
}
