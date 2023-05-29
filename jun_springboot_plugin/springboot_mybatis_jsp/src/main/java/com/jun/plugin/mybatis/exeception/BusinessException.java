package com.jun.plugin.mybatis.exeception;

import com.jun.plugin.mybatis.enums.ResponseCodeEnum;

/**
 * Create Date: 2017/11/06
 * Description: 业务异常
 *
 * @author Wujun
 */
public class BusinessException extends BaseException{


    public BusinessException(String defaultMessage) {
        super(defaultMessage);
    }

    public BusinessException(ResponseCodeEnum codeEnum) {
        super(codeEnum.getCode(), codeEnum.getMessage());
    }
}
