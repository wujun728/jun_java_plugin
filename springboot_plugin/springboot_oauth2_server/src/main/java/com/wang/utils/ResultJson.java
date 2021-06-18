package com.wang.utils;

/**
 * Created by wangxiangyun on 2017/1/22.
 */
public class ResultJson {
    private int code = 0;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultJson(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultJson(Object data) {
        this.data = data;
    }

    public ResultJson() {
        super();
    }
}
