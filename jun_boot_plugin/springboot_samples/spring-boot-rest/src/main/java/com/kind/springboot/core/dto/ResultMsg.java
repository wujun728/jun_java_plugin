package com.kind.springboot.core.dto;

import com.kind.springboot.common.config.ResultStatus;

/**
 * Function:返回结果. <br/>
 * Date:     2016年8月11日 下午1:19:21 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
public class ResultMsg {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public ResultMsg(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultMsg(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultMsg(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = "";
    }

    public ResultMsg(ResultStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static ResultMsg ok(Object content) {
        return new ResultMsg(ResultStatus.SUCCESS, content);
    }

    public static ResultMsg ok() {
        return new ResultMsg(ResultStatus.SUCCESS);
    }

    public static ResultMsg error(ResultStatus error) {
        return new ResultMsg(error);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }
}
