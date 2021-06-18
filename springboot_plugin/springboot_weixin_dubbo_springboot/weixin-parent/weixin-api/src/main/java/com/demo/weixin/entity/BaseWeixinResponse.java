package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;


public class BaseWeixinResponse implements Serializable{

    private static final long serialVersionUID = -4707516620244408399L;
    @JSONField(name = "errcode")
    private int errorCode;

    @JSONField(name = "errmsg")
    private String errorMessage;

    public boolean hasError() {
        return (errorMessage != null && errorCode != 0);
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

    @Override
    public String toString() {
        return "BaseWeixinResponse{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
