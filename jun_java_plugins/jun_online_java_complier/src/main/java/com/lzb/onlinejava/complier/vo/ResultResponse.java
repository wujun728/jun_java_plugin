package com.lzb.onlinejava.complier.vo;

import com.lzb.onlinejava.complier.enums.ResultTypeEnum;

import java.io.Serializable;

/**
 * author: haiyangp
 * date:  2017/9/26
 * desc: 响应结果
 */
public class ResultResponse implements Serializable {
    //执行结果
    private String excuteResult;
    //状态类型
    private ResultTypeEnum resultTypeEnum;
    //执行时间
    private Long excuteDurationTime=-1l;

    private String message;

    public String getExcuteResult() {
        return excuteResult;
    }


    public static ResultResponse Build(ResultTypeEnum resultTypeEnum) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setResultTypeEnum(resultTypeEnum);
        return resultResponse;
    }

    public static ResultResponse Build(ResultTypeEnum resultTypeEnum, String message) {
        ResultResponse resultResponse = Build(resultTypeEnum);
        resultResponse.setMessage(message);
        return resultResponse;
    }


    public void setExcuteResult(String excuteResult) {
        this.excuteResult = excuteResult;
    }

    public Integer getResultTypeEnum() {
        return resultTypeEnum.getTypeCode();
    }

    public void setResultTypeEnum(ResultTypeEnum resultTypeEnum) {
        this.resultTypeEnum = resultTypeEnum;
    }

    public Long getExcuteDurationTime() {
        return excuteDurationTime;
    }

    public void setExcuteDurationTime(Long excuteDurationTime) {
        this.excuteDurationTime = excuteDurationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
