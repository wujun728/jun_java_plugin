package com.zh.springbootexception.dto;

import com.zh.springbootexception.enums.AppResultCode;
import lombok.Data;

/**
 * @author Wujun
 * @date 2019/6/5
 */
@Data
public class Result {

    private int code;

    private String msg;

    private Object data;

    public Result() {}

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(AppResultCode appResultCode) {
        this.code = appResultCode.getCode();
        this.msg = appResultCode.getMsg();
    }

    public Result(AppResultCode appResultCode, Object data) {
        this.code = appResultCode.getCode();
        this.msg = appResultCode.getMsg();
        this.data = data;
    }

    public static Result genSuccessResult() {
        return new Result(AppResultCode.SUCCESS);
    }

    public static Result genSuccessResult(Object data) {
        return new Result(AppResultCode.SUCCESS,data);
    }

    public static Result genFailResult(AppResultCode appResultCode) {
        return new Result(appResultCode);
    }

    public static Result genFailResult() {
        return new Result(AppResultCode.FAIL);
    }

    public static Result genFailResult(Object data) {
        return new Result(AppResultCode.FAIL,data);
    }
}
