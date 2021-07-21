package org.wf.jwtp.client;

import org.wf.jwtp.provider.Token;

import java.io.Serializable;

/**
 * 统一认证中心返回数据
 * Created by wangfan on 2020-01-14 上午 9:49.
 */
public class AuthResult implements Serializable {
    public static final int CODE_OK = 0;
    public static final int CODE_ERROR = 1;
    public static final int CODE_EXPIRED = 2;

    private Integer code;
    private String msg;
    private Token token;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
