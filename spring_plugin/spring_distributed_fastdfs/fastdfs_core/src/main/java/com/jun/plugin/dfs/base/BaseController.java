package com.jun.plugin.dfs.base;

public abstract class BaseController {

    /**
     * 应用appKey
     */
    public final String HEADER_APP_KEY = "dfs-request-app-key";

    /**
     * 时间戳
     */
    public final String HEADER_TIMESTAMP = "dfs-request-timestamp";

    /**
     * 签名
     */
    public final String HEADER_SIGN = "dfs-request-sign";

    public String getResponseOK() {
        return "{\"result\":" + ErrorCodeEnum.OK.getCode() + "}";
    }

    public String getResponseByCode(ErrorCodeEnum code) {
        return "{\"result\":" + code.getCode() + ",\"msg\":\"" + code.getMessage() + "\"}";
    }

    public String getResponseOKWithBody(String body) {
        return "{\"result\":" + ErrorCodeEnum.OK.getCode() + ",\"body\":" + body + "}";
    }
}
